package com.example.android.hackudaipur.model;


public class User {

    private String mName;
    private int mId;

    public User(String mName, int mId) {
        this.mName = mName;
        this.mId = mId;
    }

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

}
