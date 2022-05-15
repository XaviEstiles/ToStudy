package com.example.tostudy.ui.singup;

import com.example.tostudy.ui.base.OnRepositoryCallBack;

public interface SingUpContract {
    interface View extends OnRepositoryCallBack {
        void setNomEmpty();
        void setEmailEmpty();
        void setPassEmpty();
        void setEmailErr();
        void setPassErr();
        void setPassDontMach();
    }
    interface Interactor extends  OnRepositoryCallBack{
        void onNomEmpty();
        void onEmailEmpty();
        void onPassEmpty();
        void onEmailErr();
        void onPassErr();
        void onPassDontMach();
    }
    interface Presenter extends Interactor {
        void validateCredentials(String nomUser, String email, String pass, String confirmPass);
    }
}
