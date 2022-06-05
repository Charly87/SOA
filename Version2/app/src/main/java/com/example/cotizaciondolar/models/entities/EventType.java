package com.example.cotizaciondolar.models.entities;

public enum EventType {
    SMS_SENT("Mensaje-SMS-Enviado"),
    QUOTATION_DATA_RETRIEVED("Cotizacion-Obtenida"),
    USER_SIGNED_UP("Usuario-Registrado"),
    USER_LOGGED_IN("Usuario-Inicio-Sesion"),
    SENSOR_ACCELEROMETER("Sensor-Acelerometro"),
    SENSOR_PROXIMITY("Sensor-Proximidad");

    public final String tag;

    EventType(String tag) {
        this.tag = tag;
    }
}
