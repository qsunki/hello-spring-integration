package com.example.oddeven;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class OddEvenConfig {

    @Bean
    public Counter counter() {
        return new Counter();
    }

    @Bean
    public MessageChannel evenChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel oddChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel numbersChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel positivesChannel() {
        return new DirectChannel();
    }

    @InboundChannelAdapter(
            channel = "numbersChannel",
            poller = @Poller(cron = "1,2,3,5,8,13,21,34,55 * * * * ?", maxMessagesPerPoll = "1"))
    public int numbersMessageSource() {
        return counter().next();
    }

    @Bean
    @Filter(
            inputChannel = "numbersChannel",
            outputChannel = "positivesChannel",
            discardChannel = "nullChannel")
    public MessageSelector positiveNumberFilter() {
        return message -> {
            Integer payload = (Integer) message.getPayload();
            return payload > 0;
        };
    }

    @Router(inputChannel = "positivesChannel")
    public String routeToChannels(Message<Integer> message) {
        return message.getPayload() % 2 == 0 ? "evenChannel" : "oddChannel";
    }

    @Bean
    @ServiceActivator(inputChannel = "oddChannel")
    public LoggingHandler oddLoggerActivator() {
        LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.INFO);
        adapter.setLoggerName("org.example.oddeven.OddLogger");
        adapter.setLogExpressionString(
                "'odd number ' + payload + ' at ' + headers.timestamp / 1000 % 60 + 's'");
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "evenChannel")
    public EvenLogger evenLoggerActivator() {
        return new EvenLogger();
    }
}
