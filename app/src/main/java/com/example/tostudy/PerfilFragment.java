package com.example.tostudy;

import static java.lang.System.load;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.tostudy.databinding.FragmentPerfilBinding;;

public class PerfilFragment extends Fragment {

    FragmentPerfilBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        binding = FragmentPerfilBinding.inflate(getLayoutInflater());

        Glide.with(getContext())
                .load("http://vps-9e48c221.vps.ovh.net/fotos-perfil/prueba.jpg")
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfil);

        return binding.getRoot();
    }
}