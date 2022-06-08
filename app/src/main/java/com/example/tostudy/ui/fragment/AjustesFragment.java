package com.example.tostudy.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tostudy.R;
import com.example.tostudy.databinding.FragmentAjustesBinding;
import com.example.tostudy.ui.login.LoginActivity;

public class AjustesFragment extends Fragment {

    FragmentAjustesBinding binding;
    SharedPreferences prefs;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        binding = FragmentAjustesBinding.inflate(getLayoutInflater());
        prefs = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
        context = getContext();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding.tvEmail.setText(prefs.getString("Email","example@gmail.com"));
        binding.tvNombre.setText(prefs.getString("Name","example"));

        Glide.with(getContext())
                .load(prefs.getString("Img",""))
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfilAjustes);

        binding.btnCerrarSesion.setOnClickListener(v -> {
            prefs.edit().remove("Email").apply();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        if (prefs.getString("OrdenarEve","").equals("Fecha")){
            binding.swOrdEve.setChecked(false);
        }else {
            binding.swOrdEve.setChecked(true);
        }

        if (prefs.getString("OrdenarObj","").equals("Fecha")){
            binding.swOrdObj.setChecked(false);
        }else {
            binding.swOrdObj.setChecked(true);
        }

        binding.swOrdEve.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefs.edit().putString("OrdenarEve","Prioridad").apply();
            } else {
                prefs.edit().putString("OrdenarEve","Fecha").apply();
            }
        });

        binding.swOrdObj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefs.edit().putString("OrdenarObj","Prioridad").apply();
            } else {
                prefs.edit().putString("OrdenarObj","Fecha").apply();
            }
        });

        return binding.getRoot();
    }
}