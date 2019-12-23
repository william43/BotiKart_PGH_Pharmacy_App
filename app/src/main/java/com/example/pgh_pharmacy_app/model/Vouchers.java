package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Vouchers implements Parcelable {

    String name;
    ArrayList<String> description;
    int discount;

    public Vouchers() {
    }

    public Vouchers(String name, ArrayList<String> description, int discount) {
        this.name = name;
        this.description = description;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    protected Vouchers(Parcel in) {
        name = in.readString();
        description = in.createStringArrayList();
        discount = in.readInt();
    }

    public static final Creator<Vouchers> CREATOR = new Creator<Vouchers>() {
        @Override
        public Vouchers createFromParcel(Parcel in) {
            return new Vouchers(in);
        }

        @Override
        public Vouchers[] newArray(int size) {
            return new Vouchers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeStringList(description);
        dest.writeInt(discount);
    }
}
