package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.Database;
import com.example.tostudy.data.dao.ObjetivoDao;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;
import com.example.tostudy.ui.objetivos.ObjetivosManage.ObjManageContract;
import com.example.tostudy.ui.objetivos.ObjetivosManage.ObjManageInteractor;
import com.example.tostudy.ui.objetivos.ObjetivoContract;
import com.example.tostudy.ui.objetivos.ObjetivoInteractor;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ObjetivoRepositoryRoom implements ObjetivoContract.Repository, ObjManageContract.Repository {
    ArrayList<Objetivo> objetivos;
    OnRepositoryListCallBack interactor;
    static ObjetivoRepositoryRoom repository;
    private ObjetivoDao objetivoDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ObjetivoRepositoryRoom() {
        objetivos = new ArrayList<>();
        objetivoDao = Database.getDatabase().objetivoDao();
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
        try {
            objetivos = (ArrayList<Objetivo>) Database.databaseWriteExecutor.submit(()->objetivoDao.select()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        interactor.onSuccessLoad(objetivos);
    }

    @Override
    public void delete(Objetivo objetivo) {
        Database.databaseWriteExecutor.submit(()->objetivoDao.delete(objetivo));
        interactor.onDeleteSuccess(objetivo.getNombre()+" eliminado.");
    }

    @Override
    public void undo(Objetivo objetivo) {
        Database.databaseWriteExecutor.submit(()->objetivoDao.insert(objetivo));
        interactor.onSuccess("Elemento recuperado");
    }


    @Override
    public void add(Objetivo objetivo) {
        Database.databaseWriteExecutor.submit(()->objetivoDao.insert(objetivo));
        interactor.onSuccess("Elemento añadido");
    }

    @Override
    public void edit(Objetivo objetivo) {
        Database.databaseWriteExecutor.submit(()->objetivoDao.update(objetivo));
        interactor.onSuccess("Elemento editado");
    }
}
