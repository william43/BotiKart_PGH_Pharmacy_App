package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InProcessModel extends ShoppingCartModel implements Parcelable {

    String inProcessID;

    public InProcessModel() {
    }


    public InProcessModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type, String inProcessID) {
        super(productid, quantiyperProduct, productlist, checked, type);
        this.inProcessID = inProcessID;
    }

    public InProcessModel(Parcel in, String inProcessID) {
        super(in);
        this.inProcessID = inProcessID;
    }

    protected InProcessModel(Parcel in) {
        super(in);
        inProcessID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(inProcessID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InProcessModel> CREATOR = new Creator<InProcessModel>() {
        @Override
        public InProcessModel createFromParcel(Parcel in) {
            return new InProcessModel(in);
        }

        @Override
        public InProcessModel[] newArray(int size) {
            return new InProcessModel[size];
        }
    };

    public String getInProcessID() {
        return inProcessID;
    }

    public void setInProcessID(String inProcessID) {
        this.inProcessID = inProcessID;
    }


}
