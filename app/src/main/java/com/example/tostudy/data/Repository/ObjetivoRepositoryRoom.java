package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.apiservice.ToStudyApiAdapter;
import com.example.tostudy.apiservice.dto.ObjetivoResponse;
import com.example.tostudy.data.model.Objetivo;
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
    public void load() {
        Call<ObjetivoResponse> call = ToStudyApiAdapter.getApiService().getObjetives("1");
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
        //Database.databaseWriteExecutor.submit(()->objetivoDao.delete(objetivo));
        interactor.onDeleteSuccess(objetivo.getName()+" eliminado.");
    }

    @Override
    public void undo(Objetivo objetivo) {
        //Database.databaseWriteExecutor.submit(()->objetivoDao.insert(objetivo));
        interactor.onSuccess("Elemento recuperado");
    }


    @Override
    public void add(Objetivo objetivo) {
        //Database.databaseWriteExecutor.submit(()->objetivoDao.insert(objetivo));
        interactor.onSuccess("Elemento añadido");
    }

    @Override
    public void edit(Objetivo objetivo) {
        //Database.databaseWriteExecutor.submit(()->objetivoDao.update(objetivo));
        interactor.onSuccess("Elemento editado");
    }
}
