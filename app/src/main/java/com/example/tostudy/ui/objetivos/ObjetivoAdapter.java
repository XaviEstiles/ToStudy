package com.example.tostudy.ui.objetivos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tostudy.R;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.data.model.OjetivoComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjetivoAdapter extends RecyclerView.Adapter<ObjetivoAdapter.ViewHolder> {

    private ArrayList<Objetivo> list;
    OnManagerObjetivosList listener;

    public interface OnManagerObjetivosList{
        void showInfo(Objetivo objetivo);
    }

    public ObjetivoAdapter(ArrayList<Objetivo> list, OnManagerObjetivosList listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objetivo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getName());
        holder.tvFecha.setText(list.get(position).getDate());
        holder.pro.setProgress((int)list.get(position).getProgress());

        switch (list.get(position).getPriority()){
            case 1:
                holder.pri.setImageResource(R.drawable.ic_importancia_baja);
                break;
            case 2:
                holder.pri.setImageResource(R.drawable.ic_importancia_media);
                break;
            case 3:
                holder.pri.setImageResource(R.drawable.ic_importancia_alta);
                break;
        }
        holder.bind(listener, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tv;
        TextView tvFecha;
        ImageView pri;
        ProgressBar pro;
        RelativeLayout layoutABorrar;
        RelativeLayout eliminar;
        RelativeLayout editar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tv = itemView.findViewById(R.id.tv);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            pri = itemView.findViewById(R.id.imgPrioridad);
            pro = itemView.findViewById(R.id.pbProgreso);
            layoutABorrar = itemView.findViewById(R.id.layoutABorrar);
            eliminar = itemView.findViewById(R.id.eliminar);
            editar = itemView.findViewById(R.id.editar);
        }
        public void bind(OnManagerObjetivosList listener, Objetivo item){
            itemView.setOnClickListener(v->{
                listener.showInfo(item);
            });
        }
    }

    public ArrayList<Objetivo> getList() {
        return list;
    }

    public void updateList(List<Objetivo> list){
        this.list.clear();
        this.list.addAll(list);
        orderByFecha();
        notifyDataSetChanged();
    }

    public void delete(Objetivo objetivo){
        list.remove(objetivo);
        notifyDataSetChanged();
    }

    public void undo(Objetivo objetivo) {
        list.add(objetivo);
        orderByFecha();
        notifyDataSetChanged();
    }

    public void edit(Objetivo objetivo){
        for (Objetivo o : list){
            if (o.getId().equals(objetivo.getId())){
                list.remove(o);
                break;
            }
        }
        list.add(objetivo);
        orderByFecha();
        notifyDataSetChanged();
    }

    public void orderByFecha(){
        Collections.sort(list);
        notifyDataSetChanged();
    }
    public void orderByPrioridad(){
        Collections.sort(list,new OjetivoComparator());
        notifyDataSetChanged();
    }
}
