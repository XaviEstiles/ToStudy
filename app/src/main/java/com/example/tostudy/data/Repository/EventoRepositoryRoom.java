package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.apiservice.ToStudyApiAdapter;
import com.example.tostudy.apiservice.dto.BooleanResponse;
import com.example.tostudy.apiservice.dto.EventoResponse;
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
        Call<EventoResponse> call = ToStudyApiAdapter.getApiService().getEvents(userId,formatter.format(new Date()),"nearlyEvents");
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
        Call<EventoResponse> call = ToStudyApiAdapter.getApiService().getEvents(userId,fecha,"");
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
        Call<BooleanResponse> call = ToStudyApiAdapter.getApiService().deleteEvents(String.valueOf(evento.getId()));
        call.enqueue(new Callback<BooleanResponse>() {

            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                BooleanResponse insertado = response.body();
                if (insertado.getResult().equals("true"))
                    interactor.onDeleteSuccess("Elemento eliminado");
                else
                    interactor.onFailure("Error al eliminar el evento");
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                interactor.onFailure("Error al eliminar");
            }
        });
    }

    @Override
    public void undo(Evento evento) {
        add(evento);
    }


    @Override
    public void add(Evento evento) {
        String data = "{" +
                "\"userId\": \""+evento.getUserId()+"\"," +
                "\"name\": \""+evento.getName()+"\"," +
                "\"startTime\": \""+evento.getStartTime()+"\"," +
                "\"finishTime\": \""+evento.getFinishTime()+"\"," +
                "\"date\": \""+evento.getDate()+"\"," +
                "\"description\": \""+evento.getDescription()+"\"," +
                "\"priority\": \""+evento.getPriority()+"\""+
                "}";
        Call<BooleanResponse> call = ToStudyApiAdapter.getApiService().saveEvents(
                data
        );
        call.enqueue(new Callback<BooleanResponse>() {

            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                BooleanResponse insertado = response.body();
                if (insertado.getResult().equals("true"))
                    interactor.onSuccess("Elemento añadido");
                else
                    interactor.onFailure("Error al guardar el evento");
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                interactor.onFailure("Error al guardar el evento");
            }
        });
    }

    @Override
    public void edit(Evento evento) {
        String data = "{" +
                "\"id\": \""+evento.getId()+"\"," +
                "\"userId\": \""+evento.getUserId()+"\"," +
                "\"name\": \""+evento.getName()+"\"," +
                "\"date\": \""+evento.getDate()+"\"," +
                "\"startTime\": \""+evento.getStartTime()+"\"," +
                "\"finishTime\": \""+evento.getFinishTime()+"\"," +
                "\"description\": \""+evento.getDescription()+"\"," +
                "\"priority\": \""+evento.getPriority()+"\"" +
                "}";
        Call<BooleanResponse> call = ToStudyApiAdapter.getApiService().editEvents(
                data
        );
        call.enqueue(new Callback<BooleanResponse>() {

            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                BooleanResponse insertado = response.body();
                if (insertado.getResult().equals("true"))
                    interactor.onSuccess("Elemento editado");
                else
                    interactor.onFailure("Error al editar el evento");
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                interactor.onFailure("Error al editar el evento");
            }
        });
    }
}
