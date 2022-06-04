package com.example.cotizaciondolar.contracts;


import android.content.Context;

import com.example.cotizaciondolar.models.entities.UserLoginHistory;

import java.util.ArrayList;
import java.util.List;

import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public interface HistoryContract {
    interface View {
        void loadTable(DataTableHeader header, ArrayList<DataTableRow> rows);
    }

    interface Model {


        List<UserLoginHistory> getHistoryData(Context context);
    }

    interface Presenter {

        void onGenerateTable();
    }
}
