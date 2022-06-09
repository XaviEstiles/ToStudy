package com.example.tostudy.ui.objetivos;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.databinding.FragmentInfoObjetivosBinding;
import com.example.tostudy.ui.objetivos.ObjetivoContract;
import com.example.tostudy.ui.objetivos.ObjetivoPresenter;
import com.example.tostudy.ui.objetivos.ObjetivosFragment;

public class InfoObjetivosFragment extends ObjetivosFragment {

    Objetivo objetivo;
    FragmentInfoObjetivosBinding binding;
    ObjetivoContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInfoObjetivosBinding.inflate(getLayoutInflater());
        presenter = new ObjetivoPresenter(this);
        objetivo = (Objetivo) getArguments().getSerializable("objetivo");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        binding.tvNombre.setText(objetivo.getName());
        binding.tvDescripcion.setText(objetivo.getDescription());
        binding.tvFecha.setText(objetivo.getDate());
        binding.seekBar2.setProgress((int)objetivo.getProgress());
        binding.tvProgreso.setText(binding.seekBar2.getProgress() + "%");

        switch (objetivo.getPriority()){
            case 1:
                binding.imgPrioridad.setImageResource(R.drawable.ic_importancia_baja);
                break;
            case 2:
                binding.imgPrioridad.setImageResource(R.drawable.ic_importancia_media);
                break;
            case 3:
                binding.imgPrioridad.setImageResource(R.drawable.ic_importancia_alta);
                break;
        }

        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pro = seekBar.getProgress();
                objetivo.setProgress(pro);
                presenter.editProgres(objetivo);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                binding.tvProgreso.setText(progress+"%");
            }
        });


        return binding.getRoot();
    }
}