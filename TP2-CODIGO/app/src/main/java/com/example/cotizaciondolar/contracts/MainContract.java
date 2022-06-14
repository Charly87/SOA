package com.example.cotizaciondolar.contracts;

public interface MainContract {

    interface View {
        void showLogoutDialog();
    }

    interface Model {
        void logoutUser();

        void registerSensorEvent(float proximity);
    }

    interface Presenter {
        void onLogoutClick();

        void registerSensorListener();

        void unregisterSensorListener();
    }
}
