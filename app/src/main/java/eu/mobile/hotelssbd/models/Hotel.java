package eu.mobile.hotelssbd.models;

import java.util.List;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class Hotel {

    private int             mHotelId;
    private String          mHotelName;
    private String          mHotelDescription;
    private int             mStarsCount;
    private List<String>    mImages;

    public Hotel(){}

    public Hotel(int mHotelId, String mHotelName, String mHotelDescription, int starsCount) {
        this.mHotelId = mHotelId;
        this.mHotelName = mHotelName;
        this.mHotelDescription = mHotelDescription;
        this.mStarsCount        = starsCount;
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

    public int getmStarsCount() {
        return mStarsCount;
    }

    public void setmStarsCount(int mStarsCount) {
        this.mStarsCount = mStarsCount;
    }
}
