package com.sporty.group.events_tracker_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GenericPublisher<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;
    private final RetryTemplate retryTemplate;

    public GenericPublisher(KafkaTemplate<K, V> kafkaTemplate,
                            @Value("${retry.attempts}") int attempts,
                            @Value("${retry.backoff}") long backoffMs) {
        this.kafkaTemplate = kafkaTemplate;
        this.retryTemplate = createRetryTemplate(attempts, backoffMs);
    }

    private RetryTemplate createRetryTemplate(int attempts, long backoffMs) {
        RetryTemplate tpl = new RetryTemplate();
        FixedBackOffPolicy backOff = new FixedBackOffPolicy();
        backOff.setBackOffPeriod(backoffMs);
        tpl.setBackOffPolicy(backOff);

        SimpleRetryPolicy policy = new SimpleRetryPolicy(attempts);
        tpl.setRetryPolicy(policy);

        return tpl;
    }

    public void publish(String topic, K key, V value) throws Exception {
        retryTemplate.execute(ctx -> {
            kafkaTemplate.send(topic, key, value).get();
            return null;
        }, ctx -> {
            log.error("Failed publishing to topic {} with key {} after {} retries, error:", topic, key,
                    ctx.getRetryCount(), ctx.getLastThrowable());
            return null;
        });
    }
}
