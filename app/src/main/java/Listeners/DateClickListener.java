package Listeners;

import android.app.FragmentManager;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by root on 14/12/15.
 */
public class DateClickListener implements View.OnClickListener {
    View viewToSet;
    DatePickerListener listener;
    FragmentManager fragment;
    public DateClickListener (FragmentManager fragment, View v){
        this.viewToSet = v;
        this.listener = new DatePickerListener(v);
        this.fragment = fragment;

    }
    @Override
    public void onClick(View v) {
        System.out.println("**************************************");
        System.out.println("I am date clicked");
        System.out.println("**************************************");
        Calendar today = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                listener,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(today);
        dpd.show(fragment, "Datepickerdialog");

    }
}
