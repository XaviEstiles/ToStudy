package com.example.tostudy.ui.ajustes;

import static com.example.tostudy.utils.CommonUtils.convert;
import static com.example.tostudy.utils.CommonUtils.redimensionarImagen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tostudy.R;
import com.example.tostudy.data.model.User;
import com.example.tostudy.databinding.FragmentAjustesBinding;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.login.LoginActivity;
import com.example.tostudy.ui.main.MainActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AjustesFragment extends Fragment implements OnRepositoryCallBack {

    FragmentAjustesBinding binding;
    SharedPreferences prefs;
    Context context;
    AjustePresenter presenter;
    private static final int SELECT_FILE = 1;
    private String imgBase64 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        presenter = new AjustePresenter(this);
        binding = FragmentAjustesBinding.inflate(getLayoutInflater());
        prefs = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
        context = getContext();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding.tvEmail.setText(prefs.getString("Email","example@gmail.com"));
        binding.tvNombre.setText(prefs.getString("Name","example"));

        Glide.with(getContext())
                .load(prefs.getString("Img",""))
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfil);

        Glide.with(getContext())
                .load(R.drawable.ic_editprofile)
                .circleCrop()
                .into(binding.imgEdit);

        binding.btnCerrarSesion.setOnClickListener(v -> {
            prefs.edit().remove("Email").apply();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        if (prefs.getString("OrdenarEve","").equals("Fecha")){
            binding.swOrdEve.setChecked(false);
        }else {
            binding.swOrdEve.setChecked(true);
        }

        if (prefs.getString("OrdenarObj","").equals("Fecha")){
            binding.swOrdObj.setChecked(false);
        }else {
            binding.swOrdObj.setChecked(true);
        }

        binding.swOrdEve.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefs.edit().putString("OrdenarEve","Prioridad").apply();
            } else {
                prefs.edit().putString("OrdenarEve","Fecha").apply();
            }
        });

        binding.swOrdObj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                prefs.edit().putString("OrdenarObj","Prioridad").apply();
            } else {
                prefs.edit().putString("OrdenarObj","Fecha").apply();
            }
        });

        binding.imgPerfil.setOnClickListener(v -> {
            abrirGaleria(getView());
        });

        return binding.getRoot();
    }

    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

    public void onActivityResult(int requestCode, int resultCode,
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
                                imageStream = getActivity().getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                            bmp = redimensionarImagen(bmp);
                            imgBase64 = convert(bmp);
                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            presenter.editarImg(
                                    new User(
                                                Integer.parseInt(prefs.getString("IdUser","")),
                                                prefs.getString("Name",""),
                                                prefs.getString("Email",""),
                                                prefs.getString("Img","")
                                            ),
                                    imgBase64
                            );
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(String msg, User user) {
        prefs.edit().putString("IdUser", String.valueOf(user.getId())).apply();
        prefs.edit().putString("Email", user.getEmail()).apply();
        prefs.edit().putString("Name",user.getUser()).apply();
        prefs.edit().putString("Img",user.getImg()).apply();
        Glide.with(getContext())
                .load(user.getImg())
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfil);
        ((MainActivity)getActivity()).cambiarImg();
    }

    @Override
    public void onFailure(String msg) {

    }
}