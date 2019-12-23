package com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esafirm.imagepicker.model.Image;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.OverTheCounterAdapter;
import com.example.pgh_pharmacy_app.dialogs.shopping.PrescribedShoppingCartDialog;
import com.example.pgh_pharmacy_app.dialogs.shopping.ShoppingCartDialog;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;

import java.util.ArrayList;

public class OverTheCounterFragment extends Fragment {

    View myFragment;
    private Bundle bundle;
    private ArrayList<MedicineModel> stringlist = new ArrayList<>();
    private ArrayList<MedicineModel> Originalstringlist = new ArrayList<>();
    private RecyclerView overtheCounterRV;
    private OverTheCounterAdapter overTheCounterAdapter;

    ProgressDialog dialog;

    PrescribedShoppingCartDialog prescribedShoppingCartDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.fragment_shopping_over_the_counter, container, false);


        bundle = this.getArguments();

        overtheCounterRV = myFragment.findViewById(R.id.overthecounterRV);

        overTheCounterAdapter = new OverTheCounterAdapter(getActivity(), stringlist, new OverTheCounterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MedicineModel item) {
                if(item instanceof PrescribedMedicineModel){
                    prescribedShoppingCartDialog = new PrescribedShoppingCartDialog(item);
                    prescribedShoppingCartDialog.show(getFragmentManager(), "sample");
                }
                else {
                    Toast.makeText(getActivity(), "Item + " + item.getGenericName(), Toast.LENGTH_LONG).show();
                    ShoppingCartDialog shoppingCartDialog = new ShoppingCartDialog(item);
                    shoppingCartDialog.show(getFragmentManager(), "sample");
                }
            }
        });
        overtheCounterRV.setAdapter(overTheCounterAdapter);
        overtheCounterRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading Medicines");
        dialog.show();


        return myFragment;
    }

    public void onSearchBar(CharSequence s){


        if(s != ""){
            stringlist.clear();
            for(int i = 0; i < Originalstringlist.size(); i++){
                Log.d("OVERTHESEARCH",""+stringlist.toString());
                if (Originalstringlist.get(i).getGenericName().contains(s)){
                    stringlist.add(Originalstringlist.get(i));
                }
            }
            Log.d("OVERTHECOUNTERCreERROR",""+stringlist.toString());
        }else{
            stringlist.clear();
            for(int i = 0; i < Originalstringlist.size(); i++){
                MedicineModel st = Originalstringlist.get(i);
                stringlist.add(Originalstringlist.get(i));
            }
        }

        overTheCounterAdapter.notifyDataSetChanged();
    }

    public void upddateAdapter(ArrayList<MedicineModel> m){
        Log.d("OVERTHECOUNTERCre", "beforebefore adapter: ." +m.size());
        //stringlist.clear();
        Log.d("OVERTHECOUNTERCre", "beforeupdate adapter: ." +m.size());

        stringlist.clear();
        Originalstringlist.clear();

        for(int i = 0; i < m.size(); i++){
            MedicineModel model = m.get(i);
            stringlist.add(model);
        }

        for(int j = 0; j < stringlist.size(); j++) {
            MedicineModel s = stringlist.get(j);
            Originalstringlist.add(s);
        }

        Log.d("OVERTHECOUNTERCre", "upddateAdapter: ." +stringlist.size());

        overTheCounterAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    public void imageUpload(Image image, Uri data){
        prescribedShoppingCartDialog.attachReceiptImage(image, data);
    }

}
