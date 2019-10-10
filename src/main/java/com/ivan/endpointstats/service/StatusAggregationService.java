package com.ivan.endpointstats.service;

import java.util.concurrent.ConcurrentHashMap;

import com.ivan.endpointstats.model.StatusHolder;
import com.ivan.endpointstats.model.AppVersionKey;
import com.ivan.endpointstats.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@ThreadSafe
@Component("statusService")
public class StatusAggregationService {

	private static final Logger logger = LoggerFactory.getLogger(StatusAggregationService.class);

	private final ConcurrentHashMap<AppVersionKey, StatusHolder> statuses = new ConcurrentHashMap<>();

	public void addOrUpdateStatus(Status status) {

		AppVersionKey key = AppVersionKey.create(status.getApplication(), status.getVersion());
		//Thread-safe based on CompareAndSwap: atomically add or update key,value of the map:
		statuses.computeIfAbsent(key, k -> new StatusHolder()).addRequestCount(status.getRequests_count());
		statuses.computeIfAbsent(key, k -> new StatusHolder()).addSuccessCount(status.getSuccess_count());
	}

	public void printReport() {
		logger.info("Total status = " + statuses.size());
		for(AppVersionKey key: statuses.keySet()) {
			logger.info(key.getAppName() + ',' + key.getVersionName() + statuses.get(key).getSuccessRate());
		}
	}

}
