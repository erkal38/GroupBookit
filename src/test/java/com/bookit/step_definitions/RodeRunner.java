package com.bookit.step_definitions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RodeRunner {
    public static void main(String[] args) throws ParseException {

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        System.out.println(dt.toString());

        System.out.println(dt.getDate());


        System.out.println("******************");

        String untildate="2011-10-08";//can take any date in current format
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar cal = Calendar.getInstance();
        cal.setTime( dateFormat.parse(untildate));
        cal.add( Calendar.DATE, 1 );
        String convertedDate=dateFormat.format(cal.getTime());
        System.out.println("Date increase by one.."+convertedDate);
    }
}
