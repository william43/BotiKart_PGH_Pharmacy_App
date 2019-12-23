package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompletedModel extends ShoppingCartModel implements Parcelable {

    String completedID;

    public CompletedModel() {
    }

    public CompletedModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type, String completedID) {
        super(productid, quantiyperProduct, productlist, checked, type);
        this.completedID = completedID;
    }

    protected CompletedModel(Parcel in) {
        super(in);
        completedID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(completedID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompletedModel> CREATOR = new Creator<CompletedModel>() {
        @Override
        public CompletedModel createFromParcel(Parcel in) {
            return new CompletedModel(in);
        }

        @Override
        public CompletedModel[] newArray(int size) {
            return new CompletedModel[size];
        }
    };

    public String getCompletedID() {
        return completedID;
    }

    public void setCompletedID(String completedID) {
        this.completedID = completedID;
    }
}
