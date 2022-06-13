package com.example.tostudy.ui.singup;

import android.util.Patterns;

import com.example.tostudy.data.Repository.LoginRepositoriFirebase;
import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.utils.CommonUtils;

public class SingUpInteractor implements OnRepositoryCallBack {

    SingUpContract.Presenter presenter;

    public SingUpInteractor(SingUpContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void validateCredential(String nomUser, String email, String pass, String confirmPass, String img){
        if(nomUser.isEmpty()){
            presenter.onNomEmpty();
            return;
        }
        if(email.isEmpty()){
            presenter.onEmailEmpty();
            return;
        }
        if(pass.isEmpty()){
            presenter.onPassEmpty();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            presenter.onEmailErr();
            return;
        }
        if(!CommonUtils.isPasswordValid(pass)){
            presenter.onPassErr();
            return;
        }
        if(!pass.equals(confirmPass)){
            presenter.onPassDontMach();
            return;
        }


        LoginRepositoriFirebase.getInstance(this).SingUp(new User(nomUser,email,img), pass);
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
