package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToPayAdminProcessModel extends AdminProcessModel implements Parcelable {

    String type;
    String counter;

    public ToPayAdminProcessModel() {
    }

    public ToPayAdminProcessModel(String id, boolean isprocessed, String userid, String type, String counter) {
        super(id, isprocessed, userid);
        this.type = type;
        this.counter = counter;
    }

    protected ToPayAdminProcessModel(Parcel in) {
        super(in);
        type = in.readString();
        counter = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
        dest.writeString(counter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToPayAdminProcessModel> CREATOR = new Creator<ToPayAdminProcessModel>() {
        @Override
        public ToPayAdminProcessModel createFromParcel(Parcel in) {
            return new ToPayAdminProcessModel(in);
        }

        @Override
        public ToPayAdminProcessModel[] newArray(int size) {
            return new ToPayAdminProcessModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
}
