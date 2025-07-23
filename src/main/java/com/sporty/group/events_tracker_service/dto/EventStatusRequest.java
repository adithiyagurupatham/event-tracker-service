package com.sporty.group.events_tracker_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventStatusRequest {
    private Long eventId;
    private String eventStatus;

}
