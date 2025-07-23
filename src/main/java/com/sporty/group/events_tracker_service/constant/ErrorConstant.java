package com.sporty.group.events_tracker_service.constant;

import lombok.Getter;

@Getter
public enum ErrorConstant {

    EVENT_ID_MISSING("EVE_400_1", "Event id is missing"),
    EVENT_STATUS_MISSING("EVE_400_2", "Event Status is missing"),
    INCORRECT_EVENT_STATUS("EVE_400_3","Please check the event status"),
    INVALID_EVENT_STRATEGY("EVE_400_4","No Strategy present for provided event name"),
    EXTERNAL_API_EMPTY_RESPONSE("EVE_400_5","External API has returned empty response"),
    EXTERNAL_API_FAILURE("EVE_400_6","External API has some error"),
    CURRENT_SCORE_MISSING("EVE_400_7","Current score is missing");



    private final String errorCode;
    private final String errorMessage;

    ErrorConstant(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
