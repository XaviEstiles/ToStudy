package com.example.tostudy.ui.eventos;

import static com.example.tostudy.data.model.Prioridad.ALTA;
import static com.example.tostudy.data.model.Prioridad.BAJA;
import static com.example.tostudy.data.model.Prioridad.MEDIA;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.databinding.FragmentInfoEventBinding;
import com.example.tostudy.databinding.FragmentInfoObjetivosBinding;
import com.example.tostudy.ui.objetivos.ObjetivoContract;
import com.example.tostudy.ui.objetivos.ObjetivoPresenter;

public class InfoEventFragment extends Fragment {

    Evento evento;
    FragmentInfoEventBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInfoEventBinding.inflate(getLayoutInflater());
        evento = (Evento) getArguments().getSerializable("evento");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        binding.tvNombre.setText(evento.getNombre());
        binding.tvDescripcion.setText(evento.getDescripcion());
        binding.tvFecha.setText(evento.getFecha());
        binding.tvHoraIni.setText(evento.getHoraIni());
        binding.tvHoraFin.setText(evento.getHoraFin());

        switch (evento.getPrioridad()){
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


        return binding.getRoot();
    }
}