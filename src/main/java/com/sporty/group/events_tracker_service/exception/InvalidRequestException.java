package com.sporty.group.events_tracker_service.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException{

    private final String errorCode;
    private final String errorMessage;

    public InvalidRequestException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
