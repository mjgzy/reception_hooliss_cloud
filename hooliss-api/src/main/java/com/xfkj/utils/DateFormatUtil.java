package com.xfkj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    public static String dateToString(Date str_date){
        String format = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        try {
            format = simpleDateFormat.format(str_date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }
    public static String dateToString(Date str_date,String pattrn){
        String format = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattrn);
        try {
            format = simpleDateFormat.format(str_date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }
    public  static Date stringToDate(String date_str){
        Date format = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        try {
            format = simpleDateFormat.parse(date_str );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }
    public  static Date dateToDate(Date date_str,String pattrn){
        Date format = null;
        String str_date = DateFormatUtil.dateToString(date_str);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattrn);
        try {
            format = simpleDateFormat.parse(str_date );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }
}
