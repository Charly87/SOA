package com.example.cotizaciondolar.presenters;


import androidx.fragment.app.Fragment;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.Users;
import com.example.cotizaciondolar.contracts.HistoryContract;

import com.example.cotizaciondolar.models.HistoryModel;

import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;


public class HistoryPresenter implements
        HistoryContract.Presenter{

    private final HistoryContract.View view;
    private final HistoryContract.Model model;

    public HistoryPresenter(HistoryContract.View view) {
        this.view = view;
        this.model = new HistoryModel();
    }


    @Override
    public void onGenerateTable() {
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Usuario", 1)
                .item("Ultimo acceso", 1)
                .build();

        ArrayList<DataTableRow> rows = new ArrayList<>();

        List<Users> list = model.getHistoryData(((Fragment)view).getContext());
        for (Users l: list) {
            DataTableRow row = new DataTableRow.Builder()
                    .value(l.getEmail())
                    .value(l.getDate())
                    .build();
            rows.add(row);
        }
        view.loadTable(header,rows);
    }
}
