package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OneTimeUsersModel extends UserModel implements Parcelable {


    public OneTimeUsersModel() {
    }

    public OneTimeUsersModel(String lastname, String firstname, String username, String password, String id, String type, String address, String number) {
        super(lastname, firstname, username, password, id, type, address, number);
    }

    public OneTimeUsersModel(String username, String number){
        super(username, number);
    }

    protected OneTimeUsersModel(Parcel in) {
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

    public static final Creator<OneTimeUsersModel> CREATOR = new Creator<OneTimeUsersModel>() {
        @Override
        public OneTimeUsersModel createFromParcel(Parcel in) {
            return new OneTimeUsersModel(in);
        }

        @Override
        public OneTimeUsersModel[] newArray(int size) {
            return new OneTimeUsersModel[size];
        }
    };
}
