package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.Prioridad;
import com.example.tostudy.ui.eventos.EventoContract;
import com.example.tostudy.ui.eventos.EventoInteractor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EventoRepository implements EventoContract.Repository {
    ArrayList<Evento> eventos;
    EventoInteractor interactor;
    static EventoRepository repository;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private EventoRepository(EventoInteractor interactor) {
        eventos = new ArrayList<>();
        this.interactor = interactor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static EventoRepository getInstance(EventoInteractor interactor){
        if (repository == null){
            repository = new EventoRepository(interactor);
        }
        repository.interactor = interactor;
        return repository;
    }

    @Override
    public void load() {
        interactor.onSuccessLoad(eventos);
    }

    @Override
    public void load(String fecha) {

    }

    /*@Override
    public void edit(Evento evento) {
        for (Evento o : eventos){
            if (o.getId().equals(evento.getId())){
                eventos.remove(o);
                break;
            }
        }
        eventos.add(evento);
        interactor.onSuccess("Elemento editado");
    }*/

    @Override
    public void delete(Evento evento) {
        eventos.remove(evento);
        interactor.onDeleteSuccess(evento.getNombre()+" eliminado.");
    }

    @Override
    public void undo(Evento evento) {
        eventos.add(evento);
        interactor.onSuccess("Elemento recuperado");
    }
}
