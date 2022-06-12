package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.apiservice.ToStudyApiAdapter;
import com.example.tostudy.apiservice.dto.BooleanResponse;
import com.example.tostudy.apiservice.dto.ObjetivoResponse;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;
import com.example.tostudy.ui.objetivos.ObjetivosManage.ObjManageContract;
import com.example.tostudy.ui.objetivos.ObjetivoContract;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjetivoRepositoryRoom implements ObjetivoContract.Repository, ObjManageContract.Repository {
    ArrayList<Objetivo> objetivos;
    OnRepositoryListCallBack interactor;
    static ObjetivoRepositoryRoom repository;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ObjetivoRepositoryRoom() {
        objetivos = new ArrayList<>();
        //objetivoDao = Database.getDatabase().objetivoDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ObjetivoRepositoryRoom getInstance(OnRepositoryListCallBack interactor){
        if (repository == null){
            repository = new ObjetivoRepositoryRoom();
        }
        repository.interactor = interactor;
        return repository;
    }

    @Override
    public void load(String userId) {
        Call<ObjetivoResponse> call = ToStudyApiAdapter.getApiService().getObjetives(userId);
        call.enqueue(new Callback<ObjetivoResponse>() {

            @Override
            public void onResponse(Call<ObjetivoResponse> call, Response<ObjetivoResponse> response) {
                objetivos = response.body().getResult();
                interactor.onSuccessLoad(objetivos);
            }

            @Override
            public void onFailure(Call<ObjetivoResponse> call, Throwable t) {
                interactor.onFailure("Error al cargar");
            }
        });
    }

    @Override
    public void delete(Objetivo objetivo) {
        Call<BooleanResponse> call = ToStudyApiAdapter.getApiService().deleteObjetive(String.valueOf(objetivo.getId()));
        call.enqueue(new Callback<BooleanResponse>() {

            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                BooleanResponse insertado = response.body();
                if (insertado.getResult().equals("true"))
                    interactor.onDeleteSuccess("Elemento eliminado");
                else
                    interactor.onFailure("Error al eliminar el objetivo");
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                interactor.onFailure("Error al eliminar");
            }
        });
    }

    @Override
    public void undo(Objetivo objetivo) {
        add(objetivo);
    }


    @Override
    public void add(Objetivo objetivo) {
        String data = "{" +
                "\"userId\": \""+objetivo.getUserId()+"\"," +
                "\"name\": \""+objetivo.getName()+"\"," +
                "\"date\": \""+objetivo.getDate()+"\"," +
                "\"description\": \""+objetivo.getDescription()+"\"," +
                "\"priority\": \""+objetivo.getPriority()+"\"," +
                "\"progress\": \""+objetivo.getProgress()+"\"" +
                "}";
        Call<BooleanResponse> call = ToStudyApiAdapter.getApiService().saveObjetive(
                data
        );
        call.enqueue(new Callback<BooleanResponse>() {

            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                BooleanResponse insertado = response.body();
                if (insertado.getResult().equals("true"))
                    interactor.onSuccess("Elemento añadido");
                else
                    interactor.onFailure("Error al guardar el objetivo");
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                interactor.onFailure("Error al guardar el objetivo");
            }
        });
    }

    @Override
    public void edit(Objetivo objetivo) {
        String data = "{" +
                "\"id\": \""+objetivo.getId()+"\"," +
                "\"userId\": \""+objetivo.getUserId()+"\"," +
                "\"name\": \""+objetivo.getName()+"\"," +
                "\"date\": \""+objetivo.getDate()+"\"," +
                "\"description\": \""+objetivo.getDescription()+"\"," +
                "\"priority\": \""+objetivo.getPriority()+"\"," +
                "\"progress\": \""+objetivo.getProgress()+"\"" +
                "}";
        Call<BooleanResponse> call = ToStudyApiAdapter.getApiService().editObjetive(
                data
        );
        call.enqueue(new Callback<BooleanResponse>() {

            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                BooleanResponse insertado = response.body();
                if (insertado.getResult().equals("true"))
                    interactor.onSuccess("Elemento editado");
                else
                    interactor.onFailure("Error al editar el objetivo");
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                interactor.onFailure("Error al editar el objetivo");
            }
        });
    }
}
