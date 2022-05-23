package com.example.tostudy.data.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.login.LoginContract;
import com.example.tostudy.ui.singup.SingUpActivity;
import com.example.tostudy.ui.singup.SingUpContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepositoriFirebase implements LoginContract.Repository {

    private static final String TAG = LoginRepositoriFirebase.class.getName();
    static LoginRepositoriFirebase repository;
    OnRepositoryCallBack interactor;

    public static LoginRepositoriFirebase getInstance(OnRepositoryCallBack interactor) {
        if (repository == null) {
            repository = new LoginRepositoriFirebase();
        }
        repository.interactor = interactor;
        return repository;
    }

    @Override
    public void login(String email, String pass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "signInWithCustomToken:success");

        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        interactor.onSuccess("usuario correcto");
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                        interactor.onFailure("Error de autenticacion" + task.getException());

                    }
                }
            });
    }

    public void SingUp(String email, String pass) {
        Log.d(TAG, email);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    interactor.onSuccess("usuario creado");
                } else {
                    interactor.onFailure("Error de registro" + task.getException());
                }
            }
        });
    }
}
