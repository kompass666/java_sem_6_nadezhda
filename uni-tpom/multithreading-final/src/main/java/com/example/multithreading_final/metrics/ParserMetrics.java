package com.example.multithreading_final.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class ParserMetrics {

    public final Timer crawlPipelineTime;
    public final Counter crawlSuccess;
    public final Counter crawlError;
    public final Counter savedRecords;

    public ParserMetrics(MeterRegistry registry) {
        this.crawlPipelineTime = Timer.builder("crawler.pipeline.time")
                .description("Full time of crawling + parsing + saving one page")
                .publishPercentileHistogram()
                .register(registry);

        this.crawlSuccess = Counter.builder("crawler.pipeline.success")
                .description("Successful crawls")
                .register(registry);

        this.crawlError = Counter.builder("crawler.pipeline.error")
                .description("Failed crawls")
                .register(registry);

        this.savedRecords = Counter.builder("crawler.saved.records")
                .description("Records saved to DB")
                .register(registry);
    }
}
