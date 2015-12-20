package Listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 20/12/15.
 */
public class RemainderTypeListener implements AdapterView.OnItemClickListener {

    final boolean action;
    final Set<String> selectionStrings = new HashSet<>();
    final Set<View> actionableViews = new HashSet<>();
    final BaseAdapter adapter;

    public RemainderTypeListener (BaseAdapter adapter, Set<String> selectableStrings, boolean action, Set<View> actionableViews){
        this.action = action;
        this.adapter = adapter;
        selectionStrings.addAll(selectableStrings);
        this.actionableViews.addAll(actionableViews);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("********************");
        System.out.println("Spinner selected");
        System.out.println("Spinner position:"+position);
        System.out.println("Spinner selection:"+adapter.getItem(position));
        System.out.println("********************");
        boolean actionToPerform;
        if (selectionStrings.contains(adapter.getItem(position)))
            actionToPerform = this.action;
        else
            actionToPerform = !(this.action);
        for (String selection : selectionStrings) {
            System.out.println("Selection Strings Added:"+selection);
        }
        for (View actionableView : actionableViews) {
            System.out.println("********************************");
            System.out.println("Enabling View:" + actionableView.getId());
            System.out.println("Enabling action:"+actionToPerform);
            System.out.println("********************************");
            actionableView.setEnabled(actionToPerform);
        }

    }
}
