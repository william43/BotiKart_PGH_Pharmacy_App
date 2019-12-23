package com.example.pgh_pharmacy_app.activity.Admin;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin.AdminCheckAdapter;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.example.pgh_pharmacy_app.model.CompletedModel;
import com.example.pgh_pharmacy_app.model.PendingAdminProcessModel;
import com.example.pgh_pharmacy_app.model.PendingModel;
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
import java.util.Random;

public class AdminCheckPurchase extends AppCompatActivity {

    private static final String TAG = AdminCheckPurchase.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    RecyclerView recyclerView;
    Button pendBTN;
    Button proceedBTN;
    AdminCheckAdapter adminAdapter;
    ArrayList<ShoppingCartModel> s;
    String userid;
    String receiptid;

    DatabaseReference pendingRef;
    DatabaseReference adminReceiptRef;
    DatabaseReference userRef;
    DatabaseReference randomRef;
    String randomnum;
    boolean allow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_purchases);

        Intent intent = getIntent();
        s = intent.getParcelableArrayListExtra("purchases");
        userid = intent.getStringExtra("user");
        receiptid = intent.getStringExtra("receipt");


        recyclerView = (RecyclerView) findViewById(R.id.admin_check_purchases_RV);
        pendBTN = (Button) findViewById(R.id.admin_pending);
        pendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {pendingRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(userid);

                checkForSmsPermission();
                if(allow) {
                    pendingRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(userid);
                    adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin");
                    String pendingID = pendingRef.push().getKey();
                    for (int i = 0; i < s.size(); i++) {
                        if (s.get(i).getProductlist() instanceof PrescribedMedicineModel) {
                            ShoppingCartModel sh = new PendingModel(s.get(i).getProductid(), s.get(i).getQuantiyperProduct(), (PrescribedMedicineModel) s.get(i).getProductlist(), s.get(i).isChecked(), s.get(i).getType(), pendingID);
                            sh.setType("pending");
                            pendingRef.child(s.get(i).getProductid()).setValue(sh);
                        } else {
                            ShoppingCartModel sh = new PendingModel(s.get(i).getProductid(), s.get(i).getQuantiyperProduct(), s.get(i).getProductlist(), s.get(i).isChecked(), s.get(i).getType(), pendingID);
                            sh.setType("pending");
                            pendingRef.child(s.get(i).getProductid()).setValue(sh);
                        }


                    }
                    adminReceiptRef.child(receiptid).removeValue();
                    adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin");
                    AdminProcessModel ad = new PendingAdminProcessModel(pendingID, false, userid, "pending");
                    adminReceiptRef.child(pendingID).setValue(ad);
                    sendSMS();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Permissions were not granted to send sms", Toast.LENGTH_LONG).show();
                }

            }
        });
        proceedBTN = (Button) findViewById(R.id.admin_proceed);
        proceedBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), ProceedPurchaseAdminActivity.class);
                intent.putParcelableArrayListExtra("purchases", s);
                intent.putExtra("user", userid);
                intent.putExtra("receipt", receiptid);
                startActivity(intent);

            }
        });

        adminAdapter = new AdminCheckAdapter(this, s);

        recyclerView.setAdapter(adminAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin").child(receiptid).child("isprocessed");
        adminReceiptRef.setValue(false);
    }


    /**
     * Checks whether the app has SMS permission.
     */
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            enableSmsButton();
        }
    }

    /**
     * Makes the sms button (message icon) invisible so that it can't be used,
     * and makes the Retry button visible.
     */
    private void disableSmsButton() {
        allow = false;
        /*
        Toast.makeText(this, R.string.sms_disabled, Toast.LENGTH_LONG).show();
        sendSMS.setVisibility(View.INVISIBLE);
        Button retryButton = (Button) findViewById(R.id.button_retry);
        retryButton.setVisibility(View.VISIBLE);

         */
    }

    /**
     * Makes the sms button (message icon) visible so that it can be used.
     */
    private void enableSmsButton() {
        allow = true;
        //sendSMS.setVisibility(View.VISIBLE);
    }

    /**
     * Processes permission request codes.
     *
     * @param requestCode  The request code passed in requestPermissions()
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // For the requestCode, check if permission was granted or not.
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Enable sms button.
                    //enableSmsButton();
                } else {
                    // Permission denied.
                    Log.d(TAG, getString(R.string.failure_permission));
                    Toast.makeText(this, getString(R.string.failure_permission),
                            Toast.LENGTH_LONG).show();
                    // Disable the sms button.
                    disableSmsButton();
                }
            }
        }
    }

    /**
     * Sends an intent to start the activity.
     *
     * @param view  View (Retry button) that was clicked.
     */
    public void retryApp(View view) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(intent);
    }

    public void sendSMS(){
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("number");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String num = dataSnapshot.getValue(String.class);
                String message = "BOTIKART: NOTIFICATION\n" +
                        "Proceed to" + " Pended Office " + ". Your orders were pended.";
                String scAddress = null;
                PendingIntent sentIntent = null, deliveryIntent = null;
                checkForSmsPermission();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num, scAddress, message,
                        sentIntent, deliveryIntent);
                Random random = new Random();
                randomnum = String.format("%04d", random.nextInt(10000));
                randomRef.setValue(  "Pen " + randomnum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
