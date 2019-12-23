package com.example.pgh_pharmacy_app.activity.Purchases;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.PurchasesPagerAdapter;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.SectionPagerAdapter;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.CompletePurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.CounterPurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.PendingPurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.ProcessingPurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity.QueuePurchases;
import com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment.OverTheCounterFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment.ProhibitedDrugsFragment;
import com.example.pgh_pharmacy_app.model.CompletedModel;
import com.example.pgh_pharmacy_app.model.InProcessModel;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.PendingModel;
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

public class PurchasesActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;

    UserModel user;
    ArrayList<ShoppingCartModel> purchaseList = new ArrayList<>();
    public DatabaseReference purchasesDataRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        viewPager = findViewById(R.id.purchases_viewPager);
        tabLayout = findViewById(R.id.purchases_tabLayout);


        purchasesDataRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart").child(user.getId());
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void setUpViewPager(final ViewPager viewPager){
        final PurchasesPagerAdapter adapter = new PurchasesPagerAdapter(getSupportFragmentManager());

        ProcessingPurchases process = new ProcessingPurchases();
        CompletePurchases complete = new CompletePurchases();
        QueuePurchases queue = new QueuePurchases();
        CounterPurchases counter = new CounterPurchases();
        PendingPurchases pending = new PendingPurchases();


        purchasesDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                purchaseList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String type = dataSnapshot1.child("type").getValue(String.class);
                    if(type.equalsIgnoreCase("processing")){
                        ShoppingCartModel s = dataSnapshot1.getValue(InProcessModel.class);
                        purchaseList.add(s);
                        //Log.d("PURCHASES", "processing" + s.getProductlist().getGenericName() + " " + s.getQuantiyperProduct());
                    }
                    else if(type.equalsIgnoreCase("queue")){
                        ShoppingCartModel s = dataSnapshot1.getValue(QueueModel.class);
                        purchaseList.add(s);
                    }
                    else if(type.equalsIgnoreCase("pending")){
                        ShoppingCartModel s = dataSnapshot1.getValue(PendingModel.class);
                        purchaseList.add(s);
                    }
                    else if(type.equalsIgnoreCase("complete")){
                        ShoppingCartModel s = dataSnapshot1.getValue(CompletedModel.class);
                        purchaseList.add(s);
                    }
                    else if(type.equalsIgnoreCase("counter")){
                        ShoppingCartModel s = dataSnapshot1.getValue(ToPayModel.class);
                        purchaseList.add(s);
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("purchases",purchaseList);
                bundle.putParcelable("user", user);

                process.setArguments(bundle);
                complete.setArguments(bundle);
                queue.setArguments(bundle);
                counter.setArguments(bundle);
                pending.setArguments(bundle);

                adapter.addFragment(process, "Processing");
                adapter.addFragment(complete, "Complete");
                adapter.addFragment(queue, "Queue");
                adapter.addFragment(counter, "Counter");
                adapter.addFragment(pending, "Pending");

                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
