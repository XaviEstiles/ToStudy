package com.example.tostudy.ui.objetivos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.Repository.ObjetivoRepository;
import com.example.tostudy.data.Repository.ObjetivoRepositoryRoom;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

import java.util.List;

public class ObjetivoInteractor implements OnRepositoryListCallBack {

    ObjetivoContract.Presenter presenter;

    public ObjetivoInteractor(ObjetivoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void load(){
        ObjetivoRepositoryRoom.getInstance(this).load();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void delete(Objetivo objetivo){
        ObjetivoRepositoryRoom.getInstance(this).delete(objetivo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void editProgres(Objetivo objetivo){
        ObjetivoRepositoryRoom.getInstance(this).edit(objetivo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void undo(Objetivo objetivo){
        ObjetivoRepositoryRoom.getInstance(this).undo(objetivo);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
        presenter.onSuccessLoad(list);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        presenter.onDeleteSuccess(msg);
    }

    @Override
    public void onSuccess(String msg) {
        presenter.onSuccess(msg);
    }

    @Override
    public void onFailure(String msg) {
        presenter.onFailure(msg);
    }

}
