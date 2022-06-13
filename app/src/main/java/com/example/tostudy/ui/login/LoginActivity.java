package com.example.tostudy.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.tostudy.data.model.User;
import com.example.tostudy.databinding.ActivityLoginBinding;
import com.example.tostudy.ui.main.MainActivity;
import com.example.tostudy.ui.singup.SingUpActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private ActivityLoginBinding binding;
    private LoginContract.Presenter presenter;
    SharedPreferences prefs;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        presenter = new LoginPresenter(this);
        prefs = getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
        email = prefs.getString("Email", null);

        if (email != null ){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            presenter.validateCredentials(binding.tieUser.getText().toString().trim(),binding.tiePassword.getText().toString());
        });

        binding.btnRegistrar.setOnClickListener(view -> {
            startActivity(new Intent(this, SingUpActivity.class));
        });

    }

    @Override
    public void onSuccess(String msg, User user) {
        prefs.edit().putString("IdUser", String.valueOf(user.getId())).apply();
        prefs.edit().putString("Email", user.getEmail()).apply();
        prefs.edit().putString("Name",user.getUser()).apply();
        prefs.edit().putString("Img",user.getImg()).apply();

        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setEmailErr() {
        binding.tilUser.setError("Email erroneo");
    }

    @Override
    public void setEmailEmpty() {
        binding.tilUser.setError("Email vacio");
    }

    @Override
    public void setPasswordErr() {
        binding.tilPassword.setError("Contraseña erronea");
    }

    @Override
    public void setPasswordEmpty() {
        binding.tilPassword.setError("Contraseña vacia");
    }
}