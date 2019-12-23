package com.example.pgh_pharmacy_app.activity.ShoppingCart;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.ShoppingCart.CheckoutAdapter;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    RecyclerView finalShoppingCartRV;


    TextView addressUser;
    ImageView addressNext;

    TextView finalTotalPrice;
    Button finalOrder;

    ImageView back;

    ArrayList<ShoppingCartModel> shoppinglist = new ArrayList<>();
    CheckoutAdapter checkoutAdapter;
    UserModel user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_check_out);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        shoppinglist = intent.getParcelableArrayListExtra("shoppinglist");

        finalShoppingCartRV = findViewById(R.id.finalShoppingCartRV);
        checkoutAdapter = new CheckoutAdapter(shoppinglist, this);

        finalShoppingCartRV.setAdapter(checkoutAdapter);
        finalShoppingCartRV.setLayoutManager(new LinearLayoutManager(this));

        addressUser = findViewById(R.id.userAddress);
        addressNext = findViewById(R.id.searchAddress);

        finalTotalPrice = findViewById(R.id.finalTotalPrice);
        finalOrder = findViewById(R.id.finalBTN);
        finalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
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
                else if(user.getType().equals("patient")) {
                    intent.putExtra("user", (OneTimeUsersModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT);
                }
                intent.putParcelableArrayListExtra("shoppinglist", shoppinglist);
                startActivity(intent);

            }
        });

        back = findViewById(R.id.shopping_checkout_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void updateTotal(){
        double total = 0;
        for(int i = 0 ; i < shoppinglist.size(); i++){
            total = total + (shoppinglist.get(i).getProductlist().getPrice() * shoppinglist.get(i).getQuantiyperProduct());
        }
        finalTotalPrice.setText("Total: Php " + total );

    }
}
