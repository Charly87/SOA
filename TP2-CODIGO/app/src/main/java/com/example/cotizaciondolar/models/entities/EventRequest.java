package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class EventRequest {
    @SerializedName("env")
    private final String environment;
    @SerializedName("type_events")
    private final String eventType;
    private final String description;

    public EventRequest(String eventType, String description) {
        this.environment = "PROD";
        this.eventType = eventType;
        this.description = description;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }
}
