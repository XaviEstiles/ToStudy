package com.example.tostudy.ui.ajustes;

import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryCallBack;


public class AjustePresenter implements OnRepositoryCallBack {

    AjusteInteractor interactor;
    AjustesFragment view;

    public AjustePresenter(AjustesFragment view) {
        this.view = view;
        this.interactor = new AjusteInteractor(this);
    }

    public void editarImg(User user, String img) {
        interactor.editImg(user,img);
    }

    @Override
    public void onSuccess(String msg, User user) {
        view.onSuccess(msg, user);
    }

    @Override
    public void onFailure(String msg) {
        view.onFailure(msg);
    }
}
