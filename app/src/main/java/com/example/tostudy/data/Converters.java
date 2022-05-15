package com.example.tostudy.data;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
@ProvidedTypeConverter
public class Converters {
    @TypeConverter
    public static Date formLocaleDate(LocalDate date){
        return Date.valueOf(date.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate formDate(Date date){
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
    }
}
