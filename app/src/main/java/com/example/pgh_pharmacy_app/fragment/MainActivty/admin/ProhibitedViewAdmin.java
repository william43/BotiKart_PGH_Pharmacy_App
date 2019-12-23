package com.example.pgh_pharmacy_app.fragment.MainActivty.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pgh_pharmacy_app.R;

public class ProhibitedViewAdmin extends Fragment {

    View myFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_admin_prohibited, container,false);

        return myFragment;
    }

}
