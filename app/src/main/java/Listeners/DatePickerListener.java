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
        String day = "";
        if (dayOfMonth < 10)
            day = "0"+dayOfMonth;
        String month = "";
        switch (monthOfYear){
            case 0:
                month = "January";
                break;
            case 1:
                month = "February";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "August";
                break;
            case 8:
                month = "September";
                break;
            case 9:
                month = "October";
                break;
            case 10:
                month = "November";
                break;
            case 11:
                month = "December";
                break;

        }

        String date = day +"-"+month+"-"+year;
        if (viewToSet instanceof EditText)
            ((EditText)viewToSet).setText(date);
    }

}