package com.sporty.group.events_tracker_service.controller;

import com.sporty.group.events_tracker_service.dto.EventStatusResponse;
import com.sporty.group.events_tracker_service.service.MockEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock-api")
@RequiredArgsConstructor
public class MockEventApiController {

    private final MockEventService mockEventService;

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventStatusResponse> getScore(@PathVariable Long eventId) {
        return new ResponseEntity<>(mockEventService.getMockScore(eventId), HttpStatus.OK);
    }
}