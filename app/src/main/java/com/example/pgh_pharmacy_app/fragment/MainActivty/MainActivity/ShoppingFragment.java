package com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.esafirm.imagepicker.model.Image;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.activity.ShoppingCart.CheckShoppingCartActivity;
import com.example.pgh_pharmacy_app.activity.ShoppingCart.WaitOrderActivity;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.SectionPagerAdapter;
import com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment.OverTheCounterFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment.ProhibitedDrugsFragment;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {

    private Bundle bundle;
    private UserModel user;
    View myFragment;

    ViewPager viewPager;
    TabLayout tabLayout;

    AppBarLayout appBar;

    ArrayList<MedicineModel> medicineList = new ArrayList<>();
    public DatabaseReference medDatabaseRef;


    Button checkoutBTN;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.fragment_main_shopping, container, false);

        bundle = getArguments();
        user = bundle.getParcelable("user");
        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        appBar = myFragment.findViewById(R.id.appBarLayout);
        appBar.setBackground(null);

        checkoutBTN = myFragment.findViewById(R.id.checkoutBTN);

        checkoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(viewPager.getCurrentItem() == 0) {
                    intent = new Intent(getContext(), CheckShoppingCartActivity.class);
                }
                else{
                    intent = new Intent(getContext(), WaitOrderActivity.class);
                }
                if(user.getType().equals("admin")) {
                    intent.putExtra("user", (AdminModel)user);
                    Toast.makeText(getContext(),
                            "Is an Admin",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user.getType().equals("patient")){
                    intent.putExtra("user", (PatientModel) user);
                    Toast.makeText(getContext(),
                            "Is an Patient",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user.getType().equals("patient")){
                    intent.putExtra("user", (OneTimeUsersModel) user);
                    Toast.makeText(getContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);

            }
        });

        medDatabaseRef = FirebaseDatabase.getInstance().getReference().child("medicine");


        Log.d("OVERTHECOUNTERCre","help "+ medDatabaseRef.getRef());




        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch(position){

                    case 0:
                        ((MainActivity)getActivity()).updateHeader(viewPager.getCurrentItem());
                        checkoutBTN.setText("Proceed to Checkout");
                        break;
                    case 1:
                        ((MainActivity)getActivity()).updateHeader(viewPager.getCurrentItem());
                        checkoutBTN.setText("Send Receipt");
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(final ViewPager viewPager){
        final SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());


        /*
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Janine");
        stringList.add("Judge");
        stringList.add("Yka");
        stringList.add("Jory");
        stringList.add("Kendrick");
        stringList.add("Nicole");
        stringList.add("Myles");
        stringList.add("Tapat");
        stringList.add("Hazel");
        stringList.add("Joshua");
        stringList.add("Baby");
        stringList.add("Baby Ethan");
        stringList.add("Ethan");
        */


        medDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                Log.d("OVERTHECOUNTERCre","help gaga"+ dataSnapshot.getKey());
                for(DataSnapshot postDataSnapshot: dataSnapshot.getChildren()){
                    String type = postDataSnapshot.child("type").getValue(String.class);
                    if(type.equalsIgnoreCase("generic")) {
                        MedicineModel medicineModel = postDataSnapshot.getValue(MedicineModel.class);
                        medicineList.add(medicineModel);
                    }
                    else {
                        MedicineModel medicineModel = postDataSnapshot.getValue(PrescribedMedicineModel.class);
                        medicineList.add(medicineModel);
                    }

                }
                Log.d("OVERTHECOUNTERCre", "firebase: ." +medicineList.size());
                onLoad(medicineList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("OVERTHECOUNTERCre","help gaga");

            }
        });


        OverTheCounterFragment over = new OverTheCounterFragment();
        ProhibitedDrugsFragment prohibited = new ProhibitedDrugsFragment();


        adapter.addFragment(over, "Available Drugs");
        adapter.addFragment(prohibited, "Picture of Receipt");

        viewPager.setAdapter(adapter);

    }

    public void onTextChange(CharSequence s){
        /*
        Fragment fragment = viewPager.getAdapter().getItemPosition();
        if(fragment instanceof OverTheCounterFragment)

         */

        if(viewPager.getCurrentItem() == 0){
            OverTheCounterFragment frag1 = (OverTheCounterFragment) viewPager
                    .getAdapter()
                    .instantiateItem(viewPager, viewPager.getCurrentItem());
            frag1.onSearchBar(s);
        }
        else if(viewPager.getCurrentItem() == 1){

        }

        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    public void onLoad(ArrayList<MedicineModel> m){
        Log.d("OVERTHECOUNTERCre", "firebaseafter: ." +m.size());

        if(viewPager.getCurrentItem() == 0){
            OverTheCounterFragment frag1 = (OverTheCounterFragment) viewPager
                    .getAdapter()
                    .instantiateItem(viewPager, viewPager.getCurrentItem());
            frag1.upddateAdapter(m);
        }
        else if(viewPager.getCurrentItem() == 1){

        }
    }

    public void onLoadPicture(Image image, Uri data){
        if(viewPager.getCurrentItem() == 1)
        {
            ProhibitedDrugsFragment frag1 = (ProhibitedDrugsFragment) viewPager
                    .getAdapter()
                    .instantiateItem(viewPager, viewPager.getCurrentItem());
            frag1.loadPicture(image);
        }
        else {
            OverTheCounterFragment frag2 = (OverTheCounterFragment) viewPager
                    .getAdapter()
                    .instantiateItem(viewPager, viewPager.getCurrentItem());
            frag2.imageUpload(image, data);
        }
    }

}
