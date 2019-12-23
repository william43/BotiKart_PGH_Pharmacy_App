package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HomeNewsModel implements Parcelable {


    String name;
    ArrayList<String> description;

    public HomeNewsModel() {
    }

    public HomeNewsModel(String name, ArrayList<String> description) {
        this.name = name;
        this.description = description;
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

    protected HomeNewsModel(Parcel in) {
        name = in.readString();
        description = in.createStringArrayList();
    }

    public static final Creator<HomeNewsModel> CREATOR = new Creator<HomeNewsModel>() {
        @Override
        public HomeNewsModel createFromParcel(Parcel in) {
            return new HomeNewsModel(in);
        }

        @Override
        public HomeNewsModel[] newArray(int size) {
            return new HomeNewsModel[size];
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
    }
}
