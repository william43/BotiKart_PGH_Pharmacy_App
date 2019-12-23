package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToPayModel extends ShoppingCartModel implements Parcelable {

    String toPayID;
    String counter;
    public ToPayModel(){

    }

    public ToPayModel(String toPayID) {
        this.toPayID = toPayID;
    }

    public ToPayModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type, String toPayID, String counter) {
        super(productid, quantiyperProduct, productlist, checked, type);
        this.toPayID = toPayID;
        this.counter = counter;
    }


    protected ToPayModel(Parcel in) {
        super(in);
        toPayID = in.readString();
        counter = in.readString();
    }

    public String getToPayID() {
        return toPayID;
    }

    public void setToPayID(String toPayID) {
        this.toPayID = toPayID;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(toPayID);
        dest.writeString(counter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToPayModel> CREATOR = new Creator<ToPayModel>() {
        @Override
        public ToPayModel createFromParcel(Parcel in) {
            return new ToPayModel(in);
        }

        @Override
        public ToPayModel[] newArray(int size) {
            return new ToPayModel[size];
        }
    };

}
