package com.example.cotizaciondolar.ui.presenters;

import com.example.cotizaciondolar.DolarService;
import com.example.cotizaciondolar.QuotationContract;
import com.example.cotizaciondolar.ui.models.QuotationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotationPresenter implements QuotationContract.Presenter {

    private QuotationContract.View view;
    private QuotationContract.Model model;
    private DolarService service;

    public QuotationPresenter(QuotationContract.View view) {
        this.view = view;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api-dolar-argentina.herokuapp.com/api/")
                .build();

        this.service = retrofit.create(DolarService.class);
    }

    @Override
    public void getDollarBlueQuotation() {
        Call<QuotationModel> execute = service.getBlueQuotation();
        execute.enqueue(new Callback<QuotationModel>() {
            @Override
            public void onResponse(Call<QuotationModel> call, Response<QuotationModel> response) {
                if (response.isSuccessful()) {
                    view.setQuotation(response.body().getBuyPrice());
                } else {
                    view.setQuotation("");
                }
            }

            @Override
            public void onFailure(Call<QuotationModel> call, Throwable t) {
                // TODO: tirar toast
                System.out.println("error en retrofit");
            }
        });

    }
}
