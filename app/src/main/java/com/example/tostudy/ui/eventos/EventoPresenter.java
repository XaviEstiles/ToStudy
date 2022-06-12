package com.example.tostudy.ui.eventos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.model.Evento;

import java.util.List;

public class EventoPresenter implements EventoContract.Presenter{
    EventoContract.View view;
    EventoInteractor interactor;

    public EventoPresenter(EventoContract.View view) {
        this.view = view;
        this.interactor = new EventoInteractor(this);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
        view.showData(list);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        view.onDeleteSuccess(msg);
    }

    @Override
    public void onSuccess(String msg) {
        view.onSuccess(msg);
    }

    @Override
    public void onFailure(String msg) {
        view.onFailure(msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void load(String userId) {
        interactor.load(userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void load(String fecha, String userId) {
        interactor.load(fecha, userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void undo(Evento evento) {
        interactor.undo(evento);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void delete(Evento evento) {
        interactor.delete(evento);
    }

    @Override
    public void order() {
        view.showOrder();
    }
}
