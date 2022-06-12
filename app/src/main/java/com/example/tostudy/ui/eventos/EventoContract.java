package com.example.tostudy.ui.eventos;

import com.example.tostudy.data.model.Evento;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

import java.util.List;

public interface EventoContract {
    interface View extends OnRepositoryListCallBack {
        <T> void showData(List<T> list);
        void showOrder();
        void showNoData();
    }
    interface Presenter extends OnRepositoryListCallBack {
        void load(String userId);
        void load(String fecha,String userId);
        void undo(Evento evento);
        void delete(Evento evento);
        void order();
    }
    interface Repository{
        void load(String userId);
        void load(String userId,String fecha);
        void delete(Evento evento);
        void undo(Evento evento);
    }
}
