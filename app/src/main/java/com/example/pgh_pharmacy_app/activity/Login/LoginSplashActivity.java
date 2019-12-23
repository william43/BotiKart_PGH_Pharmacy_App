package com.example.pgh_pharmacy_app.activity.Login;

import android.content.Intent;
import android.graphics.PathEffect;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.AdminActivity;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.dialogs.loginActivity.LoginCodeActivityDialog;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LoginSplashActivity extends AppCompatActivity implements LoginCodeActivityDialog.ExampleDialogListener{


    public Button codeBTN;
    public Button loginBTN;
    public Button signupBTN;

    public ArrayList<UserModel> userList;
    public DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);


        userList = new ArrayList<>();
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");
        userDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String type = dataSnapshot1.child("type").getValue(String.class);
                    if(type.equalsIgnoreCase("admin")) {
                        UserModel a = dataSnapshot1.getValue(AdminModel.class);
                        userList.add(a);
                    }
                    else if(type.equalsIgnoreCase("patient")){
                        UserModel a = dataSnapshot1.getValue(PatientModel.class);
                        userList.add(a);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        codeBTN = findViewById(R.id.codeBTN);
        loginBTN = findViewById(R.id.loginBTN);
        signupBTN = findViewById(R.id.signupBTN);
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSplashActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        EditText mEmail = (EditText) findViewById(R.id.etEmail);
        EditText mPassword = (EditText) findViewById(R.id.etPassword);

        codeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){

                    boolean loggedin = false;

                    for(int i = 0; i < userList.size(); i++) {
                        if (mEmail.getText().toString().equals(userList.get(i).getUsername()) &&
                                mPassword.getText().toString().equals(userList.get(i).getPassword())) {

                            if (userList.get(i).getType().equals("admin")) {
                                Intent myIntent = new Intent(getBaseContext(), AdminActivity.class);
                                myIntent.putExtra("user", (AdminModel) userList.get(i));
                                Toast.makeText(LoginSplashActivity.this,
                                        "Is an Admin",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(myIntent);
                            } else if (userList.get(i).getType().equals("patient")) {
                                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                                myIntent.putExtra("user", (PatientModel) userList.get(i));
                                Toast.makeText(LoginSplashActivity.this,
                                        "Is an patient",
                                        Toast.LENGTH_SHORT).show();

                                startActivity(myIntent);
                            }
                            loggedin = true;
                            //Admin View
                            break;
                        }

                    }
                    if(!loggedin)
                        Toast.makeText(LoginSplashActivity.this,
                                "Invalid Credentials",
                                Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginSplashActivity.this,
                            "Please enter the necessary details",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    public void openDialog() {
        LoginCodeActivityDialog exampleDialog = new LoginCodeActivityDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String username, String password) {

        /*
        try{
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(username,null,password,null,null);
            Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
        }
        */
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("smsto:"));
            i.setType("vnd.android-dir/mms-sms");
            i.putExtra("address", new String(username));
            i.putExtra("sms_body",password);
            startActivity(Intent.createChooser(i, "Send sms via:"));
            Toast.makeText(this, "SMSsent", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
        }

        Intent myIntent = new Intent(getBaseContext(),   CodeActivity.class);
        UserModel user = new OneTimeUsersModel(username, password);
        myIntent.putExtra("user", (OneTimeUsersModel) user);
        startActivity(myIntent);

    }

}
