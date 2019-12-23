package com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.Login.LoginSplashTutorialActivity;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.example.pgh_pharmacy_app.model.Vouchers;

public class TutorialsFragment extends Fragment {

    private Bundle bundle;
    private UserModel user;

    View myFragment;

    Button tutorials;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.fragment_main_tutorials, container, false);

        bundle = getArguments();
        if(bundle != null) {
            user = bundle.getParcelable("user");
        }


        tutorials = myFragment.findViewById(R.id.tutorials);
        tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginSplashTutorialActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);

            }
        });

        return myFragment;
    }
}
