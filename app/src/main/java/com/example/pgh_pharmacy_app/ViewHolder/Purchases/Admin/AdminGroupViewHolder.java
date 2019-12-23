package com.example.pgh_pharmacy_app.ViewHolder.Purchases.Admin;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class AdminGroupViewHolder extends GroupViewHolder {

    TextView osName;
    TextView process;

    public AdminGroupViewHolder(View itemView) {
        super(itemView);
       osName = (TextView) itemView.findViewById(R.id.admin_receipt_id);
       process = (TextView) itemView.findViewById(R.id.admin_isprocessed);
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

    public void setGroupName(ExpandableGroup group, AdminProcessModel admin) {
        osName.setText(group.getTitle());
        if(admin.isIsprocessed()){
            process.setText("Being Processed");
            process.setTextColor(itemView.getResources().getColor(R.color.inprocess));
        }
        else{
            process.setText("Not being Processed");
            process.setTextColor(itemView.getResources().getColor(R.color.notinprocess));
        }

    }
}
