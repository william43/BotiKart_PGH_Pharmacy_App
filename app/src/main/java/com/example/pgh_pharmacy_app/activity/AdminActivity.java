package com.example.pgh_pharmacy_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.AdminPagerAdapter;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.PurchasesPagerAdapter;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.CompletePurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.CounterPurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.PendingPurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.ProcessingPurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.QueuePurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.admin.OverTheCounterViewAdmin;
import com.example.pgh_pharmacy_app.fragment.MainActivty.admin.ProhibitedViewAdmin;
import com.example.pgh_pharmacy_app.model.CompletedModel;
import com.example.pgh_pharmacy_app.model.InProcessModel;
import com.example.pgh_pharmacy_app.model.PendingModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.QueueModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.ToPayModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;
    UserModel user;    ArrayList<ShoppingCartModel> purchaseList = new ArrayList<>();
    public DatabaseReference purchasesDataRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        user = intent.getExtras().getParcelable("user");

        viewPager = findViewById(R.id.admin_viewPager);
        tabLayout = findViewById(R.id.admin_tabLayout);
        purchasesDataRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart");
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setUpViewPager(final ViewPager viewPager) {
        final AdminPagerAdapter adapter = new AdminPagerAdapter(getSupportFragmentManager());

        OverTheCounterViewAdmin otcounter = new OverTheCounterViewAdmin();
        ProhibitedViewAdmin prohibited = new ProhibitedViewAdmin();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user",user);

        otcounter.setArguments(bundle);
        prohibited.setArguments(bundle);


        adapter.addFragment(otcounter,"Available Drugs");
        adapter.addFragment(prohibited, "Picture of Receipt");

        viewPager.setAdapter(adapter);

    }
}
