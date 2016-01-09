package Listeners;

import android.view.View;
import android.widget.EditText;

import com.remainder.sankar.sample.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import Utils.AppUtils;

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

        String time = AppUtils.getTimeString(hourOfDay, minute);
        if (viewToSet instanceof EditText)
            ((EditText)viewToSet).setText(time);
    }
}
