package Listeners;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by root on 5/1/16.
 */
public abstract class AbstractListener implements View.OnClickListener {
    AppCompatActivity listenerActivity;

    AbstractListener (AppCompatActivity activity){
        this.listenerActivity = activity;
    }

}
