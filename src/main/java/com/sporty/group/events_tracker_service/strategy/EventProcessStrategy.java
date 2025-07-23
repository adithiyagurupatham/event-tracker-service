package com.sporty.group.events_tracker_service.strategy;

import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.enums.EventStatusEnum;

public interface EventProcessStrategy {

    EventStatusEnum getEventStatus();

    void process(EventStatusRequest eventStatusRequest);

}
