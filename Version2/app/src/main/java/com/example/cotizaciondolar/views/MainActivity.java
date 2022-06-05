package com.example.cotizaciondolar.views;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cotizaciondolar.R;
import com.example.cotizaciondolar.contracts.MainContract;
import com.example.cotizaciondolar.databinding.ActivityMainBinding;
import com.example.cotizaciondolar.presenters.MainPresenter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    public static final int OFFICIAL_BUTTON_ID = R.id.btn_official;
    public static final int BLUE_BUTTON_ID = R.id.btn_blue;
    public static final int STOCK_BUTTON_ID = R.id.btn_stock;
    private static final int LOGOUT_BUTTON_ID = R.id.action_logout;
    private AppBarConfiguration mAppBarConfiguration;
    private SensorManager manager;
    private Sensor proximitySensor;

    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.cotizaciondolar.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_history, R.id.nav_quotation)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(
                this,
                navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        presenter = new MainPresenter(this, getApplicationContext());

        setSensorManager();

    }

    private void setSensorManager() {
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = manager.getSensorList(Sensor.TYPE_PROXIMITY).get(0);
        registerSensorListener();
    }

    private void registerSensorListener() {
        manager.registerListener(
                (SensorEventListener) presenter,
                proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // Deshabilitamos que no pueda volver a la pantalla anterior
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case LOGOUT_BUTTON_ID:
                showLogoutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showLogoutDialog() {
        manager.unregisterListener((SensorEventListener) presenter);
        new MaterialAlertDialogBuilder(this)
                .setTitle("¿Desea cerrar sesión?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registerSensorListener();
                    }
                })
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onLogoutClick();
                    }
                })
                .show();
    }
}
