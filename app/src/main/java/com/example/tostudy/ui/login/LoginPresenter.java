package com.example.tostudy.ui.login;

import com.example.tostudy.data.model.User;

public class LoginPresenter implements LoginContract.Presenter{

    private LoginInteractor interactor;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.interactor = new LoginInteractor(this);
        this.view = view;
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
    public void validateCredentials(String email, String pass) {
        interactor.validateCredentials(email, pass);
    }

    @Override
    public void onEmailErr() {
        view.setEmailErr();
    }

    @Override
    public void onEmailEmpty() {
        view.setEmailEmpty();
    }

    @Override
    public void onPasswordErr() {
        view.setPasswordErr();
    }

    @Override
    public void onPasswordEmpty() {
        view.setPasswordEmpty();
    }
}
