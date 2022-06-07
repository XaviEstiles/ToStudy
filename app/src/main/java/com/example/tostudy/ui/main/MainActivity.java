package com.example.tostudy.ui.main;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tostudy.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



import com.example.tostudy.R;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity{



    public interface OnBackPressedListener{
        void onBackPressed();
    }
    private OnBackPressedListener listener;
    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }
    @Override
    public void onBackPressed() {
        if(listener != null)
            listener.onBackPressed();
        super.onBackPressed();
    }

    private ActivityMainBinding binding;

    DrawerLayout  drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    SharedPreferences prefs;

    NavController navController;

    private AppBarConfiguration appBarConfiguration;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(this, R.id.nav_host);

        prefs = this.getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);

        toolbar = binding.layoutToolbar.toolbar;
        toolbar.setTitle("ToStudy");
        setSupportActionBar(toolbar);



        Set<Integer> topLevelDestination = new HashSet<>();
        topLevelDestination.add(R.id.eventosRecientesFragment);
        topLevelDestination.add(R.id.calendarioFragment);
        topLevelDestination.add(R.id.objetivoFragment);
        topLevelDestination.add(R.id.aboutFragment);
        topLevelDestination.add(R.id.ajustesFragment);

        NavigationUI.setupWithNavController(binding.navigationView, navController);
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestination).setOpenableLayout(binding.drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfiguration);

        binding.navigationView.getHeaderView(0).setOnClickListener(v -> {
            navController.navigate(R.id.perfilFragment);
            binding.getRoot().closeDrawers();
        });

        ImageView img = ((ImageView) binding.navigationView.getHeaderView(0).findViewById(R.id.imgPerfilMenu));
        TextView tvEmail = ((TextView) binding.navigationView.getHeaderView(0).findViewById(R.id.tvEmail));
        TextView tvNombre = ((TextView) binding.navigationView.getHeaderView(0).findViewById(R.id.tvNombre));

        tvEmail.setText(prefs.getString("Email","example@gmail.com"));

        Glide.with(getApplicationContext())
                .load("http://vps-9e48c221.vps.ovh.net/fotos-perfil/prueba.jpg")
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(img);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.navigationView.setCheckedItem(R.id.eventosRecientesFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!prefs.getBoolean("Recuerdame",false)){
            prefs.edit().clear().apply();
        }
    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        navController.navigate(R.id.calendarioFragment);

        switch (item.getItemId()){
            case R.id.nav_calendario:
                navController.navigate(R.id.calendarioFragment);
                break;

            case R.id.nav_eventos:
                navController.navigate(R.id.action_calendarioFragment_to_eventosRecientesFragment);
                break;

            case R.id.nav_rutinas:
                navController.navigate(R.id.action_calendarioFragment_to_rutinasFragment);
                break;

            case R.id.nav_ajustes:
                navController.navigate(R.id.action_calendarioFragment_to_ajustesFragment);
                break;

            case R.id.nav_about:
                navController.navigate(R.id.action_calendarioFragment_to_aboutFragment);
                break;

        }
                return false;
    }*/
    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fragmenlist_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController.navigate(R.id.calendarioFragment);
        switch (item.getItemId()){
            case R.id.action_añadirRutina:
                navController.navigate(R.id.action_calendarioFragment_to_editarObjetivoFragment);
                return true;
            case R.id.action_añadirEvento:
                navController.navigate(R.id.action_calendarioFragment_to_editarEventoFragment);
                return true;
            default:
                //return false si el fragmen modifica el menu.
                return false;
        }
    }*/
}