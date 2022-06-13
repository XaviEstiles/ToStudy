package com.example.tostudy.ui.singup;

import static com.example.tostudy.utils.CommonUtils.convert;
import static com.example.tostudy.utils.CommonUtils.redimensionarImagen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tostudy.R;
import com.example.tostudy.data.model.User;
import com.example.tostudy.databinding.ActivitySingUpBinding;
import com.example.tostudy.ui.login.LoginActivity;
import com.example.tostudy.ui.main.MainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SingUpActivity extends AppCompatActivity implements SingUpContract.View{

    SingUpContract.Presenter presenter;
    ActivitySingUpBinding binding;
    private static final int SELECT_FILE = 1;
    private String imgBase64 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        presenter = new SingUpPresenter(this);
        setContentView(binding.getRoot());

        Glide.with(getApplicationContext())
                .load(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfil);

        Glide.with(getApplicationContext())
                .load(R.drawable.ic_editprofile)
                .circleCrop()
                .into(binding.imgEdit);

        binding.imgPerfil.setOnClickListener(v->{
            abrirGaleria(binding.getRoot());
        });

        binding.btnRegistrarse.setOnClickListener(view -> {
            presenter.validateCredentials(binding.tieNom.getText().toString().trim(),
                                        binding.tieEmail.getText().toString().trim(),
                                        binding.tiePassword.getText().toString(),
                                        binding.tieRePassword.getText().toString(),
                                        imgBase64
            );
        });
    }

    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                            bmp = redimensionarImagen(bmp);
                            imgBase64 = convert(bmp);
                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            Glide.with(getApplicationContext())
                                    .load(bmp)
                                    .error(R.drawable.imgperfil)
                                    .circleCrop()
                                    .into(binding.imgPerfil);
                        }
                    }
                }
                break;
        }
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
        binding.tilEmail.setError("Formato no valido.");
    }

    @Override
    public void setPassErr() {
        binding.tilPassword.setError("Formato no valido. Debe contener ");
    }

    @Override
    public void setPassDontMach() {
        binding.tilRePassword.setError("Las contraseñas no coinciden.");
    }
}