package com.ivan.endpointstats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ivan.endpointstats.model.Status;
import com.ivan.endpointstats.service.EndpointStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final EndpointStatsService endpointStatsService;

    public AppRunner(EndpointStatsService endpointStatsService) {
        this.endpointStatsService = endpointStatsService;
    }

    @Override
    public void run(String... args) throws Exception {
        collectStatuses();
        showResults();
    }

    public void showResults() {
        endpointStatsService.printReport();
    }

    public void collectStatuses() throws InterruptedException {
        long start = System.currentTimeMillis();

        List<CompletableFuture<Status>> statuses = new ArrayList<>();

        for(int i = 0; i < 1000; i++) {
            // Calling 1000 endpoints asynchronously using a threadPoolExecutor for parallelization
            CompletableFuture<Status> status = endpointStatsService.getStats(String.valueOf(i));
            statuses.add(status);
        }

        // Wait for all threads to complete
        CompletableFuture.allOf(statuses.toArray(new CompletableFuture[statuses.size()])).join();

        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
    }

}
