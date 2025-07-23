package com.sporty.group.events_tracker_service.controller;

import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @PostMapping("/status")
    public ResponseEntity<String> updateEventStatus(@RequestBody EventStatusRequest eventStatusRequest){
        eventService.updateEventStatus(eventStatusRequest);
        return ResponseEntity.ok("Updated event status: " + eventStatusRequest.getEventStatus());
    }

}
