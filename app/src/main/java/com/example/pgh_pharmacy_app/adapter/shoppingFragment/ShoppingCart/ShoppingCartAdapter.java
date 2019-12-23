package com.example.pgh_pharmacy_app.adapter.shoppingFragment.ShoppingCart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.pgh_pharmacy_app.HorizontalNumberPicker;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.OverTheCounterAdapter;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    ArrayList<ShoppingCartModel> shoppingCartList = new ArrayList<>();
    private Context context;
    public DatabaseReference shoppingCartDataRef;
    private UserModel user;

    public ShoppingCartAdapter(ArrayList<ShoppingCartModel> shoppingCartList, Context context, UserModel user) {
        this.shoppingCartList = shoppingCartList;
        this.context = context;
        this.user = user;
        shoppingCartDataRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(user.getId());
    }

    public ShoppingCartAdapter(ArrayList<ShoppingCartModel> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_shopping_check_cart, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder  = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.quantity.setValue(shoppingCartList.get(position).getQuantiyperProduct());
        holder.medicineInformation.setText(shoppingCartList.get(position).getProductlist().getGenericName());
        holder.quantity.getEt_number().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //holder.quantity.changeNumber(shoppingCartList.get(position), holder.quantity.getValue());
                Toast.makeText(context, "hehe " + holder.quantity.getValue(), Toast.LENGTH_SHORT).show();
                if(user instanceof PatientModel) {

                    if(shoppingCartList.get(position).getProductlist() instanceof PrescribedMedicineModel){
                        ShoppingCartModel sH = new ShoppingCartModel(shoppingCartList.get(position).getProductid(), holder.quantity.getValue(), (PrescribedMedicineModel) shoppingCartList.get(position).getProductlist(), false, shoppingCartList.get(position).getType());
                        shoppingCartDataRef.child(shoppingCartList.get(position).getProductid()).setValue(sH);

                    }
                    else {
                        ShoppingCartModel sH = new ShoppingCartModel(shoppingCartList.get(position).getProductid(), holder.quantity.getValue(), shoppingCartList.get(position).getProductlist(), false, shoppingCartList.get(position).getType());
                        shoppingCartDataRef.child(shoppingCartList.get(position).getProductid()).setValue(sH);
                    }

                }
                shoppingCartList.get(position).setQuantiyperProduct(holder.quantity.getValue());
            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.isChecked()){
                    shoppingCartList.get(position).setChecked(true);
                    if(user instanceof PatientModel) {
                        Log.d("CHECKED", "okaych");
                        if (shoppingCartList.get(position).getProductlist() instanceof PrescribedMedicineModel) {
                            ShoppingCartModel sH = new ShoppingCartModel(shoppingCartList.get(position).getProductid(), holder.quantity.getValue(), (PrescribedMedicineModel) shoppingCartList.get(position).getProductlist(), false, shoppingCartList.get(position).getType());
                            shoppingCartDataRef.child(shoppingCartList.get(position).getProductid()).setValue(sH);
                            Log.d("CHECKED", "okaych");

                        } else {
                            ShoppingCartModel sH = new ShoppingCartModel(shoppingCartList.get(position).getProductid(), holder.quantity.getValue(), shoppingCartList.get(position).getProductlist(), false, shoppingCartList.get(position).getType());
                            shoppingCartDataRef.child(shoppingCartList.get(position).getProductid()).setValue(sH);
                        }
                    }
                }
                else{
                    shoppingCartList.get(position).setChecked(false);
                    if(user instanceof PatientModel) {
                        Log.d("CHECKED", "okay");
                        if(shoppingCartList.get(position).getProductlist() instanceof PrescribedMedicineModel){
                            ShoppingCartModel sH = new ShoppingCartModel(shoppingCartList.get(position).getProductid(), holder.quantity.getValue(), (PrescribedMedicineModel) shoppingCartList.get(position).getProductlist(), false, shoppingCartList.get(position).getType());
                            shoppingCartDataRef.child(shoppingCartList.get(position).getProductid()).setValue(sH);

                        }
                        else {
                            ShoppingCartModel sH = new ShoppingCartModel(shoppingCartList.get(position).getProductid(), holder.quantity.getValue(), shoppingCartList.get(position).getProductlist(), false, shoppingCartList.get(position).getType());
                            shoppingCartDataRef.child(shoppingCartList.get(position).getProductid()).setValue(sH);
                        }
                    }
                }
            }
        });
        holder.check.setChecked(shoppingCartList.get(position).isChecked());
        holder.medicinePrice.setText("Price: Php " + shoppingCartList.get(position).getProductlist().getPrice());
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

        CheckBox check;
        ImageView medicineImage;
        TextView medicineInformation;
        TextView medicinePrice;
        HorizontalNumberPicker quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.shopping_cart_check_box);
            medicineImage = itemView.findViewById(R.id.shopping_cart_img);
            medicineInformation = itemView.findViewById(R.id.shopping_cart_medicine_details);
            medicinePrice = itemView.findViewById(R.id.shopping_cart_medicine_price);
            quantity = itemView.findViewById(R.id.shopping_cart_number_picker);

        }
    }
}
