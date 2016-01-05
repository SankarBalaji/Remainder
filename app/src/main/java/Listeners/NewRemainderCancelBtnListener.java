package Listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by root on 3/1/16.
 */
public class NewRemainderCancelBtnListener extends AbstractListener {

    public NewRemainderCancelBtnListener (AppCompatActivity activity){
        super (activity);
    }
    @Override
    public void onClick(View v) {
        System.out.println("**************************");
        System.out.println("cancel clicked");
        System.out.println("**************************");
        this.listenerActivity.finish();
    }
}
