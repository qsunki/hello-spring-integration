package com.example.oddeven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
public class EvenLogger {

    public void log(int i, @Header("timestamp") long timestamp) {
        log.info("even number: {} at {}s", i, timestamp / 1000 % 60);
    }
}
