package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class EventResponse {
    private final boolean success;
    @SerializedName("env")
    private final String environment;
    private final Event event;

    public EventResponse(boolean success, String environment, Event event) {
        this.success = success;
        this.environment = environment;
        this.event = event;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEnvironment() {
        return environment;
    }

    public Event getEvent() {
        return event;
    }
}
