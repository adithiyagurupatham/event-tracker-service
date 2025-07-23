package com.sporty.group.events_tracker_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class MockConfig {

    @Bean
    public Random random(){
        return new Random();
    }
}
