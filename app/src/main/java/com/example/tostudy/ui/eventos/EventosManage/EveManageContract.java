package com.example.tostudy.ui.eventos.EventosManage;

import com.example.tostudy.data.model.Evento;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;

public interface EveManageContract {

    interface View extends OnRepositoryListCallBack {
        void setNameEmpty();
        void setHoraIniEmpty();
        void setHoraFinEmpty();
        void setDateEmpty();
        void setHoraFinErr();
        void setDateErr();
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
