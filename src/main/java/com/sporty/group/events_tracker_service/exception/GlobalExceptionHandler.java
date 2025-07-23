package com.sporty.group.events_tracker_service.exception;

import com.sporty.group.events_tracker_service.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> InvalidRequestHandler(InvalidRequestException e){
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse = apiResponse.buildErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getErrorCode(), e.getErrorMessage());
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}
