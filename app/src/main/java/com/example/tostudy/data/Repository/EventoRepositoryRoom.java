package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.Database;
import com.example.tostudy.data.dao.EventoDao;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.ui.eventos.EventoContract;
import com.example.tostudy.ui.eventos.EventoInteractor;
import com.example.tostudy.ui.eventos.EventosManage.EveManageContract;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EventoRepositoryRoom implements EventoContract.Repository, EveManageContract.Repository {
    ArrayList<Evento> eventos;
    EventoInteractor interactor;
    EveManageContract.Interactor interactorM;
    static EventoRepositoryRoom repository;
    private EventoDao eventoDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private EventoRepositoryRoom() {
        eventos = new ArrayList<>();
        eventoDao = Database.getDatabase().eventoDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static EventoRepositoryRoom getInstance(EventoInteractor interactor){
        if (repository == null){
            repository = new EventoRepositoryRoom();
        }
        repository.interactor = interactor;
        return repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static EventoRepositoryRoom getInstanceManage(EveManageContract.Interactor interactor){
        if (repository == null){
            repository = new EventoRepositoryRoom();
        }
        repository.interactorM = interactor;
        return repository;
    }

    @Override
    public void load() {
        try {
            eventos = (ArrayList<Evento>)Database.databaseWriteExecutor.submit(()->eventoDao.select()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        interactor.onSuccessLoad(eventos);
    }

    @Override
    public void load(String fecha) {
        try {
            eventos = (ArrayList<Evento>)Database.databaseWriteExecutor.submit(()->eventoDao.selectFromDate(fecha)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        interactor.onSuccessLoad(eventos);
    }

    @Override
    public void delete(Evento evento) {
        Database.databaseWriteExecutor.submit(()->eventoDao.delete(evento));
        interactor.onDeleteSuccess(evento.getNombre()+" eliminado.");
    }

    @Override
    public void undo(Evento evento) {
        Database.databaseWriteExecutor.submit(()->eventoDao.insert(evento));
        interactor.onSuccess("Elemento recuperado");
    }

    @Override
    public void add(Evento evento) {
        Database.databaseWriteExecutor.submit(()->eventoDao.insert(evento));
        interactorM.onSuccess("Elemento añadido");
    }

    @Override
    public void edit(Evento evento) {
        Database.databaseWriteExecutor.submit(()->eventoDao.update(evento));
        interactor.onSuccess("Elemento editado");
    }
}
