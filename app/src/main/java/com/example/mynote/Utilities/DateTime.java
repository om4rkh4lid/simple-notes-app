package com.example.mynote.Utilities;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTime {


    private DateTime(){};

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String DISPLAY_FORMAT_SAME_YEAR = "MMM d 'at' HH:mm";
    private static final String DISPLAY_FORMAT_DIFFERENT_YEAR = "MM/d/yyyy";

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private static final SimpleDateFormat displayDateTimeFormatSameYear = new SimpleDateFormat(DISPLAY_FORMAT_SAME_YEAR);
    private static final SimpleDateFormat displayDateTimeFormatDifferentYear = new SimpleDateFormat(DISPLAY_FORMAT_DIFFERENT_YEAR);

    public static String getDateTimeSortable(){
        return dateTimeFormat.format(Calendar.getInstance().getTime());
    }

    public static String getDateTimeForDisplay(String timeStampString){
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = dateTimeFormat.parse(timeStampString);
            calendar.setTime(date);
        }catch (ParseException pe){
            Log.d("DateTime", pe.getMessage());
        }
        return getDateTimeString(calendar);
    }

    private static String getDateTimeString(Calendar then){
        Calendar now = Calendar.getInstance();
        if(now.get(Calendar.YEAR) == then.get(Calendar.YEAR)){
            return displayDateTimeFormatSameYear.format(then.getTime());
        }else{
            return displayDateTimeFormatDifferentYear.format(then.getTime());
        }
    }





}
