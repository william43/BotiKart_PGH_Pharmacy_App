package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AdminModel extends UserModel implements Parcelable {

    public AdminModel() {
    }

    public AdminModel(String username, String number) {
        super(username, number);
    }

    public AdminModel(String lastname, String firstname, String username, String password, String id, String type, String address, String number) {
        super(lastname, firstname, username, password, id, type, address, number);
    }


    protected AdminModel(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminModel> CREATOR = new Creator<AdminModel>() {
        @Override
        public AdminModel createFromParcel(Parcel in) {
            return new AdminModel(in);
        }

        @Override
        public AdminModel[] newArray(int size) {
            return new AdminModel[size];
        }
    };
}
