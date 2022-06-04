package com.example.cotizaciondolar.presenters;

import android.content.Context;

import com.example.cotizaciondolar.contracts.HistoryContract;
import com.example.cotizaciondolar.models.HistoryModel;
import com.example.cotizaciondolar.models.entities.UserLoginHistory;

import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class HistoryPresenter implements
        HistoryContract.Presenter {

    private final HistoryContract.View view;
    private final HistoryContract.Model model;
    private final Context context;

    public HistoryPresenter(HistoryContract.View view, Context context) {
        this.view = view;
        this.model = new HistoryModel();
        this.context = context;
    }

    @Override
    public void onGenerateTable() {
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Usuario", 1)
                .item("Ultimo acceso", 1)
                .build();

        ArrayList<DataTableRow> rows = new ArrayList<>();

        List<UserLoginHistory> userLoginHistories = model.getHistoryData(context);

        for (UserLoginHistory userLoginHistory : userLoginHistories) {
            DataTableRow row = new DataTableRow.Builder()
                    .value(userLoginHistory.getName())
                    .value(userLoginHistory.getLoginDate())
                    .build();

            rows.add(row);
        }

        view.loadTable(header, rows);
    }
}
