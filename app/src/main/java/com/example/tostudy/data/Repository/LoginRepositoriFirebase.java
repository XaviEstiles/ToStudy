package com.example.tostudy.data.Repository;

import static com.example.tostudy.apiservice.ToStudyApiAdapter.getApiService;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tostudy.R;
import com.example.tostudy.apiservice.ToStudyApiAdapter;
import com.example.tostudy.apiservice.dto.BooleanResponse;
import com.example.tostudy.apiservice.dto.UserResponse;
import com.example.tostudy.data.model.User;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.login.LoginContract;
import com.example.tostudy.ui.singup.SingUpActivity;
import com.example.tostudy.ui.singup.SingUpContract;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Call<UserResponse> call =  ToStudyApiAdapter.getApiService().getUserInfo(email);
                        call.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                UserResponse user = response.body();
                                interactor.onSuccess("usuario correcto", new User(user.getId(), user.getUser(), user.getEmail(), user.getImg()));
                            }
                            @Override
                            public void onFailure(Call<UserResponse> call, Throwable t) {
                                interactor.onFailure("Error de autenticacion");
                            }
                        });
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                        interactor.onFailure("Error de autenticacion" + task.getException());
                    }
                }
            });
    }

    public void SingUp(User user, String pass) {
        Log.d(TAG, user.getEmail());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Call<BooleanResponse> call =  ToStudyApiAdapter.getApiService().saveUser(
                            "{\"email\":\""+user.getEmail()+"\",\"name\":\""+user.getUser()+"\",\"img\":\""+user.getImg()+"\"}"
                    );
                    call.enqueue(new Callback<BooleanResponse>() {
                        @Override
                        public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                            BooleanResponse insertado = response.body();
                            if (insertado.getResult().equals("true"))
                                interactor.onSuccess("usuario regidtrado", new User(user.getUser(), user.getEmail(), user.getImg()));
                            else
                                interactor.onFailure("Error de autenticacion");
                        }

                        @Override
                        public void onFailure(Call<BooleanResponse> call, Throwable t) {
                            interactor.onFailure("Error de autenticacion");
                        }
                    });
                } else {
                    interactor.onFailure("Error de registro" + task.getException());
                }
            }
        });
    }

    public void editImgPerfil(User user, String img){
        Call<UserResponse> call =  ToStudyApiAdapter.getApiService().editUser(
                "{\"id\":\""+user.getId()+"\",\"email\":\""+user.getEmail()+"\",\"name\":\""+user.getUser()+"\",\"img\":\""+img+"\"}"
        );
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse editado = response.body();
                interactor.onSuccess("usuario regidtrado", new User(editado.getId(),editado.getUser(), editado.getEmail(), editado.getImg()));
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                interactor.onFailure("Error de autenticacion");
            }
        });
    }
}
