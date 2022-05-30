package com.example.cotizaciondolar.ui.quotation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cotizaciondolar.QuotationContract;
import com.example.cotizaciondolar.databinding.FragmentQuotationBinding;
import com.example.cotizaciondolar.ui.presenters.QuotationPresenter;

public class QuotationFragment extends Fragment implements QuotationContract.View {

    private TextView textView;
    private FragmentQuotationBinding binding;
    private QuotationContract.Presenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuotationViewModel quotationViewModel =
                new ViewModelProvider(this).get(QuotationViewModel.class);

        binding = FragmentQuotationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.textQuotation;
        quotationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        presenter = new QuotationPresenter(this);
        presenter.getDollarBlueQuotation();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setQuotation(final String quotation) {
        textView.setText(quotation);
    }
}