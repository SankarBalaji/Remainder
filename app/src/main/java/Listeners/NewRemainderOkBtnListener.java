package Listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.remainder.sankar.sample.DatabaseAPI;
import com.remainder.sankar.sample.R;
import com.remainder.sankar.sample.TodayScreen;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;

import Utils.Month;

/**
 * Created by root on 3/1/16.
 */
public class NewRemainderOkBtnListener extends AbstractListener {

    public NewRemainderOkBtnListener (AppCompatActivity activity){
        super (activity);
    }
    @Override
    public void onClick(View v) {

        EditText descText = (EditText)this.listenerActivity.findViewById(R.id.fName);
        String desc= descText.getText().toString();
        MaterialBetterSpinner spinner = (MaterialBetterSpinner)this.listenerActivity.findViewById(R.id.type);
        String type = spinner.getText().toString();
        EditText dateText = (EditText)this.listenerActivity.findViewById(R.id.start_date);
        String date= dateText.getText().toString();
        EditText timeText = (EditText)this.listenerActivity.findViewById(R.id.start_time);
        String time= timeText.getText().toString();
        EditText phoneNumberText = (EditText)this.listenerActivity.findViewById(R.id.fetch_contacts);
        String phoneNumber = phoneNumberText.getText().toString();

        System.out.println("**************************");
        System.out.println("Ok clicked");
        System.out.println("Desc:"+desc);
        System.out.println("Type:"+type);
        System.out.println("Date:"+date);
        System.out.println("Time:"+time);
        System.out.println("PhoneNumber:"+phoneNumber);
        System.out.println("**************************");
        DatabaseAPI db = DatabaseAPI.getDatabaseHandler(TodayScreen.dbContext);
        if (validateInputs (desc, date, time, phoneNumber)) {
            db.insertNewRemainder(this.listenerActivity, desc, type, date, time, 1);
            this.listenerActivity.finish();
        }
    }

    private boolean validateInputs (String description, String date, String time, String phoneNumbers){
        int toastDuration = Toast.LENGTH_SHORT;
        String message = "Success";
        String errorMessage = "Please select a valid ";
        boolean isInputValid = true;
        boolean errorFlag = false;
        if (null == description
                || description.length() == 0
                || description.equalsIgnoreCase("")) {
            System.out.println("Checking descrpription");
            errorMessage = errorMessage+"description";
            errorFlag = true;
        } else if (null == date || date.length() == 0 || date.equalsIgnoreCase("")) {
            errorMessage = errorMessage+"date";
            errorFlag = true;
        } else if (null == phoneNumbers || phoneNumbers.length() == 0 || phoneNumbers.equalsIgnoreCase("")) {
            errorMessage = errorMessage+"phone number";
            errorFlag = true;
        } else if (null == time || time.length() == 0 || time.equalsIgnoreCase("")) {
            errorMessage = errorMessage+"time";
            errorFlag = true;
        } else {
            String[] toCheckDate = date.split("-");
            int toCheckday = Integer.parseInt(toCheckDate[0]);
            int toCheckMonth = Month.getMonth(toCheckDate[1]).getCalendarMonth();
            int toCheckYear = Integer.parseInt(toCheckDate[2]);
            String[] timeToCheck = time.split(":");
            int hourToCheck = Integer.parseInt(timeToCheck[0]);
            int minuteToCheck = Integer.parseInt(timeToCheck[1].split("-")[0]);
            String AM_PM = timeToCheck[1].split("-")[1];
            if (AM_PM.equalsIgnoreCase("PM")) {
                hourToCheck = hourToCheck + 12;
            }
            Calendar toCheckCalendar = Calendar.getInstance();
            toCheckCalendar.set(toCheckYear, toCheckMonth, toCheckday, hourToCheck, minuteToCheck);
            Calendar today = Calendar.getInstance();
            long now = System.currentTimeMillis();
            today.setTimeInMillis(now);
            if (today.after(toCheckCalendar)) {
                errorFlag = true;
                errorMessage = "Please select a time that comes after current time";
            }
        }
        if (errorFlag) {
            message = errorMessage;
            isInputValid = false;
            toastDuration = Toast.LENGTH_LONG;
        }
        displayToast (message, toastDuration);
        return isInputValid;
    }

    private void displayToast (String message, int duration){
        Toast.makeText(this.listenerActivity, message, duration).show();
    }
}
