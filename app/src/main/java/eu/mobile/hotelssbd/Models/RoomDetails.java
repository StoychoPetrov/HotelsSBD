package eu.mobile.hotelssbd.Models;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class RoomDetails {

    private int         mRoomDetailId;
    private double      mRoomPrice;
    private String      mRoomDescription;
    private int         mHotelId;

    public RoomDetails(){}

    public RoomDetails(int mRoomDetailId, double mRoomPrice, String mRoomDescription,int mHotelId) {
        this.mRoomDetailId = mRoomDetailId;
        this.mRoomPrice = mRoomPrice;
        this.mRoomDescription = mRoomDescription;
        this.mHotelId          = mHotelId;
    }

    public int getmHotelId() {
        return mHotelId;
    }

    public void setmHotelId(int mHotelId) {
        this.mHotelId = mHotelId;
    }

    public int getmRoomDetailId() {
        return mRoomDetailId;
    }

    public void setmRoomDetailId(int mRoomDetailId) {
        this.mRoomDetailId = mRoomDetailId;
    }

    public double getmRoomPrice() {
        return mRoomPrice;
    }

    public void setmRoomPrice(double mRoomPrice) {
        this.mRoomPrice = mRoomPrice;
    }

    public String getmRoomDescription() {
        return mRoomDescription;
    }

    public void setmRoomDescription(String mRoomDescription) {
        this.mRoomDescription = mRoomDescription;
    }
}
