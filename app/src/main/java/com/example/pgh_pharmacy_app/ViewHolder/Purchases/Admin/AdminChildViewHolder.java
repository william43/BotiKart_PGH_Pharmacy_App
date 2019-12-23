package com.example.pgh_pharmacy_app.ViewHolder.Purchases.Admin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class AdminChildViewHolder extends ChildViewHolder {

    private TextView genericName;
    private TextView subTotal;
    private TextView quantity;
    private ImageView img;

    public AdminChildViewHolder(View itemView) {
        super(itemView);
        genericName = (TextView) itemView.findViewById(R.id.processings_purchase_generic_name);
        subTotal = (TextView) itemView.findViewById(R.id.processings_purchase_subtotal);
        quantity = (TextView) itemView.findViewById(R.id.processings_purchase_quantity);
        img = (ImageView) itemView.findViewById(R.id.processings_purchase_img);

    }

    public void onBind(ShoppingCartModel order, ExpandableGroup group) {
        genericName.setText(order.getProductlist().getGenericName());
        subTotal.setText("Subtotal: " + Double.toString  (order.getProductlist().getPrice() * order.getQuantiyperProduct()));
        quantity.setText("Total Quantity: " + order.getQuantiyperProduct());
    }
}
