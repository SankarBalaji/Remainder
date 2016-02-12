package Utils;

import android.content.Context;
import android.os.Parcelable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 9/1/16.
 */
public class AlarmNotification {

    private String sDesc;
    private String lDesc;
    private String date;
    private String time;
    private String type;
    private String customAction;
    private String phoneNumber;
    private Context context;
    private int notificationId;
    private int actionCode;

    private AlarmNotification (){

    }

    public String getsDesc() {
        return sDesc;
    }

    public String getlDesc() {
        return lDesc;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getPhoneNumber (){
        return phoneNumber;
    }

    public String getCustomAction() {
        return customAction;
    }

    public Context getContext (){
        return context;
    }

    public int getActionCode () {return  actionCode ;}

    public int getNotificationId(){
        return notificationId;
    }

    private void setsDesc(String sDesc) {
        this.sDesc = sDesc;
    }

    private void setlDesc(String lDesc) {
        this.lDesc = lDesc;
    }

    private void setDate(String date) {
        this.date = date;
    }

    private void setTime(String time) {
        this.time = time;
    }

    private void setType(String type) {
        this.type = type;
    }

    private void setPhoneNumber (String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    private void setCustomAction(String customAction) {
        this.customAction = customAction;
    }

    private void setContext (Context context){
        this.context = context;
    }

    private void setNotificationId (int id){
        this.notificationId = id;
    }

    private void setActionCode (int code) {this.actionCode = code;}



    public static class AlarmBuilder {
        String sDesc;
        String lDesc;
        String date;
        String time;
        String type;
        String customAction;
        String phoneNumber;
        Context context;
        int notificationId;
        int actionCode;
        public AlarmBuilder (Context context){
            this.context = context;

        }
        public AlarmBuilder setShortDesc (String desc){
            this.sDesc = desc;
            return this;
        }
        public AlarmBuilder setLongDesc (String desc){
            this.lDesc = desc;
            return this;
        }
        public AlarmBuilder setDate (String date){
            this.date = date;
            return this;
        }
        public AlarmBuilder setTime (String time){
            this.time = time;
            return this;
        }
        public AlarmBuilder setType (String type){
            this.type = type;
            return this;
        }
        public AlarmBuilder setCustomAction (String action){
            this.customAction = action;
            return this;
        }
        public AlarmBuilder setPhoneNumber (String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }
        public AlarmBuilder setNotificationId (int id){
            this.notificationId = id;
            return this;
        }

        public AlarmBuilder setActionCode (int actionCode){
            this.actionCode = actionCode;
            return this;
        }

        public AlarmNotification build (){
            AlarmNotification alarm = new AlarmNotification ();
            alarm.setCustomAction(this.customAction);
            alarm.setDate(this.date);
            alarm.setTime(this.time);
            alarm.setType(this.type);
            alarm.setsDesc(this.sDesc);
            alarm.setlDesc(this.lDesc);
            alarm.setPhoneNumber(this.phoneNumber);
            alarm.setContext(this.context);
            alarm.setNotificationId(this.notificationId);
            alarm.setActionCode(this.actionCode);
            return alarm;
        }

    }
}
