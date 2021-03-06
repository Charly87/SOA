package com.example.cotizaciondolar.views;

import android.content.DialogInterface;
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

    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.cotizaciondolar.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        presenter.unregisterSensorListener();

        new MaterialAlertDialogBuilder(this)
                .setTitle("??Desea cerrar sesi??n?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.registerSensorListener();
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
