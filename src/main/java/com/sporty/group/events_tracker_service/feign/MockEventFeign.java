package com.sporty.group.events_tracker_service.feign;

import com.sporty.group.events_tracker_service.dto.EventStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MockEventFeign {

    @Value("${mock.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ResponseEntity<EventStatusResponse> getCurrentEventScore(Long eventId) {
        String url = baseUrl + "/mock-api/events/" + eventId;
        return restTemplate.getForEntity(url, EventStatusResponse.class);
    }


}
