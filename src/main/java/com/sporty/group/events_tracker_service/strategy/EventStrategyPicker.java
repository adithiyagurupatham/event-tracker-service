package com.sporty.group.events_tracker_service.strategy;

import com.sporty.group.events_tracker_service.constant.ErrorConstant;
import com.sporty.group.events_tracker_service.enums.EventStatusEnum;
import com.sporty.group.events_tracker_service.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class EventStrategyPicker {

    private static final Map<EventStatusEnum,EventProcessStrategy> strategiesByEventStatus = new HashMap<>();

    @Autowired
    public EventStrategyPicker(Set<EventProcessStrategy> eventProcessStrategies){
        eventProcessStrategies.forEach(each -> strategiesByEventStatus.put(each.getEventStatus(), each));
    }

    public static EventProcessStrategy getEventTrackerStrategy(String eventName){
        EventStatusEnum eventStatusEnum = EventStatusEnum.getStatus(eventName);
        if(Objects.isNull(strategiesByEventStatus.get(eventStatusEnum))){
            log.error("getEventTrackerStrategy - No strategy found for event  {}",eventName);
            throw new InvalidRequestException(ErrorConstant.INVALID_EVENT_STRATEGY.getErrorCode(),ErrorConstant.INVALID_EVENT_STRATEGY.getErrorMessage());
        }
        return strategiesByEventStatus.get(eventStatusEnum);
    }




}
