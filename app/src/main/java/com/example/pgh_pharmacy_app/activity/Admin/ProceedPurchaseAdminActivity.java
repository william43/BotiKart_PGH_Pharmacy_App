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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.AdminActivity;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin.AdminCheckAdapter;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.example.pgh_pharmacy_app.model.CompletedModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.ToPayAdminProcessModel;
import com.example.pgh_pharmacy_app.model.ToPayModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class ProceedPurchaseAdminActivity extends AppCompatActivity {

    private static final String TAG = ProceedPurchaseAdminActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    LinearLayout total;
    RelativeLayout bottom;
    RecyclerView finalpurchases;
    AdminCheckAdapter adminAdapter;
    RelativeLayout send;
    Button sendSMS;
    Spinner spinner;

    Button proceedBTN;
    EditText userCode;

    ArrayList<ShoppingCartModel> s;
    String userid;
    String receiptid;


    DatabaseReference pendingRef;
    DatabaseReference adminReceiptRef;
    DatabaseReference userRef;
    DatabaseReference randomRef;
    String randomnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_proceed_purchase);

        Intent intent = getIntent();
        s = intent.getParcelableArrayListExtra("purchases");
        userid = intent.getStringExtra("user");
        receiptid = intent.getStringExtra("receipt");

        spinner = findViewById(R.id.admin_purchases_spinner);
        bottom = findViewById(R.id.bottom_admin);
        bottom.setVisibility(View.INVISIBLE);
        total = findViewById(R.id.admin_total);
        total.setVisibility(View.INVISIBLE);
        proceedBTN = findViewById(R.id.admin_purchase_next);
        userCode = findViewById(R.id.admin_purchase_user_code);
        proceedBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userCode.getText().toString().equals(randomnum)){
                    pendingRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(userid);
                    adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin");
                    String completeID = pendingRef.push().getKey();
                    ArrayList<ShoppingCartModel> sho = new ArrayList<>();

                    for(int i = 0; i < s.size(); i++){
                        if(s.get(i).getProductlist() instanceof PrescribedMedicineModel){
                            ShoppingCartModel sh = new CompletedModel(s.get(i).getProductid(),s.get(i).getQuantiyperProduct(),(PrescribedMedicineModel)s.get(i).getProductlist(),s.get(i).isChecked(),s.get(i).getType(),completeID);
                            sh.setType("counter");
                            pendingRef.child(s.get(i).getProductid()).setValue(sh);
                            sho.add(sh);
                        }
                        else{
                            ShoppingCartModel sh = new CompletedModel(s.get(i).getProductid(),s.get(i).getQuantiyperProduct(),s.get(i).getProductlist(),s.get(i).isChecked(),s.get(i).getType(),completeID);
                            sh.setType("counter");
                            pendingRef.child(s.get(i).getProductid()).setValue(sh);
                            sho.add(sh);
                        }

                    }

                    adminReceiptRef.child(receiptid).removeValue();
                    Toast.makeText(getApplicationContext(), "Transaction Complete", Toast.LENGTH_SHORT).show();
                    userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            UserModel user = dataSnapshot.getValue(UserModel.class);

                            Intent intent = new Intent(ProceedPurchaseAdminActivity.this, AdminActivity.class);
                            intent.putExtra("user", user);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong User Code, Please try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        send = findViewById(R.id.admin_purchases_send_details);

        sendSMS = findViewById(R.id.admin_purchases_send);
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                finalpurchases.setVisibility(View.VISIBLE);
                send.setVisibility(View.INVISIBLE);

                pendingRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(userid);
                adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin");
                String pendingID = pendingRef.push().getKey();
                ArrayList<ShoppingCartModel> sho = new ArrayList<>();
                for(int i = 0; i < s.size(); i++){
                    if(s.get(i).getProductlist() instanceof PrescribedMedicineModel){
                        ShoppingCartModel sh = new ToPayModel(s.get(i).getProductid(),s.get(i).getQuantiyperProduct(),(PrescribedMedicineModel)s.get(i).getProductlist(),s.get(i).isChecked(),s.get(i).getType(),pendingID, spinner.getSelectedItem().toString());

                        sh.setType("counter");
                        pendingRef.child(s.get(i).getProductid()).setValue(sh);
                        sho.add(sh);
                    }
                    else{
                        ShoppingCartModel sh = new ToPayModel(s.get(i).getProductid(),s.get(i).getQuantiyperProduct(),s.get(i).getProductlist(),s.get(i).isChecked(),s.get(i).getType(),pendingID, spinner.getSelectedItem().toString());
                        sh.setType("counter");
                        pendingRef.child(s.get(i).getProductid()).setValue(sh);
                        sho.add(sh);
                    }

                }
                adminReceiptRef.child(receiptid).removeValue();
                adminReceiptRef = FirebaseDatabase.getInstance().getReference().child("admin");
                AdminProcessModel ad = new ToPayAdminProcessModel(pendingID,false,userid,"counter", spinner.getSelectedItem().toString());
                adminReceiptRef.child(pendingID).setValue(ad);
                receiptid = pendingID;
                sendSMS();
            }
        });

        finalpurchases = findViewById(R.id.admin_purchases_final_RV);
        adminAdapter = new AdminCheckAdapter(this, s);

        // Check to see if SMS is enabled.
        checkForSmsPermission();

        finalpurchases.setAdapter(adminAdapter);
        finalpurchases.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        finalpurchases.setVisibility(View.INVISIBLE);

        randomRef = FirebaseDatabase.getInstance().getReference().child("random").child(userid);
        randomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                randomnum = String.valueOf(dataSnapshot.getValue(String.class));
                String[] temp = randomnum.split(" " );
                randomnum = temp[1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        Toast.makeText(this, R.string.sms_disabled, Toast.LENGTH_LONG).show();
        sendSMS.setVisibility(View.INVISIBLE);
        Button retryButton = (Button) findViewById(R.id.button_retry);
        retryButton.setVisibility(View.VISIBLE);
    }

    /**
     * Makes the sms button (message icon) visible so that it can be used.
     */
    private void enableSmsButton() {
        sendSMS.setVisibility(View.VISIBLE);
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
                    enableSmsButton();
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
                        "Proceed to" + spinner.getSelectedItem().toString() + ". Your orders are ready.";
                String scAddress = null;
                PendingIntent sentIntent = null, deliveryIntent = null;
                checkForSmsPermission();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num, scAddress, message,
                        sentIntent, deliveryIntent);
                Random random = new Random();
                randomnum = String.format("%04d", random.nextInt(10000));
                randomRef.setValue(  "Pro " + randomnum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
