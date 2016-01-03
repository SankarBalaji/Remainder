package Listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by root on 3/1/16.
 */
public class NewRemainderCancelBtnListener implements View.OnClickListener {
    AppCompatActivity newRemainderActivity;

    public NewRemainderCancelBtnListener (AppCompatActivity activity){
        this.newRemainderActivity = activity;
    }
    @Override
    public void onClick(View v) {
        System.out.println("**************************");
        System.out.println("cancel clicked");
        System.out.println("**************************");
        newRemainderActivity.finish();
    }
}
