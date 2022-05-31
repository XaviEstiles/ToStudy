package com.example.tostudy.ui.objetivos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.databinding.FragmentObjetivosBinding;
import com.example.tostudy.ui.base.BaseDialogFragment;
import com.example.tostudy.ui.objetivos.ObjetivosManage.ObjManageContract;
import com.example.tostudy.ui.objetivos.ObjetivosManage.ObjManagePresenter;
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
        presenter.load();

        pref = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
        if (pref.getString("OrdenarObj","").equals("fecha")){
            adapter.orderByFecha();
        }else {
            adapter.orderByPrioridad();
        }
    }

    void iniAdapter(){
        adapter = new ObjetivoAdapter(new ArrayList<>(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        binding.rvObjetivos.setLayoutManager(linearLayoutManager);
        binding.rvObjetivos.setAdapter(adapter);
    }

    @Override
    public void onEditProgres(Objetivo objetivo) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("objetivo", objetivo);
        navController.navigate(R.id.action_objetivoFragment_to_infoObjetivosFragment,bundle);

        /*resultado = false;
        presenter.editProgres(objetivo);
        if(objetivo.getProgreso() == 5){
            //dialogo para eliminarlo
            Bundle bundle = new Bundle();
            bundle.putString(BaseDialogFragment.TITLE,"Objetivo Completado");
            bundle.putString(BaseDialogFragment.MESSAGE,"El objetivo "+objetivo.getNombre()+" ha sido cumplido. ¿Desea eliminarlo?");
            getActivity().getSupportFragmentManager().setFragmentResultListener(BaseDialogFragment.KEY, this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    if (result.getBoolean(BaseDialogFragment.KEY_BUNDLE)) {
                        deleted = objetivo;
                        presenter.delete(objetivo);
                    }
                }
            });
            NavHostFragment.findNavController(this).navigate(R.id.action_objetivoFragment_to_baseDialogFragment,bundle);
        }*/
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
        adapter.updateList((List<Objetivo>) list);
        if(list.size() < 6){
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