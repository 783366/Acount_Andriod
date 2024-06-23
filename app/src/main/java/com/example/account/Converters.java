package com.example.account;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Converters {
    //把Long类型转换为Date类型
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    //把Date转化为Long
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
