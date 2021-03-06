package com.example.cotizaciondolar.models;

import static com.example.cotizaciondolar.models.entities.EventType.USER_LOGGED_IN;

import android.content.Context;

import com.example.cotizaciondolar.contracts.LoginContract;
import com.example.cotizaciondolar.database.UserHistoryRepository;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.LoginRequest;
import com.example.cotizaciondolar.models.entities.LoginResponse;
import com.example.cotizaciondolar.services.EventService;
import com.example.cotizaciondolar.services.SessionManager;
import com.example.cotizaciondolar.services.SoaApi;
import com.example.cotizaciondolar.services.SoaApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginContract.Model {
    private final UserHistoryRepository userHistoryRepository;
    private final SessionManager sessionManager;
    private EventService eventService;
    private final Context context;

    public LoginModel(Context context) {
        this.context = context;
        userHistoryRepository = new UserHistoryRepository(context);
        sessionManager = new SessionManager(context);
    }

    @Override
    public void loginUser(LoginRequest loginRequest, OnFinishedListener onFinishedListener) {
        SoaApi apiService = SoaApiClient.getClient().create(SoaApi.class);
        Call<LoginResponse> call = apiService.postLogin(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int responseCode = response.code();

                if (responseCode == 200) {
                    LoginResponse loginResponse = response.body();

                    if (loginResponse != null) {
                        // Guarda los datos de sesion del usuario
                        sessionManager.createLoginSession(
                                loginRequest.getEmail(),
                                loginResponse.getToken(),
                                loginResponse.getTokenRefresh()
                        );

                        // Genera un evento de usuario logueado
                        EventRequest eventRequest = new EventRequest(
                                USER_LOGGED_IN.tag,
                                "Usuario inici?? sesi??n: " + loginRequest.getEmail()
                        );

                        eventService = new EventService(context);
                        eventService.execute(eventRequest);

                        // Guarda el historial del usuario en la base de datos
                        userHistoryRepository.insertUserHistory(loginRequest.getEmail());

                        onFinishedListener.onSuccess();
                    } else {
                        onFinishedListener.onError();
                    }
                } else {
                    onFinishedListener.onError();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onFinishedListener.onError();
            }
        });
    }
}
