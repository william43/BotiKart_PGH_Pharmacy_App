package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PendingAdminProcessModel extends AdminProcessModel implements Parcelable {
    String type;

    public PendingAdminProcessModel() {
    }

    public PendingAdminProcessModel(String id, boolean isprocessed, String userid, String type) {
        super(id, isprocessed, userid);
        this.type = type;
    }


    protected PendingAdminProcessModel(Parcel in) {
        super(in);
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PendingAdminProcessModel> CREATOR = new Creator<PendingAdminProcessModel>() {
        @Override
        public PendingAdminProcessModel createFromParcel(Parcel in) {
            return new PendingAdminProcessModel(in);
        }

        @Override
        public PendingAdminProcessModel[] newArray(int size) {
            return new PendingAdminProcessModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
