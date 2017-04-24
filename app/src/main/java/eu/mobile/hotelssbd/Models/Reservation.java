package eu.mobile.hotelssbd.Models;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class Reservation {

    private int         mReservationId;
    private String      mDateFrom;
    private String      mDateTo;

    public Reservation(){}

    public Reservation(int mReservationId, String mDateFrom, String mDateTo) {
        this.mReservationId = mReservationId;
        this.mDateFrom = mDateFrom;
        this.mDateTo = mDateTo;
    }

    public int getmReservationId() {
        return mReservationId;
    }

    public void setmReservationId(int mReservationId) {
        this.mReservationId = mReservationId;
    }

    public String getmDateFrom() {
        return mDateFrom;
    }

    public void setmDateFrom(String mDateFrom) {
        this.mDateFrom = mDateFrom;
    }

    public String getmDateTo() {
        return mDateTo;
    }

    public void setmDateTo(String mDateTo) {
        this.mDateTo = mDateTo;
    }
}
