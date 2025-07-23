package com.sporty.group.events_tracker_service.model;

import com.sporty.group.events_tracker_service.enums.EventStatusEnum;
import lombok.Data;
import lombok.Getter;


@Data
public class EventStatus {
    private long eventId;
    private EventStatusEnum eventStatus;


}
