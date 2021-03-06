package com.example.tostudy.ui.objetivos;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemTouchHelperObjetivos extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelperObjetivos(int dragDirs, int swipeDirs,
                                            RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            View foregroundView = ((ObjetivoAdapter.ViewHolder) viewHolder).layoutABorrar;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {



        View foregroundView = ((ObjetivoAdapter.ViewHolder) viewHolder).layoutABorrar;

        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX,dY,
                actionState,isCurrentlyActive);

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((ObjetivoAdapter.ViewHolder) viewHolder).layoutABorrar;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        if (dX < 0){
            ((ObjetivoAdapter.ViewHolder) viewHolder).editar.setVisibility(View.GONE);
            ((ObjetivoAdapter.ViewHolder) viewHolder).eliminar.setVisibility(View.VISIBLE);
        }
        if (dX > 0){
            ((ObjetivoAdapter.ViewHolder) viewHolder).eliminar.setVisibility(View.GONE);
            ((ObjetivoAdapter.ViewHolder) viewHolder).editar.setVisibility(View.VISIBLE);
        }
        View foregroundView = ((ObjetivoAdapter.ViewHolder) viewHolder).layoutABorrar;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);

    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwipe(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }


}