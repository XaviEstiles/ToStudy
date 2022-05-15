package com.example.tostudy.data.Repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.data.model.Prioridad;
import com.example.tostudy.ui.objetivos.ObjetivoContract;
import com.example.tostudy.ui.objetivos.ObjetivoInteractor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ObjetivoRepository implements ObjetivoContract.Repository {
    ArrayList<Objetivo> objetivos;
    ObjetivoInteractor interactor;
    static ObjetivoRepository repository;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ObjetivoRepository(ObjetivoInteractor interactor) {
        objetivos = new ArrayList<Objetivo>();
        this.interactor = interactor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ObjetivoRepository getInstance(ObjetivoInteractor interactor){
        if (repository == null){
            repository = new ObjetivoRepository(interactor);
        }
        repository.interactor = interactor;
        return repository;
    }

    @Override
    public void load() {
        interactor.onSuccessLoad(objetivos);
    }

    /*@Override
    public void edit(Objetivo objetivo) {
        for (Objetivo o : objetivos){
            if (o.getId().equals(objetivo.getId())){
                objetivos.remove(o);
                break;
            }
        }
        objetivos.add(objetivo);
        interactor.onSuccess("Elemento editado");
    }*/

    @Override
    public void delete(Objetivo objetivo) {
        objetivos.remove(objetivo);
        interactor.onDeleteSuccess(objetivo.getNombre()+" eliminado.");
    }

    @Override
    public void undo(Objetivo objetivo) {
        objetivos.add(objetivo);
        interactor.onSuccess("Elemento recuperado");
    }


}
