package com.example.cotizaciondolar.models;

import static com.example.cotizaciondolar.models.entities.EventType.USER_SIGNED_UP;

import android.content.Context;

import com.example.cotizaciondolar.contracts.SignUpContract;
import com.example.cotizaciondolar.database.UserHistoryRepository;
import com.example.cotizaciondolar.models.entities.EventRequest;
import com.example.cotizaciondolar.models.entities.SignUpRequest;
import com.example.cotizaciondolar.models.entities.SignUpResponse;
import com.example.cotizaciondolar.services.EventService;
import com.example.cotizaciondolar.services.SessionManager;
import com.example.cotizaciondolar.services.SoaApi;
import com.example.cotizaciondolar.services.SoaApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpModel implements SignUpContract.Model {
    private final UserHistoryRepository userHistoryRepository;
    private final SessionManager sessionManager;
    private EventService eventService;
    private final Context context;

    public SignUpModel(Context context) {
        this.context = context;
        userHistoryRepository = new UserHistoryRepository(context);
        sessionManager = new SessionManager(context);
    }

    @Override
    public void signUpUser(SignUpRequest signUpRequest, OnFinishedListener onFinishedListener) {
        SoaApi apiService = SoaApiClient.getClient().create(SoaApi.class);
        Call<SignUpResponse> call = apiService.postRegister(signUpRequest);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse signUpResponse = response.body();

                if (response.isSuccessful() && signUpResponse != null) {
                    if (signUpResponse.isSuccess()) {
                        // Guarda los datos de sesion del usuario
                        sessionManager.createLoginSession(
                                signUpRequest.getEmail(),
                                signUpResponse.getToken(),
                                signUpResponse.getTokenRefresh()
                        );

                        // Genera un evento de usuario registrado
                        EventRequest signedUpEvent = new EventRequest(
                                USER_SIGNED_UP.tag,
                                "Registro e inicio de sesión del usuario " + signUpRequest.getEmail()
                        );

                        eventService = new EventService(context);
                        eventService.execute(signedUpEvent);

                        // Guarda el historial del usuario en la base de datos
                        userHistoryRepository.insertUserHistory(signUpRequest.getEmail());

                        onFinishedListener.onSuccess();
                    } else {
                        onFinishedListener.onError(signUpResponse.getMessage());
                    }
                } else {
                    String error;

                    if (signUpResponse != null) {
                        error = signUpResponse.getMessage();
                    } else {
                        error = response.body().getMessage();
                    }

                    onFinishedListener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                onFinishedListener.onError(t.getMessage());
            }
        });
    }
}
