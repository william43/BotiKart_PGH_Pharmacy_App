package com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.pgh_pharmacy_app.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProhibitedDrugsFragment extends Fragment {

    private Bundle bundle;
    private ArrayList<String> stringlist = new ArrayList<>();
    View myFragment;
    TextView cameraTxT;
    ImageView cameraIMG;
    ImageView receiptIMG;


    private Uri mImageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_shopping_prohibited_drugs, container, false);

        bundle = this.getArguments();

        if(bundle != null){
            stringlist = bundle.getStringArrayList("strings");
            Log.d("PROHIBITED_DRUGSCre",""+stringlist.toString());

        }


        cameraTxT = myFragment.findViewById(R.id.prohibited_drugs_send_text);
        cameraIMG = myFragment.findViewById(R.id.prohibited_drugs_camera);
        cameraIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(getActivity()).start();

            }
        });
        receiptIMG = myFragment.findViewById(R.id.receipt_prohibited_drugs);




        return myFragment;


    }

    public void loadPicture(Image image){
        receiptIMG.setVisibility(View.VISIBLE);
        Glide.with(getActivity()).load(image.getPath()).into(receiptIMG);
        cameraIMG.setVisibility(View.INVISIBLE);
        cameraTxT.setVisibility(View.INVISIBLE);

    }


}
