package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AdminProcessModel implements Parcelable {

    private String id;
    private boolean isprocessed;
    private String userid;

    public AdminProcessModel() {
    }

    public AdminProcessModel(String id, boolean isprocessed, String userid) {
        this.id = id;
        this.isprocessed = isprocessed;
        this.userid = userid;
    }

    protected AdminProcessModel(Parcel in) {
        id = in.readString();
        isprocessed = in.readByte() != 0;
        userid = in.readString();
    }

    public static final Creator<AdminProcessModel> CREATOR = new Creator<AdminProcessModel>() {
        @Override
        public AdminProcessModel createFromParcel(Parcel in) {
            return new AdminProcessModel(in);
        }

        @Override
        public AdminProcessModel[] newArray(int size) {
            return new AdminProcessModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsprocessed() {
        return isprocessed;
    }

    public void setIsprocessed(boolean isprocessed) {
        this.isprocessed = isprocessed;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isprocessed ? 1 : 0));
        dest.writeString(userid);
    }
}
