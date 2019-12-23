package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CartModel extends ShoppingCartModel implements Parcelable {

    String cardID;

    public CartModel() {
    }

    public CartModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type, String cardID) {
        super(productid, quantiyperProduct, productlist, checked, type);
        this.cardID = cardID;
    }

    protected CartModel(Parcel in) {
        super(in);
        cardID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(cardID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
