package Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 6/1/16.
 */
public class AppUtils {

    public static String getTimeString (int hourOfDay, int minute){
        System.out.println("**********************");
        System.out.println("Hour:"+hourOfDay);
        System.out.println("Minute:"+minute);
        System.out.println("**********************");
        String AM_PM = "PM";
        if (hourOfDay < 12)
            AM_PM = "AM";
        String min = ""+minute;
        if (minute < 10)
            min = "0"+minute;
        String hour = ""+hourOfDay;
        if (hourOfDay > 12){
            hourOfDay = hourOfDay - 12;
            hour = ""+hourOfDay;
        } else if (hourOfDay < 10){
            hour = "0"+hourOfDay;
        }
        String time = hour +":"+min+"-"+AM_PM;
        return time;
    }

    public static String getDateString (int year, int monthOfYear, int dayOfMonth){
        String day = ""+dayOfMonth;
        if (dayOfMonth < 10)
            day = "0"+dayOfMonth;
        String month = "";
        Month monthEnum = Month.getMonth(monthOfYear);
        if (null != monthEnum)
            month = monthEnum.getMonthDescription();

        String date = day +"-"+month+"-"+year;
        return date;
    }

    public static Calendar getTime (String dateString, String timeString){
       Calendar c= Calendar.getInstance();
        //Set Date
        String[] dateArray = dateString.split("-");
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]));
        c.set(Calendar.MONTH, Month.getMonth(dateArray[1]).getCalendarMonth());
        c.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));

        //Set time
        String[] timeArray = timeString.split("-");
        String[] times = timeArray[0].split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        if (timeArray[1].equalsIgnoreCase("PM")) {
            hour = hour + 12;
        }

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        return c;
    }

    public static int getActionCode (String action){
        int code = -1;
        switch (action.toUpperCase()){
            case "PHONE":
                code = 0;
                break;
            case "MESSAGE":
                code = 1;
                break;

        }
        return code;
    }

    public static String getAction (int code){
        String action = "";
        switch (code) {
            case 0:
                action = "PHONE";
                break;
            case 1:
                action = "MESSAGE";
                break;
        }
        return action;

    }

}
