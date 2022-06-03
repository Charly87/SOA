package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class EventRequest {
    @SerializedName("env")
    private String environment;
    @SerializedName("type_events")
    private EventType eventType;
    private String description;

    public EventRequest(EventType eventType, String description) {
        this.environment = "TEST";
        this.eventType = eventType;
        this.description = description;
    }

    public String getEnvironment() {
        return environment;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }
}
