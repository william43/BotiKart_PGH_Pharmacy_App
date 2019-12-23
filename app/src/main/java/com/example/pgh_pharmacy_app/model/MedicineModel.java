package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MedicineModel implements Parcelable{

    private String genericName;
    private String dosage;
    private String lowerTohighest;
    private double price;
    private String type;


    public MedicineModel(){

    }

    public MedicineModel(String genericName, String dosage, String lowerTohighest, double price, String type) {
        this.genericName = genericName;
        this.dosage = dosage;
        this.lowerTohighest = lowerTohighest;
        this.price = price;
        this.type = type;
    }

    protected MedicineModel(Parcel in) {
        genericName = in.readString();
        dosage = in.readString();
        lowerTohighest = in.readString();
        price = in.readDouble();
        type = in.readString();
    }

    public static final Creator<MedicineModel> CREATOR = new Creator<MedicineModel>() {
        @Override
        public MedicineModel createFromParcel(Parcel in) {
            return new MedicineModel(in);
        }

        @Override
        public MedicineModel[] newArray(int size) {
            return new MedicineModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(genericName);
        dest.writeString(dosage);
        dest.writeString(lowerTohighest);
        dest.writeDouble(price);
        dest.writeString(type);
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getLowerTohighest() {
        return lowerTohighest;
    }

    public void setLowerTohighest(String lowerTohighest) {
        this.lowerTohighest = lowerTohighest;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
