package com.example.tostudy.ui.objetivos;

import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

import java.util.List;

public interface ObjetivoContract {
    interface View extends OnRepositoryListCallBack {
        <T> void showData(List<T> list);
        void showOrder();
        void showNoData();
    }
    interface Presenter extends OnRepositoryListCallBack {
        void load();
        void undo(Objetivo objetivo);
        void editProgres(Objetivo objetivo);
        void delete(Objetivo objetivo);
        void order();
    }
    interface Repository{
        void load();
        void delete(Objetivo objetivo);
        void undo(Objetivo objetivo);
    }
}
