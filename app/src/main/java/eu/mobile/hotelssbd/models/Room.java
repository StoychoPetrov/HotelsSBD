package eu.mobile.hotelssbd.models;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class Room {

    private int     mRoomId;
    private int     mRoomNumber;
    private String  mRoomDescription;
    private int     mRoomDetailId;

    public Room(){}

    public Room(int mRoomNumber, String mRoomDescription, int mRoomDetailId) {
        this.mRoomNumber        = mRoomNumber;
        this.mRoomDescription   = mRoomDescription;
        this.mRoomDetailId      = mRoomDetailId;
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

    public int getmRoomDetailId() {
        return mRoomDetailId;
    }

    public void setmRoomDetailId(int mRoomDetailId) {
        this.mRoomDetailId = mRoomDetailId;
    }
}
