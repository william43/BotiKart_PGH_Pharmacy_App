package com.example.pgh_pharmacy_app.model;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ShoppingCartModel implements Parcelable {

    String productid;
    int quantiyperProduct;
    MedicineModel productlist;
    boolean checked;
    String type;


    public ShoppingCartModel(){

    }

    public ShoppingCartModel(String productid, int quantiyperProduct, PrescribedMedicineModel productlist, boolean checked, String type) {
        this.productid = productid;
        this.quantiyperProduct = quantiyperProduct;
        this.productlist = productlist;
        this.checked = checked;
        this.type = type;
    }

    public ShoppingCartModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type) {
        this.productid = productid;
        this.quantiyperProduct = quantiyperProduct;
        this.productlist = productlist;
        this.checked = checked;
        this.type = type;
    }

    public ShoppingCartModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked) {
        this.productid = productid;
        this.quantiyperProduct = quantiyperProduct;
        this.productlist = productlist;
        this.checked = checked;
    }

    public ShoppingCartModel(String productid, int quantiyperProduct, MedicineModel productlist) {
        this.productid = productid;
        this.quantiyperProduct = quantiyperProduct;
        this.productlist = productlist;
    }

    public ShoppingCartModel(int quantiyperProduct, MedicineModel productlist) {
        this.quantiyperProduct = quantiyperProduct;
        this.productlist = productlist;
    }


    protected ShoppingCartModel(Parcel in) {
        productid = in.readString();
        quantiyperProduct = in.readInt();
        productlist = in.readParcelable(MedicineModel.class.getClassLoader());
        checked = in.readByte() != 0;
        type = in.readString();
    }

    public static final Creator<ShoppingCartModel> CREATOR = new Creator<ShoppingCartModel>() {
        @Override
        public ShoppingCartModel createFromParcel(Parcel in) {
            return new ShoppingCartModel(in);
        }

        @Override
        public ShoppingCartModel[] newArray(int size) {
            return new ShoppingCartModel[size];
        }
    };

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Creator<ShoppingCartModel> getCREATOR() {
        return CREATOR;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getQuantiyperProduct() {
        return quantiyperProduct;
    }

    public void setQuantiyperProduct(int quantiyperProduct) {
        this.quantiyperProduct = quantiyperProduct;
    }

    public MedicineModel getProductlist() {
        return productlist;
    }

    public void setProductlist(MedicineModel productlist) {
        this.productlist = productlist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productid);
        dest.writeInt(quantiyperProduct);
        dest.writeParcelable(productlist, flags);
        dest.writeByte((byte) (checked ? 1 : 0));
        dest.writeString(type);
    }
}
