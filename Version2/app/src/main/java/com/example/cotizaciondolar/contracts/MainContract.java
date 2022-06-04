package com.example.cotizaciondolar.contracts;

public interface MainContract {

    interface View {
    }

    interface Model {
        void logoutUser();
    }

    interface Presenter {

        void onLogoutClick();
    }
}
