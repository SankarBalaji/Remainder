package Listeners;

import android.view.View;
import android.widget.EditText;

import com.remainder.sankar.sample.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

/**
 * Created by root on 13/12/15.
 */
public class TimePickerListener implements TimePickerDialog.OnTimeSetListener {
    View viewToSet;

    TimePickerListener (View view){
        viewToSet = view;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
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
        if (viewToSet instanceof EditText)
            ((EditText)viewToSet).setText(time);
    }
}
