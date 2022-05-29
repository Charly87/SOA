package com.example.cotizaciondolar.ui.quotation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.cotizaciondolar.databinding.FragmentQuotationBinding;

public class QuotationFragment extends Fragment {

    private FragmentQuotationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuotationViewModel quotationViewModel =
                new ViewModelProvider(this).get(QuotationViewModel.class);

        binding = FragmentQuotationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textQuotation;
        quotationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}