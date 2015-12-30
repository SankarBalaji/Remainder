package Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Utils.PhoneNumber;
import com.remainder.sankar.sample.R;

/**
 * Created by root on 30/12/15.
 */
public class IndividualPhoneNumberAdapter extends ArrayAdapter<PhoneNumber> {
    private final Context context;
    private final List<PhoneNumber> numbers = new ArrayList<>();

    public IndividualPhoneNumberAdapter (Context context, List<PhoneNumber> contactsList){
        super(context, R.layout.individual_phonelist_layout, contactsList);
        this.context = context;
        this.numbers.addAll(contactsList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contactView = inflater.inflate(R.layout.individual_phonelist_layout, parent, false);
        TextView contactNumber = (TextView) contactView.findViewById(R.id.phonenumber);
        TextView contactType = (TextView) contactView.findViewById(R.id.phonenumbertype);
        PhoneNumber number = numbers.get(position);
        contactNumber.setText(number.getNumber());
        String type = "";
        switch (number.getType()) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME :
                type = "HOME";
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                type = "Mobile";
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                type = "WORK";
                break;
            default:
                type = "OTHER";

        };
        contactType.setText(type);

        return contactView;
    }

    @Override
    public PhoneNumber getItem (int position){
        return numbers.get(position);
    }
}
