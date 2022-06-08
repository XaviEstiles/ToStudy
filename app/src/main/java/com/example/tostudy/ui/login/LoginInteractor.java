package com.example.tostudy.ui.login;

import android.util.Log;
import android.util.Patterns;

import com.example.tostudy.data.Repository.LoginRepositoriFirebase;
import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.utils.CommonUtils;

public class LoginInteractor implements OnRepositoryCallBack {

    private LoginContract.Presenter presenter;

    public LoginInteractor(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    void validateCredentials(String email, String pass){

        if(email.equals("")){
            presenter.onEmailEmpty();
            return;
        }
        if(pass.equals("")){
            presenter.onPasswordEmpty();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            presenter.onEmailErr();
            return;
        }
        if(!CommonUtils.isPasswordValid(pass)){
            presenter.onPasswordErr();
            return;
        }
        Log.d("correcto","loguin");
        LoginRepositoriFirebase.getInstance(this).login(email, pass);
    }

    @Override
    public void onSuccess(String msg, User user) {
        presenter.onSuccess(msg,user);
    }

    @Override
    public void onFailure(String msg) {
        presenter.onFailure(msg);
    }
}
