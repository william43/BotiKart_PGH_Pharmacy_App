package com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.HomeAdapter;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.SocialServicesAdapter;
import com.example.pgh_pharmacy_app.model.HomeNewsModel;
import com.example.pgh_pharmacy_app.model.Vouchers;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View myFragment;

    RecyclerView servicesRV;

    HomeAdapter socialServicesAdapter;

    DatabaseReference vouchersRef;

    ArrayList<HomeNewsModel> vouchersList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         myFragment = inflater.inflate(R.layout.fragment_main_home, container, false);

         servicesRV = myFragment.findViewById(R.id.home_RV);

        ArrayList<String> descr1 = new ArrayList<>();
        descr1.add("New Extensions");
        descr1.add("Better Facilities");
        HomeNewsModel h = new HomeNewsModel("UP COLLEGE OF NURSING TOPS THE 2019 BOARD EXAM IN NOVEMBER",descr1);
        vouchersList.add(h);

        ArrayList<String> descr2 = new ArrayList<>();
        descr1.add("New Extensions");
        descr1.add("Better Facilities");
        HomeNewsModel h1 = new HomeNewsModel("STATE OF THE ART FACILITIES IN PGH 100",descr2);
        vouchersList.add(h1);

        ArrayList<String> descr3 = new ArrayList<>();
        descr1.add("New Extensions");
        descr1.add("Better Facilities");
        HomeNewsModel h2 = new HomeNewsModel("PSYCH WARD DOCTORS FLUES TO SPAIN FOR THE 2020 INTERNATIONAL MEET",descr2);
        vouchersList.add(h2);

        socialServicesAdapter = new HomeAdapter(getContext(),vouchersList);
        servicesRV.setAdapter(socialServicesAdapter);
        servicesRV.setLayoutManager(new LinearLayoutManager(getContext()));


        return myFragment;
    }

}
