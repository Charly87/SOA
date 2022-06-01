package com.example.cotizaciondolar.models;

import com.example.cotizaciondolar.contracts.LoginActivityContract;
import com.example.cotizaciondolar.models.services.LoginRequest;
import com.example.cotizaciondolar.models.services.LoginResponse;
import com.example.cotizaciondolar.services.APIClientSOA;
import com.example.cotizaciondolar.services.APIInterfaceSOA;
import com.google.gson.Gson;
import com.google.gson.JsonSerializer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginActivityContract.Model {

    @Override
    public void ValidateUser(String email, String password, OnFinishedListener onFinishedListener) {

        APIInterfaceSOA apiService = APIClientSOA.getClient().create(APIInterfaceSOA.class);
        LoginRequest request = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.postLogin(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    LoginResponse sr = response.body();

                    if (sr.isSuccess()) {
                        GlobalSession.authToken = sr.getToken();
                        GlobalSession.refreshToken = sr.getTokenRefresh();

                        onFinishedListener.onSuccess();
                    } else {
                        onFinishedListener.onError(sr.getMsg());
                    }
                }else{
                    LoginResponse lr =  new Gson().fromJson(response.errorBody().charStream(), LoginResponse.class);
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
