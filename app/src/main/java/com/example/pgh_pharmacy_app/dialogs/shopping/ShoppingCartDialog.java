package com.example.pgh_pharmacy_app.dialogs.shopping;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pgh_pharmacy_app.HorizontalNumberPicker;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShoppingCartDialog extends BottomSheetDialogFragment{
    private BottomSheetListener mListener;

    HorizontalNumberPicker number;
    Button addtoCart;
    Button proceedtoCheckout;
    MedicineModel item;

    ImageView img;
    TextView medicineDetails;

    public ShoppingCartDialog(MedicineModel item){
        this.item = item;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_shopping_cart_item, container, false);

        number = v.findViewById(R.id.numberPicker);
        addtoCart = v.findViewById(R.id.addtoCart);
        proceedtoCheckout = v.findViewById(R.id.proceedtoCart);

        img = v.findViewById(R.id.shopping_cart_item_img);
        if(item.getGenericName().contains("Dextrose")){
            img.setImageResource(R.drawable.dextrose_icon);
        }
        else if(item.getGenericName().startsWith("Acarbose")){
            img.setImageResource(R.drawable.acarbose_icon);
        }
        else if(item.getGenericName().startsWith("Acetazolamide")){
            img.setImageResource(R.drawable.acetazolamide_icon);
        }
        else if(item.getGenericName().startsWith("Ascorbic")){
            img.setImageResource(R.drawable.ascorbic_icon);
        }
        else if(item.getGenericName().startsWith("Betahistine")){
            img.setImageResource(R.drawable.betahistine_icon);
        }
        else if(item.getGenericName().startsWith("Betamethasone")){
            //Toast.makeText(context, "Went to Betame", Toast.LENGTH_LONG).show();
            img.setImageResource(R.drawable.betamethasone_icon);
        }
        else if(item.getGenericName().startsWith("Calcium")){
            img.setImageResource(R.drawable.calcium_icon);
        }
        else if(item.getGenericName().startsWith("Cilostazol")){
            img.setImageResource(R.drawable.cilostazol_icon);
        }
        else if(item.getGenericName().startsWith("Dextromethorpahn")){
            img.setImageResource(R.drawable.dextromethorphan_icon);
        }
        else if(item.getGenericName().startsWith("Paracetamol")){
            //Toast.makeText(context, "Went to Para", Toast.LENGTH_LONG).show();
            img.setImageResource(R.drawable.paracetamol_icon);
        }
        else{
            img.setImageResource(R.drawable.dextrose_icon);
        }
        medicineDetails = v.findViewById(R.id.medicine_details);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("cart", item, number.getValue());
                dismiss();
            }
        });

        proceedtoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("checkout", item, number.getValue());
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        medicineDetails.setText(item.getGenericName());

    }

    public interface BottomSheetListener {
        void onButtonClicked(String text, MedicineModel m, int quantity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
