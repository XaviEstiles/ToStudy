package com.example.tostudy.ui.objetivos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.model.Objetivo;

import java.util.List;

public class ObjetivoPresenter implements ObjetivoContract.Presenter{
    ObjetivoContract.View view;
    ObjetivoInteractor interactor;

    public ObjetivoPresenter(ObjetivoContract.View view) {
        this.view = view;
        this.interactor = new ObjetivoInteractor(this);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
        view.showData(list);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        view.onDeleteSuccess(msg);
    }

    @Override
    public void onSuccess(String msg) {
        view.onSuccess(msg);
    }

    @Override
    public void onFailure(String msg) {
        view.onFailure(msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void load(String userId) {
        interactor.load(userId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void undo(Objetivo objetivo) {
        interactor.undo(objetivo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void editProgres(Objetivo objetivo) {
        interactor.editProgres(objetivo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void delete(Objetivo objetivo) {
        interactor.delete(objetivo);
    }

    @Override
    public void order() {
        view.showOrder();
    }
}
