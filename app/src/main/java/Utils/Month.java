package Utils;

/**
 * Created by root on 3/1/16.
 */
public enum Month {
    JANUARY ("January", 0), FEBUARY ("February", 1),MARCH ("March", 2), APRIL ("April", 3),
    MAY ("May", 4), JUNE ("June", 5),JULY ("July", 6), AUGUST ("August", 7),
    SEPTEMBER ("September", 8), OCTOBER ("October", 9),NOVEMBER ("November", 10), DECEMBER ("December", 11);
    private final String monthDescription;
    private final int calendarMonth;
    Month (String desc, int month){
        this.monthDescription = desc;
        this.calendarMonth = month;
    }

    public String getMonthDescription (){
        return this.monthDescription;
    }

    public int getCalendarMonth (){
        return this.calendarMonth;
    }

    public static Month getMonth (String desc){
        Month monthToReturn = null;
        for (Month month :values()) {
            if (month.monthDescription.equalsIgnoreCase(desc)) {
                monthToReturn = month;
                break;
            }
        }
        return monthToReturn;

    }

    public static Month getMonth (int calendarMonth){
        Month monthToReturn = null;
        for (Month month : values()) {
            if (month.calendarMonth == calendarMonth) {
                monthToReturn = month;
                break;
            }
        }
        return monthToReturn;
    }
}
