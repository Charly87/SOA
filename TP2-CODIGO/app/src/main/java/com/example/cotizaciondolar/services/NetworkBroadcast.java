package com.example.cotizaciondolar.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkBroadcast extends BroadcastReceiver {
    public static NetworkListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        // initialize connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // check condition
        if (listener != null) {

            // when connectivity receiver
            // listener  not null
            // get connection status
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

            // call listener method
            listener.onNetworkChange(isConnected);
        }
    }

    public interface NetworkListener {
        // create method
        void onNetworkChange(boolean isConnected);
    }
}
