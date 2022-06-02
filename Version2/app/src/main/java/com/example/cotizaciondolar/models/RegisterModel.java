package com.example.cotizaciondolar.models;


import com.example.cotizaciondolar.contracts.RegisterActivityContract;
import com.example.cotizaciondolar.models.services.RegisterRequest;
import com.example.cotizaciondolar.models.services.RegisterResponse;
import com.example.cotizaciondolar.services.APIClientSOA;
import com.example.cotizaciondolar.services.APIInterfaceSOA;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel implements RegisterActivityContract.Model {

    @Override
    public void RegisterUser(String name, String lastname, String dni, String email, String password, String commission, String group, RegisterActivityContract.Model.OnFinishedListener onFinishedListener) {

        APIInterfaceSOA apiService = APIClientSOA.getClient().create(APIInterfaceSOA.class);
        RegisterRequest request = new RegisterRequest(name, lastname, dni, email,  password, commission, group);
        Call<RegisterResponse> call = apiService.postRegister(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    RegisterResponse sr = response.body();

                    if (sr.isSuccess()) {
                        GlobalSession.authToken = sr.getToken();
                        GlobalSession.refreshToken = sr.getTokenRefresh();

                        onFinishedListener.onSuccess();
                    } else {
                        onFinishedListener.onError(sr.getMsg());
                    }
                }else{
                    RegisterResponse lr =  new Gson().fromJson(response.errorBody().charStream(), RegisterResponse.class);
                    onFinishedListener.onError(lr.getMsg());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
