package com.example.pgh_pharmacy_app.fragment.MainActivty.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.Admin.AdminAdapter;
import com.example.pgh_pharmacy_app.model.AdminProcessModel;
import com.example.pgh_pharmacy_app.model.InProcessModel;
import com.example.pgh_pharmacy_app.model.Order;
import com.example.pgh_pharmacy_app.model.PendingAdminProcessModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.ToPayAdminProcessModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OverTheCounterViewAdmin extends Fragment {

    View myFragment;

    private RecyclerView recyclerView;
    private List<Order> mobileOSes;
    private AdminAdapter adapter;
    //private ArrayList<ShoppingCartModel> fullPurchaseList;
    private ArrayList<AdminProcessModel> adminProcess = new ArrayList<>();
    private TextView tv;
    private UserModel user;

    DatabaseReference receiptDatRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_admin_over_the_counter, container,false);

        Bundle bundle = getArguments();

        user = bundle.getParcelable("user");
        //fullPurchaseList = bundle.getParcelableArrayList("purchases");
        recyclerView = (RecyclerView) myFragment.findViewById(R.id.admin_processed_RV);
        tv = (TextView) myFragment.findViewById(R.id.admin_processed_TV);

        mobileOSes = new ArrayList<>();

        setData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdminAdapter(getActivity(), adminProcess);
        recyclerView.setAdapter(adapter);

        return myFragment;
    }

    /*
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    */



    private void setData() {
        /*
        ArrayList<ShoppingCartModel> first = new ArrayList<>();
        first.add(new ShoppingCartModel("",2,new MedicineModel("behhh","","",20),true,""));
        first.add(new ShoppingCartModel("",2,new MedicineModel("behhh","","",20),true,""));
        first.add(new ShoppingCartModel("",2,new MedicineModel("behhh","","",20),true,""));


        ArrayList<ShoppingCartModel> second = new ArrayList<>();
        second.add(new ShoppingCartModel("",2,new MedicineModel("ben","","",20),true,""));
        second.add(new ShoppingCartModel("",2,new MedicineModel("ben","","",20),true,""));
        second.add(new ShoppingCartModel("",2,new MedicineModel("ben","","",20),true,""));
        mobileOSes.add(new Order("First",first));
        mobileOSes.add(new Order("Second", second));
         */
        receiptDatRef = FirebaseDatabase.getInstance().getReference().child("admin");
        receiptDatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adminProcess.clear();
                Log.d("PURCHASE", "purchased clear");
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String type = dataSnapshot1.child("type").getValue(String.class);
                    if(type == null){
                        AdminProcessModel a = dataSnapshot1.getValue(AdminProcessModel.class);
                        adminProcess.add(a);
                    }
                    else if(type.equals("pending")){
                       AdminProcessModel a = dataSnapshot1.getValue(PendingAdminProcessModel.class);
                       adminProcess.add(a);
                    }
                    else if(type.equals("counter")){
                        AdminProcessModel a = dataSnapshot1.getValue(ToPayAdminProcessModel.class);
                        adminProcess.add(a);
                    }
                }
                Log.d("PURCHASES", adminProcess.size() + " hehehe");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();
        /*First For Loop, get the Receipt Keys*/



        dialog.dismiss();


    }

    public void onLoad(ArrayList<AdminProcessModel> a){

        /*
        mobileOSes.clear();
        for(int i = 0; i <a.size(); i++){
            ArrayList<ShoppingCartModel> purchases = new ArrayList<>();
            for(int j = 0; j < fullPurchaseList.size(); j++) {
                //Log.d("FULLPURCHASES", fullPurchaseList.size() + " hehehe");
                if (fullPurchaseList.get(j) instanceof InProcessModel) {
                    Log.d("FULLPURCHASES", fullPurchaseList.size() + " hehehe");
                    if (a.get(i).getId().equals(((InProcessModel) fullPurchaseList.get(j)).getInProcessID())) {
                        purchases.add(fullPurchaseList.get(i));
                    }
                }
            }
            mobileOSes.add(new Order(a.get(i).getId(), purchases));

        }

         */

        if(mobileOSes.size() == 0)
            tv.setText("No Pending Purchases");
        else
            tv.setVisibility(View.INVISIBLE);


    }

}
