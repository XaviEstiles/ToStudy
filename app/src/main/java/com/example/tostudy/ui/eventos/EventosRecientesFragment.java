package com.example.tostudy.ui.eventos;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.eventos.EventoAdapter;
import com.example.tostudy.databinding.FragmentEventosRecientesBinding;
import com.example.tostudy.ui.objetivos.RecyclerItemTouchHelperObjetivos;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class EventosRecientesFragment extends Fragment implements EventoContract.View, RecyclerItemTouchHelperEventos.RecyclerItemTouchHelperListener {
    
    EventoContract.Presenter presenter;
    private EventoAdapter adapter;
    NavController navController;
    private FragmentEventosRecientesBinding binding;
    Evento editado;
    Evento deleted;
    SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new EventoPresenter(this);
        binding = FragmentEventosRecientesBinding.inflate(getLayoutInflater());


        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelperEventos(20, ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvEventos2);

        binding.fab.setOnClickListener(v->{
            navController.navigate(R.id.action_eventosRecientesFragment_to_editarEventoFragment);
            ((Toolbar)getActivity().findViewById(R.id.toolbar)).setTitle("ToStudy");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(binding.getRoot());
        initRvEventos();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragmenlist_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_ordenar_fecha:
                adapter.orderByFecha();
                break;
            case R.id.action_ordenar_prioridad:
                adapter.orderByPrioridad();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        pref = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
        if (pref.getString("OrdenarEve","").equals("Fecha")){
            adapter.orderByFecha();
        }else {
            adapter.orderByPrioridad();
        }
    }

    private void initRvEventos(){
        //1. sera inicializar adapter
        adapter = new EventoAdapter(new ArrayList<>());
        //2. obligatoriamente se debe indicar que layout tendra el recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        //3. Asignar el layout al recycler
        binding.rvEventos2.setLayoutManager(linearLayoutManager);
        //4. Asignar a la vista sus datos
        binding.rvEventos2.setAdapter(adapter);
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
        if(list.size() < 7){
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

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //Log.d("Direction",String.valueOf(direction));
        ArrayList<Evento> list = adapter.getList();
        Evento evento = list.get(viewHolder.getAdapterPosition());
        if (direction == 4){
            deleted = evento;
            presenter.delete(evento);
        }else if (direction == 8){
            getActivity().findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
            Bundle bundle = new Bundle();
            bundle.putSerializable("eve",evento);
            navController.navigate(R.id.action_eventosRecientesFragment_to_editarEventoFragment,bundle);
        }

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