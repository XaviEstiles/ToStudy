package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.apiservice.ToStudyApiAdapter;
import com.example.tostudy.apiservice.dto.EventoResponse;
import com.example.tostudy.apiservice.dto.ObjetivoResponse;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.ui.eventos.EventoContract;
import com.example.tostudy.ui.eventos.EventoInteractor;
import com.example.tostudy.ui.eventos.EventosManage.EveManageContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoRepositoryRoom implements EventoContract.Repository, EveManageContract.Repository {
    ArrayList<Evento> eventos;
    EventoInteractor interactor;
    EveManageContract.Interactor interactorM;
    static EventoRepositoryRoom repository;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private EventoRepositoryRoom() {
        eventos = new ArrayList<>();
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
    public void load(String userId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Call<EventoResponse> call = ToStudyApiAdapter.getApiService().getEvens("1","2022-06-01","nearlyEvents");//formatter.format(new Date()));
        call.enqueue(new Callback<EventoResponse>() {

            @Override
            public void onResponse(Call<EventoResponse> call, Response<EventoResponse> response) {
                eventos = response.body().getResult();
                interactor.onSuccessLoad(eventos);
            }

            @Override
            public void onFailure(Call<EventoResponse> call, Throwable t) {
                interactor.onFailure("Error al cargar");
            }
        });
    }

    @Override
    public void load(String fecha, String userId) {
        Call<EventoResponse> call = ToStudyApiAdapter.getApiService().getEvens("1",fecha,"");
        call.enqueue(new Callback<EventoResponse>() {

            @Override
            public void onResponse(Call<EventoResponse> call, Response<EventoResponse> response) {

                eventos = response.body().getResult();
                interactor.onSuccessLoad(eventos);
            }

            @Override
            public void onFailure(Call<EventoResponse> call, Throwable t) {
                interactor.onFailure("Error al cargar");
                eventos.clear();
                interactor.onSuccessLoad(eventos);
            }
        });
    }

    @Override
    public void delete(Evento evento) {
        interactor.onDeleteSuccess(evento.getName()+" eliminado.");
    }

    @Override
    public void undo(Evento evento) {
        interactor.onSuccess("Elemento recuperado");
    }

    @Override
    public void add(Evento evento) {
        interactorM.onSuccess("Elemento añadido");
    }

    @Override
    public void edit(Evento evento) {
        interactor.onSuccess("Elemento editado");
    }
}
