package com.example.pgh_pharmacy_app.adapter.shoppingFragment.Purchases;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.ViewHolder.Purchases.OrderChildViewHolder;
import com.example.pgh_pharmacy_app.ViewHolder.Purchases.OrderReceiptViewHolder;
import com.example.pgh_pharmacy_app.model.Order;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ProcessingAdapter extends ExpandableRecyclerViewAdapter<OrderReceiptViewHolder, OrderChildViewHolder> {

    private Activity activity;

    public ProcessingAdapter(Activity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public OrderReceiptViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder_order, parent, false);

        return new OrderReceiptViewHolder(view);
    }

    @Override
    public OrderChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder_processings, parent, false);

        return new OrderChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(OrderChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ShoppingCartModel phone = ((Order) group).getItems().get(childIndex);
        holder.onBind(phone,group);
    }

    @Override
    public void onBindGroupViewHolder(OrderReceiptViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
}
