package com.example.pgh_pharmacy_app.ViewHolder.Purchases;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pgh_pharmacy_app.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class OrderReceiptViewHolder extends GroupViewHolder {

    private TextView osName;

    public OrderReceiptViewHolder(View itemView) {
        super(itemView);
        osName = (TextView) itemView.findViewById(R.id.order_receipt_id);
    }

    @Override
    public void expand() {
        osName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse() {
        Log.i("Adapter", "collapse");
        osName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);
    }

    public void setGroupName(ExpandableGroup group) {
        osName.setText(group.getTitle());
    }

}
