package com.example.cotizaciondolar.models;

import com.example.cotizaciondolar.contracts.LoginContract;
import com.example.cotizaciondolar.models.entities.LoginRequest;
import com.example.cotizaciondolar.models.entities.LoginResponse;
import com.example.cotizaciondolar.services.SoaApi;
import com.example.cotizaciondolar.services.SoaApiClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginContract.Model {

    @Override
    public void validateUser(String email, String password, OnFinishedListener onFinishedListener) {
        SoaApi apiService = SoaApiClient.getClient().create(SoaApi.class);
        LoginRequest request = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.postLogin(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse sr = response.body();

                    if (sr.isSuccess()) {
                        GlobalSession.authToken = sr.getToken();
                        GlobalSession.refreshToken = sr.getTokenRefresh();

                        onFinishedListener.onSuccess();
                    } else {
                        onFinishedListener.onError(sr.getMsg());
                    }
                } else {
                    LoginResponse lr = new Gson().fromJson(response.errorBody().charStream(), LoginResponse.class);
                    onFinishedListener.onError(lr.getMsg());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
