package Utils;

/**
 * Created by root on 6/1/16.
 */
public class AppUtils {

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
}
