package com.sporty.group.events_tracker_service.strategy;

import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.enums.EventStatusEnum;
import com.sporty.group.events_tracker_service.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class LiveEventProcessStrategy implements EventProcessStrategy {

    private final SchedulerService schedulerService;

    @Override
    public EventStatusEnum getEventStatus() {
        return EventStatusEnum.LIVE;
    }

    @Override
    public void process(EventStatusRequest eventStatusRequest) {
        schedulerService.initiatePolling(eventStatusRequest);
    }
}
