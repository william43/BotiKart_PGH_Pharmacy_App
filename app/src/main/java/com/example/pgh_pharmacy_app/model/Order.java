package com.example.pgh_pharmacy_app.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

@SuppressLint("ParcelCreator")
public class Order extends ExpandableGroup<ShoppingCartModel> {


    public Order(String title, List<ShoppingCartModel> items) {
        super(title, items);
    }

    public Order(Parcel in) {
        super(in);
    }
}
