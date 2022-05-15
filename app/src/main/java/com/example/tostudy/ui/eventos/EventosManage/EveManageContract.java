package com.example.tostudy.ui.eventos.EventosManage;

import com.example.tostudy.data.model.Evento;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

public interface EveManageContract {

    interface View extends OnRepositoryListCallBack {
        void setNombreEmpty();
        void setHoraIniEmpty();
        void setHoraFinEmpty();
        void setFechaEmpty();
        void setHoraFinErr();
        void setFechaErr();
    }

    interface Presenter extends OnRepositoryListCallBack{
        void add(Evento evento);
        void edit(Evento evento);
        void onNombreEmpty();
        void onHoraIniEmpty();
        void onHoraFinEmpty();
        void onFechaEmpty();
        void onHoraFinErr();
        void onFechaErr();
    }

    interface Interactor extends OnRepositoryListCallBack{
        void add(Evento evento);
        void edit(Evento evento);
    }

    interface Repository {
        void add(Evento evento);
        void edit(Evento evento);
    }
    
}
