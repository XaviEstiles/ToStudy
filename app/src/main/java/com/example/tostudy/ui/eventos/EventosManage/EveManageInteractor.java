package com.example.tostudy.ui.eventos.EventosManage;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.Repository.EventoRepositoryRoom;
import com.example.tostudy.data.model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EveManageInteractor implements EveManageContract.Interactor {

    EveManageContract.Presenter presenter;

    public EveManageInteractor(EveManageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void validateEventoToAdd(Evento evento){
        if(evento.getNombre().isEmpty()){
            presenter.onNombreEmpty();
            return;
        }
        if(evento.getHoraIni().isEmpty()){
            presenter.onHoraIniEmpty();
            return;
        }
        if(evento.getHoraFin().isEmpty()){
            presenter.onHoraFinEmpty();
            return;
        }
        if(evento.getFecha().isEmpty()){
            presenter.onFechaEmpty();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime horaIni = LocalTime.parse(evento.getHoraIni(),formatter);
        LocalTime horaFin = LocalTime.parse(evento.getHoraFin(),formatter);

        if(horaFin.isBefore(horaIni)){
            presenter.onHoraFinErr();
            return;
        }

        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //replace yyyy-MM-dd
        LocalDate localDate = LocalDate.parse(evento.getFecha(), formatter);

        if(localDate.isBefore(LocalDate.now())){
            presenter.onFechaErr();
            return;
        }

        add(evento);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void validateEventoToEdit(Evento evento){
        if(evento.getNombre().isEmpty()){
            presenter.onNombreEmpty();
            return;
        }
        if(evento.getHoraIni().isEmpty()){
            presenter.onHoraIniEmpty();
            return;
        }
        if(evento.getHoraFin().isEmpty()){
            presenter.onHoraFinEmpty();
            return;
        }
        if(evento.getFecha().isEmpty()){
            presenter.onFechaEmpty();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime horaIni = LocalTime.parse(evento.getHoraIni(),formatter);
        LocalTime horaFin = LocalTime.parse(evento.getHoraFin(),formatter);

        if(horaFin.isBefore(horaIni)){
            presenter.onHoraFinErr();
            return;
        }

        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(evento.getFecha(), formatter);

        if(localDate.isBefore(LocalDate.now())){
            presenter.onFechaErr();
            return;
        }

        edit(evento);
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
    public void add(Evento evento) {
        EventoRepositoryRoom.getInstanceManage(this).add(evento);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void edit(Evento evento) {
        EventoRepositoryRoom.getInstanceManage(this).edit(evento);
    }
}
