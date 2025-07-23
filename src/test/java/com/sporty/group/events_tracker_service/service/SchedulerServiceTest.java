package com.sporty.group.events_tracker_service.service;

import com.sporty.group.events_tracker_service.constant.ApplicationConstants;
import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.dto.EventStatusResponse;
import com.sporty.group.events_tracker_service.kafka.GenericPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class SchedulerServiceTest {

    private ScheduledExecutorService scheduler = mock(ScheduledExecutorService.class);
    private GenericPublisher<Long, EventStatusResponse> publisher = mock(GenericPublisher.class);
    private MockEventService mockEventService = mock(MockEventService.class);

    private SchedulerService service;

    @BeforeEach
    void setUp() {
        service = new SchedulerService(scheduler, publisher, mockEventService);
    }

    @Test
    void testInitiatePolling_SchedulesTask() {
        EventStatusRequest request = new EventStatusRequest(100L, "live");

        ScheduledFuture<?> stubFuture = mock(ScheduledFuture.class);

        when(scheduler.scheduleAtFixedRate(
                any(Runnable.class),
                eq(0L),
                anyLong(),
                eq(TimeUnit.SECONDS)
        )).thenReturn((ScheduledFuture) stubFuture);

        service.initiatePolling(request);

        verify(scheduler).scheduleAtFixedRate(
                any(Runnable.class),
                eq(0L),
                anyLong(),
                eq(TimeUnit.SECONDS)
        );
    }

    @Test
    void testFetchAndPublishEvent_PublishesToKafka() throws Exception {
        Long eventId = 100L;
        EventStatusResponse response = new EventStatusResponse(eventId, "1:10");
        when(mockEventService.getCurrentEventScore(eventId)).thenReturn(response);

        service.fetchAndPublishEvent(eventId);

        verify(publisher).publish(eq(ApplicationConstants.KAFKA_TOPIC_NAME), eq(eventId), eq(response));
    }

    @Test
    void testFetchAndPublishEvent_PublishErrorLogged() throws Exception {
        Long eventId = 101L;
        EventStatusResponse response = new EventStatusResponse(eventId, "2:15");
        when(mockEventService.getCurrentEventScore(eventId)).thenReturn(response);
        doThrow(new RuntimeException("Kafka error"))
                .when(publisher).publish(anyString(), anyLong(), any(EventStatusResponse.class));

        service.fetchAndPublishEvent(eventId);

    }


}