package eu.mobile.hotelssbd.Models;

import java.util.List;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class Hotel {

    private int             mHotelId;
    private String          mHotelName;
    private String          mHotelDescription;
    private List<String>    mImages;

    public Hotel(){}

    public Hotel(int mHotelId, String mHotelName, String mHotelDescription) {
        this.mHotelId = mHotelId;
        this.mHotelName = mHotelName;
        this.mHotelDescription = mHotelDescription;
    }

    public int getmHotelId() {
        return mHotelId;
    }

    public void setmHotelId(int mHotelId) {
        this.mHotelId = mHotelId;
    }

    public String getmHotelName() {
        return mHotelName;
    }

    public void setmHotelName(String mHotelName) {
        this.mHotelName = mHotelName;
    }

    public String getmHotelDescription() {
        return mHotelDescription;
    }

    public void setmHotelDescription(String mHotelDescription) {
        this.mHotelDescription = mHotelDescription;
    }

    public List<String> getmImages() {
        return mImages;
    }

    public void setmImages(List<String> mImages) {
        this.mImages = mImages;
    }
}
