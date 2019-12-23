package com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.Admin.AdminCheckPurchase;
import com.example.pgh_pharmacy_app.activity.Admin.PendingPurchaseAdminActivity;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.ShoppingCart.ShoppingCartAdapter;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.example.pgh_pharmacy_app.model.InProcessModel;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.PendingAdminProcessModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.ToPayAdminProcessModel;
import com.example.pgh_pharmacy_app.model.ToPayModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    Context context;
    ArrayList<AdminProcessModel> a;
    DatabaseReference shoppingRef;

    public AdminAdapter(Context context, ArrayList<AdminProcessModel> a) {
        this.context = context;
        this.a = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.group_view_holder_admin, parent, false);

        // Return a new holder instance
        AdminAdapter.ViewHolder viewHolder  = new AdminAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.osName.setText(a.get(position).getId());
        if(a.get(position) instanceof PendingAdminProcessModel) {
           holder.process.setText("Pending");
           holder.process.setTextColor(context.getResources().getColor(R.color.inprocess));
        }else if(a.get(position) instanceof ToPayAdminProcessModel){
            holder.process.setText("Already at Counter " + ((ToPayAdminProcessModel)a.get(position)).getCounter());
            holder.process.setTextColor(context.getResources().getColor(R.color.inprocess));

        }
        else{
            if (a.get(position).isIsprocessed()) {
                holder.process.setText("Being Processed");
                holder.process.setTextColor(context.getResources().getColor(R.color.inprocess));
            } else {
                holder.process.setText("Not being Processed");
                holder.process.setTextColor(context.getResources().getColor(R.color.notinprocess));
            }
        }
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView osName;
        TextView process;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            osName = (TextView) itemView.findViewById(R.id.admin_receipt_id);
            process = (TextView) itemView.findViewById(R.id.admin_isprocessed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!a.get(getAdapterPosition()).isIsprocessed()) {
                        if(a.get(getAdapterPosition()) instanceof PendingAdminProcessModel) {
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("admin").child(a.get(getAdapterPosition()).getId()).child("isprocessed");
                            data.setValue(true);
                            shoppingRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(a.get(getAdapterPosition()).getUserid());
                            shoppingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    ArrayList<ShoppingCartModel> s = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        String id = dataSnapshot1.child("pendingID").getValue(String.class);
                                        String medtype = dataSnapshot1.child("productlist").child("type").getValue(String.class);

                                        if (a.get(getAdapterPosition()).getId().equals(id)) {
                                            if(medtype != null){
                                                if(medtype.equalsIgnoreCase("prescribed")){

                                                    PrescribedMedicineModel p = dataSnapshot1.child("productlist").getValue(PrescribedMedicineModel.class);
                                                    ShoppingCartModel s1 = dataSnapshot1.getValue(ShoppingCartModel.class);
                                                    s1.setProductlist(p);
                                                    s.add(s1);
                                                }
                                                else if(medtype.equalsIgnoreCase("generic")){
                                                    MedicineModel p = dataSnapshot1.child("productlist").getValue(MedicineModel.class);
                                                    ShoppingCartModel s1 = dataSnapshot1.getValue(ShoppingCartModel.class);
                                                    s1.setProductlist(p);
                                                    s.add(s1);
                                                }
                                            }
                                        }
                                    }

                                    Intent intent = new Intent(context, PendingPurchaseAdminActivity.class);
                                    intent.putParcelableArrayListExtra("purchases", s);
                                    intent.putExtra("user", a.get(getAdapterPosition()).getUserid());
                                    intent.putExtra("receipt", a.get(getAdapterPosition()).getId());
                                    context.startActivity(intent);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else{
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("admin").child(a.get(getAdapterPosition()).getId()).child("isprocessed");
                            data.setValue(true);
                            shoppingRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(a.get(getAdapterPosition()).getUserid());
                            shoppingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    ArrayList<ShoppingCartModel> s = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                        String id = dataSnapshot2.child("inProcessID").getValue(String.class);
                                        String medtype = dataSnapshot2.child("productlist").child("type").getValue(String.class);
                                        if (a.get(getAdapterPosition()).getId().equals(id)) {
                                            if(medtype != null){
                                                if(medtype.equalsIgnoreCase("prescribed")){
                                                    PrescribedMedicineModel p = dataSnapshot2.child("productlist").getValue(PrescribedMedicineModel.class);
                                                    ShoppingCartModel s1 = dataSnapshot2.getValue(ShoppingCartModel.class);
                                                    s1.setProductlist(p);
                                                    s.add(s1);
                                                }
                                                else if(medtype.equalsIgnoreCase("generic")){
                                                    MedicineModel p = dataSnapshot2.child("productlist").getValue(MedicineModel.class);
                                                    ShoppingCartModel s1 = dataSnapshot2.getValue(ShoppingCartModel.class);
                                                    s1.setProductlist(p);
                                                    s.add(s1);
                                                }
                                            }
                                        }
                                    }

                                    Intent intent = new Intent(context, AdminCheckPurchase.class);
                                    intent.putParcelableArrayListExtra("purchases", s);
                                    intent.putExtra("user", a.get(getAdapterPosition()).getUserid());
                                    intent.putExtra("receipt", a.get(getAdapterPosition()).getId());
                                    context.startActivity(intent);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    else{
                        Toast.makeText(context,"This purchase List is already being processed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
