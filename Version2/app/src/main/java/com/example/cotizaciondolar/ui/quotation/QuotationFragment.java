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
import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.databinding.FragmentQuotationBinding;
import com.example.cotizaciondolar.ui.presenters.QuotationPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class QuotationFragment extends Fragment implements QuotationContract.View {
    private MaterialButtonToggleGroup buttonToggleGroup;
    private MaterialButton officialButton;
    private MaterialButton blueButton;
    private MaterialButton stockButton;
    private TextView dateText;
    private TextView purchaseText;
    private TextView saleText;
    private FragmentQuotationBinding binding;
    private QuotationContract.Presenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QuotationViewModel quotationViewModel =
                new ViewModelProvider(this).get(QuotationViewModel.class);

        binding = FragmentQuotationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        presenter = new QuotationPresenter(this);

        buttonToggleGroup = binding.buttonToggleGroup;
        dateText = binding.textDate;
        purchaseText = binding.textPurchase;
        saleText = binding.textSale;
        officialButton = binding.btnOfficial;
        blueButton = binding.btnBlue;
        stockButton = binding.btnStock;

        // Selecciona el boton de cot. oficial
        buttonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                // Solo ejecutamos este metodo cuando el boton esta checked
                if (isChecked) {
                    presenter.getDollarQuotation(checkedId);
                }
            }
        });
        buttonToggleGroup.check(R.id.btn_official);

        quotationViewModel.getText().observe(getViewLifecycleOwner(), purchaseText::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void setDateText(String date) {
        dateText.setText(date);
    }

    @Override
    public void setPurchaseText(final String purchase) {
        purchaseText.setText(purchase);
    }

    @Override
    public void setSaleText(String sale) {
        saleText.setText(sale);
    }

}
