package com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.Admin.AdminCheckPrescribedReceiptActivity;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;

import java.util.ArrayList;

public class AdminCheckAdapter extends RecyclerView.Adapter<AdminCheckAdapter.ViewHolder> {

    Context context;
    ArrayList<ShoppingCartModel> s;

    public AdminCheckAdapter(Context context, ArrayList<ShoppingCartModel> s) {
        this.context = context;
        this.s = s;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.child_view_holder_processings, parent, false);

        // Return a new holder instance
        AdminCheckAdapter.ViewHolder viewHolder  = new AdminCheckAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.genericName.setText(s.get(position).getProductlist().getGenericName());
        holder.subTotal.setText("Subtotal: " + Double.toString  (s.get(position).getProductlist().getPrice() * s.get(position).getQuantiyperProduct()));
        holder.quantity.setText("Total Quantity: " + s.get(position).getQuantiyperProduct());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s.get(position).getProductlist() instanceof PrescribedMedicineModel){
                    Intent intent = new Intent(context, AdminCheckPrescribedReceiptActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        if(s.get(position).getProductlist() instanceof PrescribedMedicineModel){
            holder.star.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView genericName;
        private TextView subTotal;
        private TextView quantity;
        private ImageView img;
        private ImageView star;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genericName = (TextView) itemView.findViewById(R.id.processings_purchase_generic_name);
            subTotal = (TextView) itemView.findViewById(R.id.processings_purchase_subtotal);
            quantity = (TextView) itemView.findViewById(R.id.processings_purchase_quantity);
            img = (ImageView) itemView.findViewById(R.id.processings_purchase_img);
            star = (ImageView) itemView.findViewById(R.id.prescribed_star_notice);
            star.setVisibility(View.INVISIBLE);

        }
    }
}
