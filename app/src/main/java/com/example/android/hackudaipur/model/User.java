package com.example.android.hackudaipur.model;


public class User {

    private String mName;
    private boolean mGender;
    private long mPhone;
    private int mId;

    public int getmId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public boolean isMale() {
        return mGender;
    }

    public void setGender(boolean mGender) {
        this.mGender = mGender;
    }

    public long getPhone() {
        return mPhone;
    }

    public void setPhone(long mPhone) {
        this.mPhone = mPhone;
    }
}
