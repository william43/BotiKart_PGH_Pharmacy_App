package com.example.pgh_pharmacy_app.activity.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.ShoppingCart.CheckoutActivity;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin.AdminCheckAdapter;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin.AdminCheckPendingAdapter;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.PendingAdminProcessModel;
import com.example.pgh_pharmacy_app.model.PendingModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingPurchaseAdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminCheckPendingAdapter adminAdapter;

    ArrayList<ShoppingCartModel> s;
    String userid;
    String receiptid;

    Button proceed;
    Button redo;

    DatabaseReference adminRef;
    DatabaseReference userRef;
    DatabaseReference shoppingcartRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_purchases);

        Intent intent = getIntent();
        s = intent.getParcelableArrayListExtra("purchases");
        userid = intent.getStringExtra("user");
        receiptid = intent.getStringExtra("receipt");


        proceed = findViewById(R.id.admin_pending_final_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CheckoutActivity.class);
                shoppingcartRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(userid);

                ArrayList<ShoppingCartModel> sChecked = new ArrayList<>();
                ArrayList<ShoppingCartModel> snotChecked = new ArrayList<>();
                for(int i = 0; i < s.size(); i++){
                    if(s.get(i).isChecked()){
                        ShoppingCartModel sh = s.get(i);
                        sChecked.add(sh);
                    }
                    else{
                        ShoppingCartModel sh = s.get(i);
                        snotChecked.add(sh);
                    }
                }
                userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String type = dataSnapshot.child("type").getValue(String.class);
                        UserModel u ;
                        if(type.equalsIgnoreCase("patient")) {
                            u = dataSnapshot.getValue(PatientModel.class);
                        }
                        else{
                            u = dataSnapshot.getValue(OneTimeUsersModel.class);
                        }

                        String pendingID = shoppingcartRef.push().getKey();
                        adminRef = FirebaseDatabase.getInstance().getReference().child("admin");
                        for(int j = 0; j < snotChecked.size(); j++){
                            if(s.get(j).getProductlist() instanceof PrescribedMedicineModel){
                                ShoppingCartModel sh = new PendingModel(snotChecked.get(j).getProductid(),snotChecked.get(j).getQuantiyperProduct(),(PrescribedMedicineModel) snotChecked.get(j).getProductlist(),snotChecked.get(j).isChecked(),snotChecked.get(j).getType(),pendingID);
                                sh.setType("pending");
                                shoppingcartRef.child(snotChecked.get(j).getProductid()).setValue(sh);
                            }
                            else{
                                ShoppingCartModel sh = new PendingModel(snotChecked.get(j).getProductid(),snotChecked.get(j).getQuantiyperProduct(), snotChecked.get(j).getProductlist(),snotChecked.get(j).isChecked(),snotChecked.get(j).getType(),pendingID);
                                sh.setType("pending");
                                shoppingcartRef.child(snotChecked.get(j).getProductid()).setValue(sh);
                            }
                        }
                        AdminProcessModel ad = new PendingAdminProcessModel(pendingID,false,userid,"pending");
                        adminRef.child(pendingID).setValue(ad);
                        if(u.getType().equals("patient")){
                            intent.putExtra("user", (PatientModel) u);
                            Toast.makeText(getApplicationContext(),
                                    "Is an Patient",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(u.getType().equals("onetime")){
                            intent.putExtra("user", (OneTimeUsersModel) u);
                            Toast.makeText(getApplicationContext(),
                                    "Is an One Time User",
                                    Toast.LENGTH_SHORT).show();
                        }
                        intent.putExtra("receipt", receiptid);
                        intent.putParcelableArrayListExtra("shoppinglist", sChecked);

                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        redo = findViewById(R.id.admin_pending_final_redo);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingcartRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(userid);
                for (int i = 0; i < s.size(); i++){
                    shoppingcartRef.child(s.get(i).getProductid()).removeValue();
                }
                adminRef = FirebaseDatabase.getInstance().getReference().child("admin");
                adminRef.child(receiptid).removeValue();
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.admin_pending_final_RV);

        adminAdapter = new AdminCheckPendingAdapter(this, s);
        recyclerView.setAdapter(adminAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }
}
