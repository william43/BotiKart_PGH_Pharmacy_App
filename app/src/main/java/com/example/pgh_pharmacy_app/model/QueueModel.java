package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class QueueModel extends ShoppingCartModel implements Parcelable {
    String queueID;

    public QueueModel() {
    }

    public QueueModel(String productid, int quantiyperProduct, MedicineModel productlist, boolean checked, String type, String queueID) {
        super(productid, quantiyperProduct, productlist, checked, type);
        this.queueID = queueID;
    }


    protected QueueModel(Parcel in) {
        super(in);
        queueID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(queueID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QueueModel> CREATOR = new Creator<QueueModel>() {
        @Override
        public QueueModel createFromParcel(Parcel in) {
            return new QueueModel(in);
        }

        @Override
        public QueueModel[] newArray(int size) {
            return new QueueModel[size];
        }
    };

    public String getQueueID() {
        return queueID;
    }

    public void setQueueID(String queueID) {
        this.queueID = queueID;
    }
}
