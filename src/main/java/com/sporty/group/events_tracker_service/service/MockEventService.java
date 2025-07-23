package com.sporty.group.events_tracker_service.service;

import com.sporty.group.events_tracker_service.dto.EventStatusResponse;
import com.sporty.group.events_tracker_service.exception.FeignException;
import com.sporty.group.events_tracker_service.feign.MockEventFeign;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

import static com.sporty.group.events_tracker_service.constant.ErrorConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockEventService {

    private final Random random;

    private final MockEventFeign mockEventFeign;


    public EventStatusResponse getMockScore(Long eventId) {
        int score1 = random.nextInt(10);
        int score2 = random.nextInt(100);
        String currentScore = score1 + ":" + score2;
        return EventStatusResponse.builder().eventId(eventId).currentScore(currentScore).build();
    }

    public EventStatusResponse getCurrentEventScore(Long eventId) {
        ResponseEntity<EventStatusResponse> currentEventScoreResponseEntity = null;
        try {
            currentEventScoreResponseEntity = mockEventFeign.getCurrentEventScore(eventId);
            validateResponse(currentEventScoreResponseEntity);
            return currentEventScoreResponseEntity.getBody();
        } catch (Exception e) {
            log.error("getUpdatedEventScore - An exception occurred while getting current score for event id {}", eventId);
            // publish to DLQ or persist in some data store for future processing
        }
        return null;
    }


    private void validateResponse(ResponseEntity<EventStatusResponse> currentEventScoreResponseEntity) {
        if (Objects.isNull(currentEventScoreResponseEntity) || Objects.isNull(currentEventScoreResponseEntity.getBody())) {
            log.error("validateResponse - external API response is empty");
            throw new FeignException(EXTERNAL_API_EMPTY_RESPONSE.getErrorCode(), EXTERNAL_API_EMPTY_RESPONSE.getErrorMessage());
        }
        if (!currentEventScoreResponseEntity.getStatusCode().is2xxSuccessful()) {
            log.error("validateResponse - status code error for external API  {}", currentEventScoreResponseEntity.getStatusCode());
            throw new FeignException(EXTERNAL_API_FAILURE.getErrorCode(), EXTERNAL_API_FAILURE.getErrorMessage());
        }
        EventStatusResponse eventStatusResponse = currentEventScoreResponseEntity.getBody();
        if (StringUtils.isEmpty(eventStatusResponse.getCurrentScore())) {
            log.error("validateResponse - current score empty for event id {}", eventStatusResponse.getEventId());
            throw new FeignException(CURRENT_SCORE_MISSING.getErrorCode(), CURRENT_SCORE_MISSING.getErrorMessage());
        }
    }


}
