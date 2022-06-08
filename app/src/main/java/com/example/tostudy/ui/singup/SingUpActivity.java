package com.example.tostudy.ui.singup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tostudy.data.model.User;
import com.example.tostudy.databinding.ActivitySingUpBinding;
import com.example.tostudy.ui.login.LoginActivity;
import com.example.tostudy.ui.main.MainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SingUpActivity extends AppCompatActivity implements SingUpContract.View{

    SingUpContract.Presenter presenter;
    ActivitySingUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        presenter = new SingUpPresenter(this);
        setContentView(binding.getRoot());


        binding.btnRegistrarse.setOnClickListener(view -> {
            presenter.validateCredentials(binding.tieNom.getText().toString(),
                                        binding.tieEmail.getText().toString(),
                                        binding.tiePassword.getText().toString(),
                                        binding.tieRePassword.getText().toString());
            //startActivity(new Intent(this, LoginActivity.class));
            //finishAffinity();
        });
    }

    @Override
    public void onSuccess(String msg, User user) {
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    @Override
    public void onFailure(String msg) {
        Snackbar.make(binding.getRoot(),msg, BaseTransientBottomBar.LENGTH_LONG);
    }

    @Override
    public void setNomEmpty() {
        binding.tilNom.setError("El nombre no puede ser vacio.");
    }

    @Override
    public void setEmailEmpty() {
        binding.tilEmail.setError("El email no puede ser vacio.");
    }

    @Override
    public void setPassEmpty() {
        binding.tilPassword.setError("La contraseña no puede ser vacia.");
    }

    @Override
    public void setEmailErr() {
        binding.tilNom.setError("Formato no valido.");
    }

    @Override
    public void setPassErr() {
        binding.tilNom.setError("Formato no valido.");
    }

    @Override
    public void setPassDontMach() {
        binding.tilRePassword.setError("Las contraseñas no coinciden.");
    }
}