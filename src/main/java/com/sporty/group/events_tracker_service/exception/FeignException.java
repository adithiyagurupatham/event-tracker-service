package com.sporty.group.events_tracker_service.exception;

import lombok.Getter;

@Getter
public class FeignException  extends RuntimeException{

    private final String errorCode;
    private final String errorMessage;

    public FeignException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
