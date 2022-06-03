package com.example.cotizaciondolar.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // cosas raras

        DataTable dataTable = binding.dataTable;

        DataTableHeader header = new DataTableHeader.Builder()
                .item("Usuario", 1)
      //          .item("Ultimo acceso", 1)
        .build();

        ArrayList<DataTableRow> rows = new ArrayList<>();

        DataAccess data = new DataAccess(getContext());
        ArrayList<String> list = (ArrayList<String>) data.getAllUserHistory();

        // define 200 fake rows for table
        for (String l: list) {
            DataTableRow row = new DataTableRow.Builder()
                    .value(l)
                    .build();
            rows.add(row);
        }
//        for(int i=0;i<200;i++) {
//
//            DataTableRow row = new DataTableRow.Builder()
//                    .value("Usuario #" + i)
//                    .value("03/06/22")
//
//            .build();
//            rows.add(row);
//        }

        //dataTable.setTypeface(typeface);
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(getContext());

        // terminan cosas raras

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
