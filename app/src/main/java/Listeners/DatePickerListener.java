package Listeners;

import android.view.View;
import android.widget.EditText;

import com.remainder.sankar.sample.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * Created by root on 13/12/15.
 */
class DatePickerListener implements DatePickerDialog.OnDateSetListener {
    View viewToSet;

    DatePickerListener (View view){
        viewToSet = view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth +"/"+monthOfYear+"/"+year;
        if (viewToSet instanceof EditText)
            ((EditText)viewToSet).setText(date);
    }

}