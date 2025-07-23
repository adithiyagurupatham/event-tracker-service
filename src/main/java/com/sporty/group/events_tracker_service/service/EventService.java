package com.sporty.group.events_tracker_service.service;

import com.sporty.group.events_tracker_service.constant.ErrorConstant;
import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.exception.InvalidRequestException;
import com.sporty.group.events_tracker_service.strategy.EventProcessStrategy;
import com.sporty.group.events_tracker_service.strategy.EventStrategyPicker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.sporty.group.events_tracker_service.validator.EventStatusRequestValidator.isEventIdValid;
import static com.sporty.group.events_tracker_service.validator.EventStatusRequestValidator.isEventStatusPresent;
import static com.sporty.group.events_tracker_service.validator.EventStatusRequestValidator.isEventStatusValid;

@Service
@Slf4j
public class EventService {

    public void updateEventStatus(EventStatusRequest eventStatusRequest) {
        ErrorConstant errorConstant = isEventIdValid().and(isEventStatusPresent()).and(isEventStatusValid()).apply(eventStatusRequest);
        if(Objects.nonNull(errorConstant)){
            log.error("updateEventStatus - Invalid input provided {}",eventStatusRequest);
            throw new InvalidRequestException(errorConstant.getErrorCode(), errorConstant.getErrorMessage());
        }
        EventProcessStrategy eventProcessStrategy = EventStrategyPicker.getEventTrackerStrategy(eventStatusRequest.getEventStatus());
        eventProcessStrategy.process(eventStatusRequest);
    }
}
