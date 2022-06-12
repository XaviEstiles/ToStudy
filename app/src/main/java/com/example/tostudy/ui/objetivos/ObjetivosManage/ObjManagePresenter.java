package com.example.tostudy.ui.objetivos.ObjetivosManage;

import android.os.Build;

import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

import java.util.List;

public class ObjManagePresenter implements ObjManageContract.Presenter{
    ObjManageContract.View view;
    ObjManageInteractor interactor;

    public ObjManagePresenter(ObjManageContract.View view) {
        this.view = view;
        this.interactor = new ObjManageInteractor(this);
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
    public void add(Objetivo objetivo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            interactor.validateEventoToAdd(objetivo);
        }
    }

    @Override
    public void edit(Objetivo objetivo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            interactor.validateEventoToEdit(objetivo);
        }
    }

    @Override
    public void onNombreEmpty() {
        view.setNameEmpty();
    }

    @Override
    public void onFechaEmpty() {
        view.setDateEmpty();
    }

    @Override
    public void onFechaErr() {
        view.setDateErr();
    }
}
