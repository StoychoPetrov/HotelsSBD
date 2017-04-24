package eu.mobile.hotelssbd.Models;

/**
 * Created by stoycho.petrov on 07/04/2017.
 */

public class Client {

    private int     mClientId;
    private String  mFirstName;
    private String  mLastName;
    private String  mEgn;

    public Client(){}

    public Client(int mClientId, String mFirstName, String mLastName, String mEgn) {
        this.mClientId = mClientId;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEgn = mEgn;
    }

    public int getmClientId() {
        return mClientId;
    }

    public void setmClientId(int mClientId) {
        this.mClientId = mClientId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmEgn() {
        return mEgn;
    }

    public void setmEgn(String mEgn) {
        this.mEgn = mEgn;
    }
}
