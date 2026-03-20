package com.euflausino.encurtaurl.config.exceptions;

import java.time.LocalDateTime;

public record TimeStampTDO(
        String message,
        int error,
        LocalDateTime timeStamp
) {
    public TimeStampTDO(String message, int error) {
        this(message, error, LocalDateTime.now());
    }
}
