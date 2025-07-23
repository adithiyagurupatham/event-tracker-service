package com.sporty.group.events_tracker_service.strategy;

import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.enums.EventStatusEnum;
import com.sporty.group.events_tracker_service.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotLiveEventProcessStrategy implements EventProcessStrategy{


    private final SchedulerService schedulerService;

    @Override
    public EventStatusEnum getEventStatus() {
        return EventStatusEnum.NOT_LIVE;
    }

    @Override
    public void process(EventStatusRequest eventStatusRequest) {
        schedulerService.stopPolling(eventStatusRequest);
    }
}
