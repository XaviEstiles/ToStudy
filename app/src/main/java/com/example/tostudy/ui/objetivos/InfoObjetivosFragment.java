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

        binding.tvNombre.setText(objetivo.getNombre());
        binding.tvDescripcion.setText(objetivo.getDescripcion());
        binding.tvFecha.setText(objetivo.getFecha());
        binding.seekBar2.setProgress((int)objetivo.getProgreso());
        binding.tvProgreso.setText(binding.seekBar2.getProgress() + "%");

        switch (objetivo.getPrioridad()){
            case BAJA:
                binding.imgPrioridad.setImageResource(R.drawable.ic_importancia_baja);
                break;
            case MEDIA:
                binding.imgPrioridad.setImageResource(R.drawable.ic_importancia_media);
                break;
            case ALTA:
                binding.imgPrioridad.setImageResource(R.drawable.ic_importancia_alta);
                break;
        }

        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pro = seekBar.getProgress();
                objetivo.setProgreso(pro);
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