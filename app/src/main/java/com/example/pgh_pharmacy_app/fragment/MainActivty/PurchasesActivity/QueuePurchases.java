package com.example.pgh_pharmacy_app.fragment.MainActivty.PurchasesActivity;

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
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.Purchases.ProcessingAdapter;
import com.example.pgh_pharmacy_app.model.InProcessModel;
import com.example.pgh_pharmacy_app.model.Order;
import com.example.pgh_pharmacy_app.model.QueueModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class QueuePurchases extends Fragment {


    View myFragment;

    private RecyclerView recyclerView;
    private List<Order> mobileOSes;
    private ProcessingAdapter adapter;
    private ArrayList<ShoppingCartModel> fullPurchaseList;
    private TextView tv;
    private UserModel user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_purchases_queue, container,false);

        Bundle bundle = getArguments();

        user = bundle.getParcelable("user");
        fullPurchaseList = bundle.getParcelableArrayList("purchases");

        recyclerView = (RecyclerView) myFragment.findViewById(R.id.queue_RV);
        tv = (TextView) myFragment.findViewById(R.id.queue_TV);
        mobileOSes = new ArrayList<>();

        setData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProcessingAdapter(getActivity(), mobileOSes);
        recyclerView.setAdapter(adapter);

        return myFragment;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }



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
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();
        /*First For Loop, get the Receipt Keys*/
        ArrayList<String> receiptKey = new ArrayList<>();
        for(int i = 0; i < fullPurchaseList.size(); i++){
            if(fullPurchaseList.get(i) instanceof QueueModel){
                if(!receiptKey.contains(((QueueModel)fullPurchaseList.get(i)).getQueueID())){
                    Log.d("PurchasesID", "" + ((QueueModel)fullPurchaseList.get(i)).getQueueID());
                    receiptKey.add(((QueueModel)fullPurchaseList.get(i)).getQueueID());
                }
            }
        }

        for(int i = 0; i< receiptKey.size(); i++){
            ArrayList<ShoppingCartModel> purchases = new ArrayList<>();
            for(int j = 0; j < fullPurchaseList.size(); j++){
                if(fullPurchaseList.get(j) instanceof QueueModel){
                    if(receiptKey.get(i).equals(((QueueModel) fullPurchaseList.get(j)).getQueueID())){
                        purchases.add(fullPurchaseList.get(j));
                    }
                }
            }
            mobileOSes.add(new Order(receiptKey.get(i), purchases));
        }

        if(mobileOSes.size() == 0)
            tv.setText("No Queued Purchases");
        else
            tv.setVisibility(View.INVISIBLE);

        dialog.dismiss();


    }

}
