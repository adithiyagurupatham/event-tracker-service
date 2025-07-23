package com.sporty.group.events_tracker_service.controller;

import com.sporty.group.events_tracker_service.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventsController.class)
public class EventsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService; // Service dependency is mocked

    @Test
    void testUpdateEventStatus_ValidRequest_ReturnsOk() throws Exception {
        String requestJson = "{\"eventId\":\"1234\",\"eventStatus\":\"live\"}";

        mockMvc.perform(post("/api/v1/events/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated event status: live"));
    }
}
