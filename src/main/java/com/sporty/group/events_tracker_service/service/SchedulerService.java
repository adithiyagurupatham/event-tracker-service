package com.sporty.group.events_tracker_service.service;

import com.sporty.group.events_tracker_service.constant.ApplicationConstants;
import com.sporty.group.events_tracker_service.dto.EventStatusRequest;
import com.sporty.group.events_tracker_service.dto.EventStatusResponse;
import com.sporty.group.events_tracker_service.kafka.GenericPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final ScheduledExecutorService scheduler;

    private final GenericPublisher<Long, EventStatusResponse> publisher;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    private final MockEventService mockEventService;

    @Value("${scheduler.fire-rate}")
    private long fireRate;


    public void initiatePolling(EventStatusRequest eventStatusRequest){
        if(scheduledTasks.containsKey(eventStatusRequest.getEventId())){
            return;
        }
        Runnable task = () -> fetchAndPublishEvent(eventStatusRequest.getEventId());
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, fireRate, TimeUnit.SECONDS);
        scheduledTasks.put(eventStatusRequest.getEventId(),future);

    }

    public void stopPolling(EventStatusRequest eventStatusRequest){
        ScheduledFuture<?> task = scheduledTasks.remove(eventStatusRequest.getEventId());
        if (Objects.nonNull(task)){
            task.cancel(true);
        }
    }

    public void fetchAndPublishEvent(Long eventId){
        EventStatusResponse eventStatusResponse = mockEventService.getCurrentEventScore(eventId);
        if(Objects.isNull(eventStatusResponse)){
            return;
        }
        try{
            publisher.publish(ApplicationConstants.KAFKA_TOPIC_NAME,eventId,eventStatusResponse);
        }
        catch (Exception e){
            log.error("fetchAndPublishEvent - An error has occurred while publishing to Kafka for event id {} , {}",eventId,e.getMessage());
        }

    }





}
