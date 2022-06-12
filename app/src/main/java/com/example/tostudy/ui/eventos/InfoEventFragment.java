package com.example.tostudy.ui.eventos;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.databinding.FragmentInfoEventBinding;

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

        binding.tvNombre.setText(evento.getName());
        binding.tvDescripcion.setText(evento.getDescription());
        binding.tvFecha.setText(evento.getDate());
        binding.tvHoraIni.setText(evento.getStartTime());
        binding.tvHoraFin.setText(evento.getFinishTime());

        switch (evento.getPriority()){
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


        return binding.getRoot();
    }
}