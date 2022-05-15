package com.example.tostudy.data.Repository;

import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.login.LoginContract;

import java.util.ArrayList;

public class LoginRepository implements LoginContract.Repository  {
    private static LoginRepository repository;
    private OnRepositoryCallBack interactor;

    private ArrayList<String[]> usuarios;

    public LoginRepository() {
        usuarios =new ArrayList<>();
        usuarios.add(new String[]{"xa@xa.com","Xavi.1718"});
    }

    public static LoginRepository instance(OnRepositoryCallBack interactor){
        if (repository == null)
            repository = new LoginRepository();
        repository.interactor = interactor;
        return repository;
    }

    @Override
    public void login(String email, String pass) {
        for (String[] s: usuarios){
            if (s[0].equals(email)&&s[1].equals(pass)){
                interactor.onSuccess("Usuario correcto");
                return;
            }
        }
        interactor.onFailure("Usuario incorrecto");
    }
}
