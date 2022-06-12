package com.example.tostudy.ui.eventos.EventosManage;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.model.Evento;

import java.util.List;

public class EveManagePresenter implements EveManageContract.Presenter{
    EveManageContract.View view;
    EveManageInteractor interactor;

    public EveManagePresenter(EveManageContract.View view) {
        this.view = view;
        this.interactor = new EveManageInteractor(this);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
    }

    @Override
    public void onDeleteSuccess(String msg) {

    }

    @Override
    public void onSuccess(String msg) {
        view.onSuccess(msg);
    }

    @Override
    public void onFailure(String msg) {
        view.onFailure(msg);
    }

    @Override
    public void add(Evento evento) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            interactor.validateEventoToAdd(evento);
        }
    }

    @Override
    public void edit(Evento evento) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            interactor.validateEventoToEdit(evento);
        }
    }

    @Override
    public void onNombreEmpty() {
        view.setNameEmpty();
    }

    @Override
    public void onHoraIniEmpty() {
        view.setHoraIniEmpty();
    }

    @Override
    public void onHoraFinEmpty() {
        view.setHoraFinEmpty();
    }

    @Override
    public void onFechaEmpty() {
        view.setDateEmpty();
    }

    @Override
    public void onHoraFinErr() {
        view.setHoraFinErr();
    }

    @Override
    public void onFechaErr() {
        view.setDateErr();
    }
}
