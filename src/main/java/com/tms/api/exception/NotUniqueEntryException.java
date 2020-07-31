package com.tms.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotUniqueEntryException extends RuntimeException {
    public NotUniqueEntryException(String message) {
        super(message);
    }

    public NotUniqueEntryException(Throwable cause) {
        super(cause);
    }

    public NotUniqueEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueEntryException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
