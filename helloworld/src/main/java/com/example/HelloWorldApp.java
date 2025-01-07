package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

@Slf4j
public class HelloWorldApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HelloWorldConfig.class);
        MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
        PollableChannel outputChannel = context.getBean("outputChannel", PollableChannel.class);
        inputChannel.send(new GenericMessage<>("World"));
        Message<?> message = outputChannel.receive(0);
        if (message == null) {
            log.info("==> HelloWorldDemo: null");
            return;
        }
        log.info("==> HelloWorldDemo: {}", message.getPayload());
    }
}
