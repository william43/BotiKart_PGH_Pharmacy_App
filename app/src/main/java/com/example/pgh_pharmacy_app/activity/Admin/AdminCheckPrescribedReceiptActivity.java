package com.example.pgh_pharmacy_app.activity.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;
import com.zolad.zoominimageview.ZoomInImageView;

public class AdminCheckPrescribedReceiptActivity extends AppCompatActivity {

    ZoomInImageView img;
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_prescribed_receipt);

        img = findViewById(R.id.admin_check_receipt_img);
        back = findViewById(R.id.admin_check_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}


