package com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.SocialServicesAdapter;
import com.example.pgh_pharmacy_app.model.Vouchers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServicesFragment extends Fragment {

    View myFragment;

    RecyclerView servicesRV;

    SocialServicesAdapter socialServicesAdapter;

    DatabaseReference vouchersRef;

    ArrayList<Vouchers> vouchersList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.fragment_main_services, container, false);

        servicesRV = myFragment.findViewById(R.id.vouchers_RV);

        vouchersRef = FirebaseDatabase.getInstance().getReference().child("vouchers");
        vouchersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vouchersList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Vouchers v = dataSnapshot1.getValue(Vouchers.class);
                    vouchersList.add(v);
                }

                socialServicesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        socialServicesAdapter = new SocialServicesAdapter(vouchersList,getContext());
        servicesRV.setAdapter(socialServicesAdapter);
        servicesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        return myFragment;
    }

}
