package com.sporty.group.events_tracker_service.validator;

import com.sporty.group.events_tracker_service.constant.ErrorConstant;
import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.enums.EventStatusEnum;
import io.micrometer.common.util.StringUtils;

import java.util.Objects;
import java.util.function.Function;

public interface EventStatusRequestValidator extends Function<EventStatusRequest, ErrorConstant> {

    static EventStatusRequestValidator isEventIdValid() {
        return eventStatusRequest -> Objects.isNull(eventStatusRequest.getEventId()) ?
                ErrorConstant.EVENT_ID_MISSING : null;
    }

    static EventStatusRequestValidator isEventStatusPresent() {
        return eventStatusRequest -> StringUtils.isBlank(eventStatusRequest.getEventStatus()) ?
                ErrorConstant.EVENT_STATUS_MISSING : null;
    }

    static EventStatusRequestValidator isEventStatusValid() {
        return eventStatusRequest -> Objects.isNull(EventStatusEnum.getStatus(eventStatusRequest.getEventStatus())) ?
                ErrorConstant.INCORRECT_EVENT_STATUS : null;
    }


    default EventStatusRequestValidator and(EventStatusRequestValidator other) {
        return eventStatusRequest -> {
            ErrorConstant errorConstant = this.apply(eventStatusRequest);
            return Objects.isNull(errorConstant) ? other.apply(eventStatusRequest) : errorConstant;
        };
    }
}
