package com.example.tostudy.ui.objetivos.ObjetivosManage;

import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

public interface ObjManageContract {

    interface View extends OnRepositoryListCallBack {
        void setNameEmpty();
        void setDateEmpty();
        void setDateErr();
    }

    interface Presenter extends OnRepositoryListCallBack{
        void add(Objetivo objetivo);
        void edit(Objetivo objetivo);
        void onNombreEmpty();
        void onFechaEmpty();
        void onFechaErr();
    }

    interface Interactor extends OnRepositoryListCallBack{
        void add(Objetivo objetivo);
        void edit(Objetivo objetivo);
    }

    interface Repository {
        void add(Objetivo objetivo);
        void edit(Objetivo objetivo);
    }
    
}
