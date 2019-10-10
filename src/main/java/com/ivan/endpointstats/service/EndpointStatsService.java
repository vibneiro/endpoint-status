package com.ivan.endpointstats.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ivan.endpointstats.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EndpointStatsService {

	private static final Logger logger = LoggerFactory.getLogger(EndpointStatsService.class);

	private StatusAggregationService statusAggregationService = new StatusAggregationService();

	private final RestTemplate restTemplate;

	public EndpointStatsService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
	}

	@Async
	// Call happens in its thread.
	public CompletableFuture<Status> getStats(String id) throws InterruptedException {
		logger.debug("Looking up " + id);
		// For the same endpoints but different resources it is reasonable to reuse httpConnectionPool as well as HTTP/2 for multiplexing
		String url = String.format("http://storage.googleapis.com/revsreinterview/hosts/host%s/status", id);
		ResponseEntity<Status> response = restTemplate.getForEntity(url, Status.class);

		if (HttpStatus.OK == response.getStatusCode()) {
			Status status = response.getBody();
			// Aggregate result, this line could've been moved later on the caller through CompletableFuture.thenRun(...) chain for separation of concerns:
			statusAggregationService.addOrUpdateStatus(status);
			return CompletableFuture.completedFuture(status);
		} else {
			// Here we could add Exponential back-off with a retrial mechanism
			String errorMsg = "Http failed: endPointId = " + id + " statusCode = " + response.getStatusCode();
			logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
	}

	public void printReport() {
		statusAggregationService.printReport();
	}

}
