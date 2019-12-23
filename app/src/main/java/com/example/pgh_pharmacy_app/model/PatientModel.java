package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PatientModel extends UserModel implements Parcelable {

    public PatientModel() {
    }

    public PatientModel(String username, String number) {
        super(username, number);
    }

    public PatientModel(String lastname, String firstname, String username, String password, String id, String type, String address, String number) {
        super(lastname, firstname, username, password, id, type, address, number);
    }

    public static Creator<PatientModel> getCREATOR() {
        return CREATOR;
    }

    protected PatientModel(Parcel in) {
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

    public static final Creator<PatientModel> CREATOR = new Creator<PatientModel>() {
        @Override
        public PatientModel createFromParcel(Parcel in) {
            return new PatientModel(in);
        }

        @Override
        public PatientModel[] newArray(int size) {
            return new PatientModel[size];
        }
    };
}
