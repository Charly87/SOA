package com.example.cotizaciondolar.models;

import static com.example.cotizaciondolar.models.entities.EventType.SENSOR_PROXIMITY;

import android.content.Context;

import com.example.cotizaciondolar.contracts.MainContract;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.services.EventService;
import com.example.cotizaciondolar.services.SessionManager;

public class MainModel implements MainContract.Model {
    private final SessionManager sessionManager;
    private final Context context;

    public MainModel(Context context) {
        this.context = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    public void logoutUser() {
        sessionManager.logoutUser();
    }

    @Override
    public void registerSensorEvent(float proximity) {
        EventRequest eventRequest = new EventRequest(
                SENSOR_PROXIMITY.tag,
                "Se detectó un objeto a " + proximity + " cm"
        );

        EventService eventService = new EventService(context);
        eventService.execute(eventRequest);

    }
}
