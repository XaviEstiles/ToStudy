package com.example.tostudy.ui.login;

import com.example.tostudy.ui.base.OnRepositoryCallBack;

public interface LoginContract {
    interface View extends OnRepositoryCallBack {
        void setEmailErr();
        void setEmailEmpty();
        void setPasswordErr();
        void setPasswordEmpty();
    }

    interface Presenter extends Interactor{
        void validateCredentials(String email, String pass);
    }

    interface Interactor extends OnRepositoryCallBack{
        void onEmailErr();
        void onEmailEmpty();
        void onPasswordErr();
        void onPasswordEmpty();
    }

    interface Repository{
        void login(String email, String pass);
    }
}
