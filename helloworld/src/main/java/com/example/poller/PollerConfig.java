package com.example.poller;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableIntegration
public class PollerConfig {

    @Bean
    public MessageChannel loggerChannel() {
        return new DirectChannel();
    }

    @InboundChannelAdapter(
            channel = "loggerChannel",
            poller = @Poller(fixedDelay = "2000", maxMessagesPerPoll = "2"))
    public long timeMessageSource() {
        return System.currentTimeMillis();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.initialize();
        return executor;
    }

    @Bean
    @ServiceActivator(inputChannel = "loggerChannel")
    public LoggingHandler loggingHandler() {
        LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.INFO);
        adapter.setLoggerName("org.springframework.integration.samples.helloworld");
        adapter.setLogExpressionString("headers.id + ': ' + payload");
        return adapter;
    }
}
