package com.example.cotizaciondolar.services;

import static com.example.cotizaciondolar.services.SessionManager.TOKEN;
import static com.example.cotizaciondolar.services.SessionManager.TOKEN_CREATED_DATE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cotizaciondolar.models.entities.Event;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.EventResponse;
import com.example.cotizaciondolar.models.entities.LoginResponse;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class EventService extends AsyncTask<EventRequest, Void, Void> {
    private static final int TOKEN_MAX_DURATION_IN_MINUTES = 1;

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
        HashMap<String, String> userDetails = sessionManager.getUserDetails();

        String token = userDetails.get(TOKEN);
        String stringDateTime = userDetails.get(TOKEN_CREATED_DATE);
        ZonedDateTime dateTime = ZonedDateTime.parse(stringDateTime);

        Duration dateTimeDifference = Duration.between(dateTime, ZonedDateTime.now(ZoneId.of("UTC-3")));

        if (dateTimeDifference.compareTo(Duration.ofMinutes(TOKEN_MAX_DURATION_IN_MINUTES)) > 0) {
            String refreshToken = userDetails.get(SessionManager.TOKEN_REFRESH);
            String username = userDetails.get(SessionManager.USER_EMAIL);

            regenerateToken(refreshToken, username);
        }

        Call<EventResponse> call = apiService.postEvent("Bearer " + token, eventRequests[0]);

        try {
            Response<EventResponse> response = call.execute();
            Event event = response.body().getEvent();
            Log.d(TAG, "Evento " + event.getEventType().name() + " registrado: " + event.getDescription());
        } catch (Exception e) {
            Log.e(TAG, "Error registrando evento: " + e.getMessage());
        }

        return null;
    }

    private void regenerateToken(String refreshToken, String username) {
        Call<LoginResponse> call = apiService.putRefreshToken("Bearer " + refreshToken);

        try {
            Response<LoginResponse> response = call.execute();
            LoginResponse loginResponse = response.body();

            if (loginResponse.isSuccess()) {
                sessionManager.refreshToken(
                        loginResponse.getToken(),
                        loginResponse.getTokenRefresh()
                );

                Log.d(TAG, "Token de usuario regenerado: " + username);
            } else {

                Log.e(TAG, "Error regenerando token de usuario: " + username);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error regenerando token: " + e.getMessage());
        }
    }
}
