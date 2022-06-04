package com.example.cotizaciondolar.models;


import android.content.Context;

import com.example.cotizaciondolar.contracts.HistoryContract;
import com.example.cotizaciondolar.database.UserHistoryRepository;
import com.example.cotizaciondolar.models.entities.UserLoginHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryModel implements HistoryContract.Model {


    @Override
    public List<UserLoginHistory> getHistoryData(Context context) {
        UserHistoryRepository data = new UserHistoryRepository(context);
        ArrayList<UserLoginHistory> list = (ArrayList<UserLoginHistory>) data.getAllUserHistory();
        return list;
    }
}
