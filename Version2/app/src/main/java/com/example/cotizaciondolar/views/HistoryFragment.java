package com.example.cotizaciondolar.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cotizaciondolar.contracts.HistoryContract;
import com.example.cotizaciondolar.databinding.FragmentHistoryBinding;
import com.example.cotizaciondolar.presenters.HistoryPresenter;

import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;


public class HistoryFragment extends Fragment implements HistoryContract.View {

    private FragmentHistoryBinding binding;
    private HistoryContract.Presenter presenter;

    private DataTable dataTable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        presenter = new HistoryPresenter(this);

        dataTable = binding.dataTable;

        presenter.onGenerateTable();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void loadTable(DataTableHeader header, ArrayList<DataTableRow> rows) {
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(getContext());
    }
}
