package com.example.tostudy.data.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class OjetivoComparator implements Comparator {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(Object o1, Object o2) {
        int result =  0;//((Objetivo)o1).compareTo(o2);

        if(((Objetivo)o1).getPriority() < ((Objetivo)o2).getPriority()){
            result = 1;
        }else if(((Objetivo)o1).getPriority() > ((Objetivo)o2).getPriority()){
            result = -1;
        }
        return result;
    }
}
