package com.example.cotizaciondolar.services;

import static com.example.cotizaciondolar.services.SessionManager.TOKEN;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.EventResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class EventService extends AsyncTask<EventRequest, Void, Void> {

    private final String TAG = "EventService";
    private final SoaApi apiService;
    private final SessionManager sessionManager;

    public EventService(Context context) {
        super();
        apiService = SoaApiClient.getClient().create(SoaApi.class);
        sessionManager = new SessionManager(context);
    }

    @Override
    protected Void doInBackground(EventRequest... eventRequests) {
        EventRequest eventRequest = eventRequests[0];

        if (sessionManager.isUserLoggedIn()) {
            HashMap<String, String> userDetails = sessionManager.getUserDetails();

            String token = userDetails.get(TOKEN);

            Call<EventResponse> call = apiService.postEvent("Bearer " + token, eventRequest);

            try {
                Response<EventResponse> response = call.execute();
                EventResponse eventResponse = response.body();

                if (eventResponse != null && eventResponse.isSuccess()) {
                    Log.d(TAG, "Evento " + eventRequest.getEventType() +
                            " registrado: " + eventRequest.getDescription()
                    );
                } else {
                    Log.e(TAG, "Error registrando evento " + eventRequest.getEventType());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error registrando evento: " + e.getMessage());
            }

        }

        return null;
    }
}
