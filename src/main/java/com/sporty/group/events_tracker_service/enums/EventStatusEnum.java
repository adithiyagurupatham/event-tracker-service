package com.sporty.group.events_tracker_service.enums;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum EventStatusEnum {
    LIVE("LIVE"),NOT_LIVE("NOT LIVE");

    private final String statusValue;

    EventStatusEnum(String statusValue) {
        this.statusValue = statusValue;
    }

    private static final Map<String, EventStatusEnum> LOOKUP_MAP = new HashMap<>();

    static {
        for (EventStatusEnum s : values()) {
            LOOKUP_MAP.put(s.name().toLowerCase(), s);
            LOOKUP_MAP.put(s.statusValue.toLowerCase(), s);
        }
    }

    public static EventStatusEnum getStatus(String status){
        if(StringUtils.isBlank(status)){
            return null;
        }
        return LOOKUP_MAP.get(status.trim().toLowerCase());
    }
}
