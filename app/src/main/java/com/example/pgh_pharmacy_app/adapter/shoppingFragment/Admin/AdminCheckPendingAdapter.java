package com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.Admin.AdminCheckPrescribedReceiptActivity;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminCheckPendingAdapter extends RecyclerView.Adapter<AdminCheckPendingAdapter.ViewHolder> {

    ArrayList<ShoppingCartModel> shoppingCartModels = new ArrayList<>();
    Context context;

    public AdminCheckPendingAdapter(Context context, ArrayList<ShoppingCartModel> shoppingCartModels){
        this.shoppingCartModels = shoppingCartModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_check_pending_items, parent, false);

        // Return a new holder instance
        AdminCheckPendingAdapter.ViewHolder viewHolder  = new AdminCheckPendingAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(shoppingCartModels.get(position).getProductlist().getGenericName());
        holder.subtotal.setText("Subtotal: " + Double.toString  (shoppingCartModels.get(position).getProductlist().getPrice() * shoppingCartModels.get(position).getQuantiyperProduct()));
        holder.quantity.setText("Total Quantity: " + shoppingCartModels.get(position).getQuantiyperProduct());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shoppingCartModels.get(position).getProductlist() instanceof PrescribedMedicineModel){
                    Intent intent = new Intent(context, AdminCheckPrescribedReceiptActivity.class);
                    intent.putExtra("medicine", shoppingCartModels.get(position).getProductlist());
                    context.startActivity(intent);
                }
            }
        });
        if(shoppingCartModels.get(position).getProductlist() instanceof PrescribedMedicineModel){
            holder.star.setVisibility(View.VISIBLE);
        }
        if(shoppingCartModels.get(position).isChecked())
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    Toast.makeText(context, shoppingCartModels.get(position).getProductlist().getGenericName() + " was clicked", Toast.LENGTH_LONG).show();
                    shoppingCartModels.get(position).setChecked(true);
                }
                else{
                    shoppingCartModels.get(position).setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingCartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        CheckBox checkBox;
        TextView text;
        TextView quantity;
        TextView subtotal;
        ImageView star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            img = itemView.findViewById(R.id.check_pending_img);
            checkBox = itemView.findViewById(R.id.check_pending_check);
            text = itemView.findViewById(R.id.check_pending_generic_name);
            quantity = itemView.findViewById(R.id.check_pending_quantity);
            subtotal = itemView.findViewById(R.id.check_pending_subtotal);
            star = itemView.findViewById(R.id.check_pending_star_notice);
            star.setVisibility(View.INVISIBLE);
        }
    }
}
