package com.example.tostudy.ui.objetivos;

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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.databinding.FragmentObjetivosBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ObjetivosFragment extends Fragment implements ObjetivoAdapter.OnManagerObjetivosList, ObjetivoContract.View, RecyclerItemTouchHelperObjetivos.RecyclerItemTouchHelperListener {

    NavController navController;
    ObjetivoContract.Presenter presenter;
    ObjetivoAdapter adapter;
    FragmentObjetivosBinding binding;
    Objetivo deleted;
    SharedPreferences pref;

    boolean resultado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentObjetivosBinding.inflate(getLayoutInflater());
        presenter = new ObjetivoPresenter(this);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelperObjetivos(20, ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvObjetivos);

        binding.fab.setOnClickListener(v->{
            navController.navigate(R.id.action_objetivoFragment_to_editarObjetivoFragment);
            ((Toolbar)getActivity().findViewById(R.id.toolbar)).setTitle("ToStudy");
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        return binding.getRoot();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniAdapter();
        pref = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
        presenter.load(pref.getString("IdUser",""));
    }

    void iniAdapter(){
        adapter = new ObjetivoAdapter(new ArrayList<>(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        binding.rvObjetivos.setLayoutManager(linearLayoutManager);
        binding.rvObjetivos.setAdapter(adapter);
    }

    @Override
    public void showInfo(Objetivo objetivo) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("objetivo", objetivo);
        navController.navigate(R.id.action_objetivoFragment_to_infoObjetivosFragment,bundle);

    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {
        showData(list);
    }

    @Override
    public void onDeleteSuccess(String msg) {
        adapter.delete(deleted);
        if(adapter.getList() == null || adapter.getList().size() == 0){
            showNoData();
        }else{
            binding.rvObjetivos.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.INVISIBLE);
        }
        Snackbar.make(getView(),msg, BaseTransientBottomBar.LENGTH_SHORT).setAction("Desacer", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.undo(deleted);
                adapter.undo(deleted);
                binding.rvObjetivos.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.INVISIBLE);
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
        if(list == null || list.size() == 0){
            showNoData();
        }else{
            binding.rvObjetivos.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.INVISIBLE);
        }

        adapter.updateList((List<Objetivo>) list);
        if(list.size() < 6){
            stopScroll();
        }else{
            startScroll();
        }
        if (pref.getString("OrdenarObj","").equals("Fecha")){
            adapter.orderByFecha();
        }else {
            adapter.orderByPrioridad();
        }
    }

    @Override
    public void showNoData() {

        binding.rvObjetivos.setVisibility(View.INVISIBLE);
        binding.noData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        ArrayList<Objetivo> list = adapter.getList();
        Objetivo objetivo = list.get(viewHolder.getAdapterPosition());
        if (direction == 4){
            deleted = objetivo;
            presenter.delete(objetivo);
        }else if (direction == 8){
            getActivity().findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj",objetivo);
            navController.navigate(R.id.action_objetivoFragment_to_editarObjetivoFragment,bundle);
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