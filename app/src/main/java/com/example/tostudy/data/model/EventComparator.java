package com.example.tostudy.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;

public class EventComparator implements Comparator {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(Object o1, Object o2) {
        int result =  0;//((Evento)o1).compareTo(o2);

        if(((Evento)o1).getPriority() < ((Evento)o2).getPriority()){
            result = 1;
        }else if(((Evento)o1).getPriority() > ((Evento)o2).getPriority()){
            result = -1;
        }
        return result;
    }
}
