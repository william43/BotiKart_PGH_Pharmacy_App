package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PrescribedMedicineModel extends MedicineModel implements Parcelable {

    private String mImageUrl;

    public PrescribedMedicineModel() {
    }

    public PrescribedMedicineModel(String genericName, String dosage, String lowerTohighest, double price, String type, String mImageUrl) {
        super(genericName, dosage, lowerTohighest, price, type);
        this.mImageUrl = mImageUrl;
    }



    protected PrescribedMedicineModel(Parcel in) {
        super(in);
        mImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PrescribedMedicineModel> CREATOR = new Creator<PrescribedMedicineModel>() {
        @Override
        public PrescribedMedicineModel createFromParcel(Parcel in) {
            return new PrescribedMedicineModel(in);
        }

        @Override
        public PrescribedMedicineModel[] newArray(int size) {
            return new PrescribedMedicineModel[size];
        }
    };

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
