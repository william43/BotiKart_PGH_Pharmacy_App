package com.example.pgh_pharmacy_app.activity.ShoppingCart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.HorizontalNumberPicker;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.ShoppingCart.ShoppingCartAdapter;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckShoppingCartActivity extends AppCompatActivity{


    RecyclerView shoppingCartRV;
    EditText searchVoucherET;
    ImageView searchVouceerIV;

    TextView userCredits;
    ToggleButton useCredits;

    CheckBox checkAll;
    TextView totalPrice;
    Button checkoutBTN;

    ImageView back;

    ProgressDialog dialog;

    UserModel user;

    public DatabaseReference shoppingCartDataRef;
    public ArrayList<ShoppingCartModel> shoppingCartList = new ArrayList<>();
    public ShoppingCartAdapter shoppingCartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_check_cart);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Medicines");
        dialog.show();

        Log.d("CHECKCHART", user.getFirstname() + "HEHEHEHE");
        if(user instanceof PatientModel) {
            shoppingCartDataRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(user.getId());
            shoppingCartDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    shoppingCartList.clear();
                    Log.d("CHECKCHART", dataSnapshot.getKey() + " " + dataSnapshot.getChildrenCount() + "IIIIEEEAAAAAHEHEHEHE");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String type = dataSnapshot1.child("type").getValue(String.class);
                        String medType = dataSnapshot1.child("productlist").child("type").getValue(String.class);
                        if (type.equalsIgnoreCase("cart")) {
                            if(medType != null){
                                PrescribedMedicineModel p = dataSnapshot1.child("productlist").getValue(PrescribedMedicineModel.class);

                                Log.d("INSTANCE", "prescribed " + p.getGenericName());
                                String id = dataSnapshot1.child("productid").getValue(String.class);
                                boolean checked = dataSnapshot1.child("checked").getValue(Boolean.class);
                                int quantity = dataSnapshot1.child("quantiyperProduct").getValue(Integer.class);
                                ShoppingCartModel s = new ShoppingCartModel(id,quantity,p,checked,"cart");
                                shoppingCartList.add(s);
                            }
                            else {
                                ShoppingCartModel s = dataSnapshot1.getValue(ShoppingCartModel.class);
                                Log.d("INSTANCE", "not prescribed " + s.getProductlist().getGenericName());
                                shoppingCartList.add(s);
                            }
                        }
                        //Log.d("CHECKCHART", s.getProductlist() + "EEEAAAAAHEHEHEHE");

                    }

                    Log.d("CHECKCHART", shoppingCartList.size() + "AAAAAHEHEHEHE");
                    shoppingCartAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    updateTotal();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(user instanceof OneTimeUsersModel){
            shoppingCartList = intent.getParcelableArrayListExtra("shoppinglist");
            dialog.dismiss();
        }
        shoppingCartAdapter = new ShoppingCartAdapter(shoppingCartList, this, user);

        shoppingCartRV = findViewById(R.id.shoppingcartRV);

        shoppingCartRV.setAdapter(shoppingCartAdapter);
        shoppingCartRV.setLayoutManager(new LinearLayoutManager(this));

        searchVoucherET = findViewById(R.id.searchVoucher);
        searchVouceerIV = findViewById(R.id.voucherNext);

        userCredits = findViewById(R.id.creditsTV);
        useCredits = findViewById(R.id.userCredits);

        checkAll = findViewById(R.id.checkAll);
        totalPrice = findViewById(R.id.TotalPriceTV);
        checkoutBTN = findViewById(R.id.placeOrderBTN);
        checkoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CheckoutActivity.class);

                if(user.getType().equals("admin")) {
                    intent.putExtra("user", (AdminModel)user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Admin",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user.getType().equals("patient")){
                    intent.putExtra("user", (PatientModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Patient",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user.getType().equals("onetime")){
                    intent.putExtra("user", (OneTimeUsersModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT).show();
                }
                ArrayList<ShoppingCartModel> s = new ArrayList<>();

                for(int i = 0; i < shoppingCartList.size(); i++){
                    if(shoppingCartList.get(i).isChecked()){
                        ShoppingCartModel sh = shoppingCartList.get(i);
                        s.add(sh);
                    }
                }

                intent.putParcelableArrayListExtra("shoppinglist", s);

                startActivity(intent);
            }
        });


        back = findViewById(R.id.shopping_cart_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void updateTotal(){
        double total = 0;
        for(int i = 0 ; i < shoppingCartList.size(); i++){
            total = total + (shoppingCartList.get(i).getProductlist().getPrice() * shoppingCartList.get(i).getQuantiyperProduct());
        }
        totalPrice.setText("Total: Php " + total );

    }
}
