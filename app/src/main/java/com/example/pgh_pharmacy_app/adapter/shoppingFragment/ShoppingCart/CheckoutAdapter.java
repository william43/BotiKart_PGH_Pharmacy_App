package com.example.pgh_pharmacy_app.adapter.shoppingFragment.ShoppingCart;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    ArrayList<ShoppingCartModel> shoppingCartList;
    Context context;

    public CheckoutAdapter(ArrayList<ShoppingCartModel> shoppingCartList, Context context) {
        this.shoppingCartList = shoppingCartList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_shopping_checkout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder  = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medicineDetails.setText(shoppingCartList.get(position).getProductlist().getGenericName());
        holder.medicineQuantity.setText("Total Order: " + shoppingCartList.get(position).getQuantiyperProduct() + " items");
        holder.medicineTotal.setText("Php " + shoppingCartList.get(position).getQuantiyperProduct() * shoppingCartList.get(position).getQuantiyperProduct());
        if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Dextrose")){
            holder.medicineImage.setImageResource(R.drawable.dextrose_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Acarbose")){
            holder.medicineImage.setImageResource(R.drawable.acarbose_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Acetazolamide")){
            holder.medicineImage.setImageResource(R.drawable.acetazolamide_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Ascorbic")){
            holder.medicineImage.setImageResource(R.drawable.ascorbic_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Betahistine")){
            holder.medicineImage.setImageResource(R.drawable.betahistine_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Betamethasone")){
            Toast.makeText(context, "Went to Betame", Toast.LENGTH_LONG).show();
            holder.medicineImage.setImageResource(R.drawable.betamethasone_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Calcium")){
            holder.medicineImage.setImageResource(R.drawable.calcium_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Cilostazol")){
            holder.medicineImage.setImageResource(R.drawable.cilostazol_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Dextromethorpahn")){
            holder.medicineImage.setImageResource(R.drawable.dextromethorphan_icon);
        }
        else if(shoppingCartList.get(position).getProductlist().getGenericName().startsWith("Paracetamol")){
            Toast.makeText(context, "Went to Para", Toast.LENGTH_LONG).show();
            holder.medicineImage.setImageResource(R.drawable.paracetamol_icon);
        }


    }

    @Override
    public int getItemCount() {
        return shoppingCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView medicineImage;
        TextView medicineDetails;
        EditText medicineNotes;
        TextView medicineQuantity;
        TextView medicineTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineDetails = itemView.findViewById(R.id.shopping_checkout_details);
            medicineImage = itemView.findViewById(R.id.shopping_checkout_image);
            medicineNotes = itemView.findViewById(R.id.shopping_checkout_notes);
            medicineQuantity = itemView.findViewById(R.id.shopping_checkout_total_items);
            medicineTotal = itemView.findViewById(R.id.shopping_checkout_total_price);

        }
    }
}
