package com.example.pgh_pharmacy_app.activity.ShoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;

public class WaitOrderActivity extends AppCompatActivity {

    UserModel user;
    TextView phoneNumber;
    RelativeLayout cardLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_wait_order);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        cardLayout = findViewById(R.id.waitOrder);
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

                startActivity(intent);
            }
        });
        phoneNumber = findViewById(R.id.wait_phone_number);

    }
}
