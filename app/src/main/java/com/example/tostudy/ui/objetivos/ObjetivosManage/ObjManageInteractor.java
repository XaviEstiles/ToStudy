package com.example.tostudy.ui.objetivos.ObjetivosManage;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.Repository.ObjetivoRepositoryRoom;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.Objetivo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ObjManageInteractor implements ObjManageContract.Interactor {

    ObjManageContract.Presenter presenter;

    public ObjManageInteractor(ObjManageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void validateEventoToEdit(Objetivo objetivo){
        if(objetivo.getName().isEmpty()){
            presenter.onNombreEmpty();
            return;
        }
        if(objetivo.getDate().isEmpty()){
            presenter.onFechaEmpty();
            return;
        }

        edit(objetivo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void validateEventoToAdd(Objetivo objetivo){
        if(objetivo.getName().isEmpty()){
            presenter.onNombreEmpty();
            return;
        }
        if(objetivo.getDate().isEmpty()){
            presenter.onFechaEmpty();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(objetivo.getDate(), formatter);

        if(localDate.isBefore(LocalDate.now())){
            presenter.onFechaErr();
            return;
        }

        add(objetivo);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String msg) {

    }

    @Override
    public void onSuccess(String msg) {
        presenter.onSuccess(msg);
    }

    @Override
    public void onFailure(String msg) {
        presenter.onFailure(msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void add(Objetivo objetivo) {
        ObjetivoRepositoryRoom.getInstance(this).add(objetivo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void edit(Objetivo objetivo) {
        ObjetivoRepositoryRoom.getInstance(this).edit(objetivo);
    }
}
