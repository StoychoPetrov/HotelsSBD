package eu.mobile.hotelssbd.Models;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class Room {

    private int     mRoomId;
    private int     mRoomNumber;
    private String  mRoomDescription;

    public Room(int mRoomId, int mRoomNumber, String mRoomDescription) {
        this.mRoomId = mRoomId;
        this.mRoomNumber = mRoomNumber;
        this.mRoomDescription = mRoomDescription;
    }

    public int getmRoomId() {
        return mRoomId;
    }

    public void setmRoomId(int mRoomId) {
        this.mRoomId = mRoomId;
    }

    public int getmRoomNumber() {
        return mRoomNumber;
    }

    public void setmRoomNumber(int mRoomNumber) {
        this.mRoomNumber = mRoomNumber;
    }

    public String getmRoomDescription() {
        return mRoomDescription;
    }

    public void setmRoomDescription(String mRoomDescription) {
        this.mRoomDescription = mRoomDescription;
    }
}
