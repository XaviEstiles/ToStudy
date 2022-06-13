package com.example.tostudy.ui.ajustes;

import com.example.tostudy.data.Repository.LoginRepositoriFirebase;
import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryCallBack;

public class AjusteInteractor implements OnRepositoryCallBack {

    AjustePresenter presenter;

    public AjusteInteractor(AjustePresenter presenter) {
        this.presenter = presenter;
    }

    void editImg(User user, String img){
        LoginRepositoriFirebase.getInstance(this).editImgPerfil(user,img);
    }

    @Override
    public void onSuccess(String msg, User user) {
        presenter.onSuccess(msg, user);
    }

    @Override
    public void onFailure(String msg) {
        presenter.onFailure(msg);
    }
}
