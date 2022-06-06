package com.example.cotizaciondolar.services;

import static com.example.cotizaciondolar.services.SessionManager.TOKEN;
import static com.example.cotizaciondolar.services.SessionManager.TOKEN_CREATED_DATE;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    private static final int TOKEN_MAX_DURATION_IN_MINUTES = 30;

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
            String stringDateTime = userDetails.get(TOKEN_CREATED_DATE);
            ZonedDateTime dateTime = ZonedDateTime.parse(stringDateTime);

            Duration dateTimeDifference = Duration.between(dateTime, ZonedDateTime.now(ZoneId.of("UTC-3")));

            // Verificamos que el token sea valido, de lo contrario solicitamos uno nuevo
            if (dateTimeDifference.compareTo(Duration.ofMinutes(TOKEN_MAX_DURATION_IN_MINUTES)) > 0) {
                String refreshToken = userDetails.get(SessionManager.TOKEN_REFRESH);
                String username = userDetails.get(SessionManager.USER_EMAIL);

                regenerateToken(refreshToken, username);
            }

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
