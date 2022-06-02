package com.example.cotizaciondolar.models;

import com.example.cotizaciondolar.contracts.SignUpContract;
import com.example.cotizaciondolar.models.entities.SignUpRequest;
import com.example.cotizaciondolar.models.entities.SignUpResponse;
import com.example.cotizaciondolar.services.SoaApi;
import com.example.cotizaciondolar.services.SoaApiClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpModel implements SignUpContract.Model {

    @Override
    public void signUpUser(SignUpRequest signUpRequest, OnFinishedListener onFinishedListener) {

        SoaApi apiService = SoaApiClient.getClient().create(SoaApi.class);
        Call<SignUpResponse> call = apiService.postRegister(signUpRequest);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    SignUpResponse sr = response.body();

                    if (sr.isSuccess()) {
                        GlobalSession.authToken = sr.getToken();
                        GlobalSession.refreshToken = sr.getTokenRefresh();

                        onFinishedListener.onSuccess();
                    } else {
                        onFinishedListener.onError(sr.getMsg());
                    }
                } else {
                    SignUpResponse lr = new Gson().fromJson(response.errorBody().charStream(), SignUpResponse.class);
                    onFinishedListener.onError(lr.getMsg());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
