package com.example.tostudy.ui.singup;

import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryCallBack;

public class SingUpPresenter implements SingUpContract.Presenter{

    SingUpContract.View view;
    SingUpInteractor interactor;

    public SingUpPresenter(SingUpContract.View view) {
        this.view = view;
        this.interactor = new SingUpInteractor(this);
    }

    @Override
    public void onSuccess(String msg, User user) {
        view.onSuccess(msg,user);
    }

    @Override
    public void onFailure(String msg) {
        view.onFailure(msg);
    }

    @Override
    public void onNomEmpty() {
        view.setNomEmpty();
    }

    @Override
    public void onEmailEmpty() {
        view.setEmailEmpty();
    }

    @Override
    public void onPassEmpty() {
        view.setPassEmpty();
    }

    @Override
    public void onEmailErr() {
        view.setEmailErr();
    }

    @Override
    public void onPassErr() {
        view.setPassErr();
    }

    @Override
    public void onPassDontMach() {
        view.setPassDontMach();
    }

    @Override
    public void validateCredentials(String nomUser, String email, String pass, String confirmPass,String img) {
        interactor.validateCredential(nomUser, email, pass, confirmPass, img);
    }
}
