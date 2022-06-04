package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class EventRequest {
    @SerializedName("env")
    private final String environment;
    @SerializedName("type_events")
    private final EventType eventType;
    private final String description;

    public EventRequest(EventType eventType, String description) {
        this.environment = "TEST";
        this.eventType = eventType;
        this.description = description;
    }

}
