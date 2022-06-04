package com.example.cotizaciondolar.models;



import android.content.Context;

import com.example.cotizaciondolar.DataAccess;
import com.example.cotizaciondolar.Users;
import com.example.cotizaciondolar.contracts.HistoryContract;

import java.util.ArrayList;
import java.util.List;

public class HistoryModel implements HistoryContract.Model {


    @Override
    public List<Users> getHistoryData(Context context) {
        DataAccess data = new DataAccess(context);
        ArrayList<Users> list = (ArrayList<Users>) data.getAllUserHistory();
        return list;
    }
}
