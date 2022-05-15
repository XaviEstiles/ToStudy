package com.example.tostudy.ui.eventos;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.eventos.EventoAdapter;
import com.example.tostudy.databinding.FragmentCalendarioBinding;
import com.example.tostudy.ui.objetivos.RecyclerItemTouchHelperObjetivos;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarioFragment extends Fragment implements EventoContract.View{

    EventoContract.Presenter presenter;
    NavController navController;
    FragmentCalendarioBinding binding;
    private EventoAdapter adapter;
    String dateString;
    Evento deleted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new EventoPresenter(this);
        binding = FragmentCalendarioBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Routine Calendar");
        toolbar.setVisibility(View.VISIBLE);
        binding.calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            dateString = String.format("%02d", i2)+"/"+String.format("%02d", (i1+1))+"/"+String.format("%04d", i);
            presenter.load(dateString);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRvEventos();
        navController = Navigation.findNavController(binding.getRoot());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateString = formatter.format(new Date(binding.calendarView.getDate()));
        presenter.load(dateString);

    }

    private void initRvEventos(){
        //1. sera inicializar adapter
        adapter = new EventoAdapter(new ArrayList<>());
        //2. obligatoriamente se debe indicar que layout tendra el recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        //3. Asignar el layout al recycler
        binding.rvEventos.setLayoutManager(linearLayoutManager);
        //4. Asignar a la vista sus datos
        binding.rvEventos.setAdapter(adapter);
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
        showData(list);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        adapter.delete(deleted);
        Snackbar.make(getView(),msg, BaseTransientBottomBar.LENGTH_SHORT).setAction("Desacer", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.undo(deleted);
                adapter.undo(deleted);
            }
        }).show();
    }

    @Override
    public void onSuccess(String msg) {
        //Snackbar.make(getActivity().findViewById(R.id.objetivoFragment),msg,BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public <T> void showData(List<T> list) {
        adapter.updateList((List<Evento>) list);
        if(list.size() < 5){
            stopScroll();
        }else{
            startScroll();
        }
    }

    @Override
    public void showOrder() {

    }

    @Override
    public void showNoData() {

    }

    public void stopScroll() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) binding.collapser.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(0);
        binding.collapser.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) binding.appBar.getLayoutParams();
        appBarLayoutParams.setBehavior(null);
        binding.appBar.setLayoutParams(appBarLayoutParams);
    }

    public void startScroll() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) binding.collapser.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        binding.collapser.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) binding.appBar.getLayoutParams();
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        binding.appBar.setLayoutParams(appBarLayoutParams);
    }
}