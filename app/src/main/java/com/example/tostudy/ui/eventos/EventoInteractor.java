package com.example.tostudy.ui.eventos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.Repository.EventoRepositoryRoom;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

import java.util.List;

public class EventoInteractor implements OnRepositoryListCallBack {

    EventoContract.Presenter presenter;

    public EventoInteractor(EventoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void load(String userId){
        EventoRepositoryRoom.getInstance(this).load(userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void load(String fecha, String userId){
        EventoRepositoryRoom.getInstance(this).load(fecha, userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void delete(Evento evento){
        EventoRepositoryRoom.getInstance(this).delete(evento);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void undo(Evento evento){
        EventoRepositoryRoom.getInstance(this).undo(evento);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
        presenter.onSuccessLoad(list);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        presenter.onDeleteSuccess(msg);
    }

    @Override
    public void onSuccess(String msg) {
        presenter.onSuccess(msg);
    }

    @Override
    public void onFailure(String msg) {
        presenter.onFailure(msg);
    }
}
