package com.example.pgh_pharmacy_app.activity.ShoppingCart;

import android.content.Intent;
import android.media.midi.MidiInputPort;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.example.pgh_pharmacy_app.model.CartModel;
import com.example.pgh_pharmacy_app.model.InProcessModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.ToPayAdminProcessModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlaceOrderActivity extends AppCompatActivity {


    RelativeLayout cardLayout;

    TextView codeNumber;
    public DatabaseReference shoppingCartDataRef;
    TextView phoneNumber;
    UserModel user;
    String receipt;
    ArrayList<ShoppingCartModel> shoppinglist = new ArrayList<>();

    DatabaseReference adminReceiptRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_placeorder);

        Intent intent = getIntent();
        receipt = intent.getParcelableExtra("receipt");
        user = intent.getParcelableExtra("user");
        shoppinglist = intent.getParcelableArrayListExtra("shoppinglist");
        shoppingCartDataRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(user.getId());
        cardLayout = findViewById(R.id.placeOrder);
        cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                else if(user.getType().equals("onetime")) {
                    intent.putExtra("user", (OneTimeUsersModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT);
                }

                String processID = shoppingCartDataRef.push().getKey();
                for(int i = 0; i < shoppinglist.size(); i++){
                    if(shoppinglist.get(i).getProductlist() instanceof PrescribedMedicineModel) {
                        Log.d("INSTANCE", "prescribed");
                        ShoppingCartModel s = new InProcessModel(shoppinglist.get(i).getProductid(), shoppinglist.get(i).getQuantiyperProduct(), (PrescribedMedicineModel)shoppinglist.get(i).getProductlist(), shoppinglist.get(i).isChecked(), shoppinglist.get(i).getType(), processID);
                        s.setType("processing");
                        shoppingCartDataRef.child(s.getProductid()).setValue(s);
                    }
                    else{
                        ShoppingCartModel s = new InProcessModel(shoppinglist.get(i).getProductid(), shoppinglist.get(i).getQuantiyperProduct(), shoppinglist.get(i).getProductlist(), shoppinglist.get(i).isChecked(), shoppinglist.get(i).getType(), processID);
                        s.setType("processing");
                        shoppingCartDataRef.child(s.getProductid()).setValue(s);
                    }
                }
                if(receipt != null){
                    adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin").child(receipt);
                    adminReceiptRef.removeValue();
                }
                adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin");
                AdminProcessModel ad = new AdminProcessModel(processID,false,user.getId());
                adminReceiptRef.child(processID).setValue(ad);

                startActivity(intent);
            }
        });


        phoneNumber = findViewById(R.id.phone_number);
        phoneNumber.setText(user.getPassword());


    }
}
