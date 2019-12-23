package com.example.pgh_pharmacy_app.activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    EditText firstname;
    EditText lastname;
    EditText phone;
    EditText address;
    EditText username;
    EditText password;
    EditText confirmPass;

    Button createUser;

    DatabaseReference userRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        firstname = findViewById(R.id.firstname_ET);
        lastname = findViewById(R.id.lastname_ET);
        phone = findViewById(R.id.phone_ET);
        address = findViewById(R.id.address_ET);
        username = findViewById(R.id.usernameET);
        password = findViewById(R.id.password_ET);
        confirmPass = findViewById(R.id.confirm_password_ET);

        createUser = findViewById(R.id.confirm_user_sign_up);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()
                && !phone.getText().toString().isEmpty() && !address.getText().toString().isEmpty()
                && !username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()
                && !confirmPass.getText().toString().isEmpty()){
                    if(password.getText().toString().equals(confirmPass.getText().toString())){
                        userRef = FirebaseDatabase.getInstance().getReference().child("users");
                        String id = userRef.push().getKey();
                        UserModel u = new PatientModel(lastname.getText().toString(),firstname.getText().toString(),
                                username.getText().toString(),password.getText().toString(),id,"patient",address.getText().toString(),
                                phone.getText().toString());
                        userRef.child(id).setValue(u);

                        Intent intent = new Intent(getApplicationContext(), LoginSplashTutorialActivity.class);
                        intent.putExtra("user", u);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Password is inconsistent", Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Fill in the necessary details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
