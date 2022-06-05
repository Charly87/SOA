package com.example.cotizaciondolar.models.entities;

import com.google.gson.annotations.SerializedName;

public class Event {
    @SerializedName("type_events")
    private final EventType eventType;
    private final String description;
    private final int dni;
    private final int id;

    public Event(EventType eventType, String description, int dni, int id) {
        this.eventType = eventType;
        this.description = description;
        this.dni = dni;
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public int getDni() {
        return dni;
    }

    public int getId() {
        return id;
    }

}
