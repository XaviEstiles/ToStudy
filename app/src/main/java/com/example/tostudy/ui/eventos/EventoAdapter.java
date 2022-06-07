package com.example.tostudy.ui.eventos;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tostudy.R;
import com.example.tostudy.data.model.EventComparator;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.data.model.OjetivoComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    interface OnManagerEventosList {
        void showInfo(Evento evento);
    }
    
    private ArrayList<Evento> list;
    private OnManagerEventosList listener;

    public EventoAdapter(ArrayList<Evento> list,OnManagerEventosList listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evento_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoAdapter.ViewHolder holder, int position) {
        holder.tvNombre.setText(list.get(position).getNombre());
        holder.tvFecha.setText(list.get(position).getFecha());
        holder.tvHoraIni.setText(list.get(position).getHoraIni());
        holder.tvHoraFin.setText(list.get(position).getHoraFin());

        switch (list.get(position).getPrioridad()){
            case BAJA:
                holder.p.setImageResource(R.drawable.ic_importancia_baja);
                break;
            case MEDIA:
                holder.p.setImageResource(R.drawable.ic_importancia_media);
                break;
            case ALTA:
                holder.p.setImageResource(R.drawable.ic_importancia_alta);
                break;
        }

        holder.bind(list.get(position),listener);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvFecha;
        TextView tvHoraIni;
        TextView tvHoraFin;
        ImageView p;
        RelativeLayout layoutABorrar;
        RelativeLayout eliminar;
        RelativeLayout editar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre =itemView.findViewById(R.id.tv);
            tvFecha =itemView.findViewById(R.id.tvFecha);
            tvHoraIni =itemView.findViewById(R.id.tvHoraIni);
            tvHoraFin =itemView.findViewById(R.id.tvHoraFin);
            p = itemView.findViewById(R.id.imgPrioridad);
            layoutABorrar = itemView.findViewById(R.id.layoutABorrar);
            eliminar = itemView.findViewById(R.id.eliminar);
            editar = itemView.findViewById(R.id.editar);

        }
        public void bind(Evento evento, OnManagerEventosList listener){
            itemView.setOnClickListener(v-> listener.showInfo(evento));
        }
    }

    public ArrayList<Evento> getList() {
        return list;
    }

    public void updateList(List<Evento> list){
        this.list.clear();
        this.list.addAll(list);
        orderByFecha();
        notifyDataSetChanged();
    }

    public void delete(Evento evento){
        list.remove(evento);
        notifyDataSetChanged();
    }

    public void undo(Evento evento) {
        list.add(evento);
        orderByFecha();
        notifyDataSetChanged();
    }

    public void edit(Evento evento){
        for (Evento o : list){
            if (o.getId().equals(evento.getId())){
                list.remove(o);
                break;
            }
        }
        list.add(evento);
        orderByFecha();
        notifyDataSetChanged();
    }

    public void orderByFecha(){
        Collections.sort(list);
        notifyDataSetChanged();
    }
    public void orderByPrioridad(){
        Collections.sort(list,new EventComparator());
        notifyDataSetChanged();
    }
}
