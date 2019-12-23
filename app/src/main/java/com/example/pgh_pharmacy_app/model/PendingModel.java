package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PendingModel extends ShoppingCartModel implements Parcelable {

    String pendingID;

    public PendingModel() {
    }

    public PendingModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type, String pendingID) {
        super(productid, quantiyperProduct, productlist, checked, type);
        this.pendingID = pendingID;
    }

    protected PendingModel(Parcel in) {
        super(in);
        pendingID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(pendingID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PendingModel> CREATOR = new Creator<PendingModel>() {
        @Override
        public PendingModel createFromParcel(Parcel in) {
            return new PendingModel(in);
        }

        @Override
        public PendingModel[] newArray(int size) {
            return new PendingModel[size];
        }
    };

    public String getPendingID() {
        return pendingID;
    }

    public void setPendingID(String pendingID) {
        this.pendingID = pendingID;
    }
}
