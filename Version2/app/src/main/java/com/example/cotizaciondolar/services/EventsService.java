package com.example.cotizaciondolar.services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cotizaciondolar.models.GlobalSession;
import com.example.cotizaciondolar.models.entities.Event;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.EventResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class EventsService extends AsyncTask<EventRequest, Void, Void> {
    private final String TAG = "EventsService";
    private final SoaApi apiService;

    public EventsService() {
        super();
        apiService = SoaApiClient.getClient().create(SoaApi.class);
    }

    @Override
    protected Void doInBackground(EventRequest... eventRequests) {
        Call<EventResponse> call = apiService.postEvent("Bearer " + GlobalSession.authToken, eventRequests[0]);

        try {
            Response<EventResponse> response = call.execute();
            Event event = response.body().getEvent();
            Log.d(TAG, "Evento registrado: " + event.getEventType().name());
        } catch (IOException e) {
            Log.e(TAG, "Error registrando evento: " + e.getMessage());
        }

        return null;
    }

}
