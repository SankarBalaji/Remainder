package Listeners;

import android.app.FragmentManager;
import android.view.View;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by root on 14/12/15.
 */
public class TimeClickListener implements View.OnClickListener  {
    View viewToSet;
    TimePickerListener listener;
    FragmentManager fragment;

    public TimeClickListener (FragmentManager fragment, View v){
        this.viewToSet = v;
        this.fragment = fragment;
        this.listener = new TimePickerListener (v);

    }
    @Override
    public void onClick(View v) {
        System.out.println("**************************************");
        System.out.println("I am time clicked");
        System.out.println("**************************************");
        Calendar now = Calendar.getInstance();
        Time t = new Time (System.currentTimeMillis());
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                listener,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                false
        );
        dpd.show(fragment, "Datepickerdialog");

    }
}
