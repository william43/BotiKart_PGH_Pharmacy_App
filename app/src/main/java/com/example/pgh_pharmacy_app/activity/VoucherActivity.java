package com.example.pgh_pharmacy_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pgh_pharmacy_app.R;

public class VoucherActivity extends AppCompatActivity {


    ImageView back;
    ImageView voucherProfile;
     TextView voucherTitle;
     RelativeLayout voucherHeader;
     TextView text;

     int pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        Intent i = getIntent();
        pos = i.getIntExtra("position", 0);

        back = findViewById(R.id.back_voucher);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        voucherProfile = findViewById(R.id.voucher_profile);
        voucherTitle = findViewById(R.id.voucher_title);
        voucherHeader = findViewById(R.id.voucher_header);
        text = findViewById(R.id.text);
        if(pos == 0){
            voucherHeader.setBackgroundResource(R.drawable.front_pgh);
            voucherTitle.setText("UP COLLEGE OF NURSING TOPS THE 2019 BOARD EXAM IN NOVEMBER");
            text.setText("MANILA, Philippines – The Professional Regulation Commission (PRC) hailed the top performing schools in the November 2019 nurse licensure examination (NLE) or nursing board exam as results are released on Thursday, December 5, 2019 or in fourteen (14) working days after the last day of exam.\n" +
                    "\n" +
                    "University of Santo Tomas (UST), Saint Louis University (SLU) and De La Salle University - Dasmarinas garnered the highest place with 100% passing rate.\n" +
                    "\n" +
                    "Velez College, Benguet State University - La Trinidad, San Pedro College - Davao City and Southern Luzon State University - Lucban complete the top 5 of highest performing schools.\n" +
                    "\n" +
                    "PRC conducted the nurse licensure exam in Manila, all regional offices nationwide (Baguio, Cagayan De Oro, Cebu, Davao, Iloilo, Legazpi, Lucena, Pagadian, Tacloban, Tuguegarao) and Zamboanga on November 16 and 17, 2018.\n");

        }
        else if(pos == 1){
            voucherHeader.setBackgroundResource(R.drawable.pgh_state_of_the_art);
            voucherTitle.setText("STATE OF THE ART FACILITIES IN PGH 100");
            text.setText("RITM provides state-of-the-art facilities dedicated to research, training, clinical care, and biologicals manufacturing. It houses laboratories, hospital facilities, biologicals production facilities, isolate bank, training center, and dormitory.");
        }
        else if(pos == 2){
            voucherHeader.setBackgroundResource(R.drawable.pgh_world_class);
            voucherTitle.setText("WORLD CLASS PROFESSIONALS");
            text.setText("Executive Health Check\n" +
                    "\n" +
                    "Be Healthy. Stay Healthy.\n" +
                    "\n" +
                    "Our team of doctors and healthcare professionals are on hand to guide you through your wellness journey. Our Executive Health Check ensures total care whether in gaining back your good health or maintaining your fitness level.\n" +
                    "\n " +
                    "Vision Center\n" +
                    "\n" +
                    "The future is clear.\n" +
                    "\n" +
                    "Achieve better vision with our world-class eye care facility. Entrust your eyes to our team of eye specialists and staff.");
        }

    }
}
