package com.example.cotizaciondolar.presenters;

import static com.example.cotizaciondolar.views.MainActivity.BLUE_BUTTON_ID;
import static com.example.cotizaciondolar.views.MainActivity.STOCK_BUTTON_ID;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.QuotationContract;
import com.example.cotizaciondolar.models.QuotationModel;
import com.example.cotizaciondolar.services.DolarService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotationPresenter implements QuotationContract.Presenter {
    private final QuotationContract.View view;
    private final DolarService service;

    public QuotationPresenter(QuotationContract.View view) {
        this.view = view;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(((Fragment) view).getResources().getString(R.string.uri_dolar_argentina))
                .build();

        this.service = retrofit.create(DolarService.class);
    }

    @Override
    public void getDollarQuotation(int checkedId) {
        Call<QuotationModel> execute;
        if (checkedId == STOCK_BUTTON_ID) {
            execute = service.getStockQuotation();
        } else if (checkedId == BLUE_BUTTON_ID) {
            execute = service.getBlueQuotation();
        } else {
            execute = service.getOfficialQuotation();
        }

        execute.enqueue(new Callback<QuotationModel>() {
            @Override
            public void onResponse(Call<QuotationModel> call, Response<QuotationModel> response) {
                QuotationModel body = response.body();

                if (response.isSuccessful()) {
                    // TODO: validar NP + convertir fecha a GMT-3
                    view.setDateText(body.getDate());
                    view.setPurchaseText(body.getPurchasePrice());
                    view.setSaleText(body.getSalePrice());
                } else {
                    view.setDateText("");
                    view.setPurchaseText("");
                    view.setSaleText("");
                }
            }

            @Override
            public void onFailure(Call<QuotationModel> call, Throwable t) {
                Toast.makeText(
                        ((Fragment) view).getContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
