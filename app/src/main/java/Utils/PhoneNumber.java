package Utils;

/**
 * Created by root on 30/12/15.
 */
public class PhoneNumber {

    final int type;
    final String number;

    public PhoneNumber (int type, String number){
        this.type = type;
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }
}
