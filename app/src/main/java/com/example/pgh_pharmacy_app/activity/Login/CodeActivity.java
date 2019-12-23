package com.example.pgh_pharmacy_app.activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CodeActivity extends AppCompatActivity {


    public Button verifyBTN;
    public EditText verifyCodeET;
    public TextView userNumberTV;
    public UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash_code);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        verifyBTN = findViewById(R.id.verifyBTN);
        verifyCodeET = findViewById(R.id.verifycodeET);
        userNumberTV = findViewById(R.id.usernumberTV);

        verifyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), LoginSplashTutorialActivity.class);



                myIntent.putExtra("user",  (OneTimeUsersModel)user);

                startActivity(myIntent);
            }
        });


    }


}
