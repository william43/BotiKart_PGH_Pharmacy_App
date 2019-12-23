package com.example.pgh_pharmacy_app.fragment.MainActivty.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.Purchases.PurchasesActivity;
import com.example.pgh_pharmacy_app.model.UserModel;

public class ProfileFragment extends Fragment {


    ImageView profilePicIMG;
    TextView profileUsername;
    RelativeLayout editProfile;
    RelativeLayout viewPurchasesHistory;

    ImageView processingsIcon;
    ImageView counterIcon;
    ImageView completeIcon;
    ImageView pendingIcon;
    ImageView queueIcon;

    Bundle bundle;
    UserModel user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragment = inflater.inflate(R.layout.fragment_drawer_profile, container, false);


        bundle = getArguments();
        user = bundle.getParcelable("user");

        profilePicIMG = myFragment.findViewById(R.id.profile_profile_picture);

        profileUsername = myFragment.findViewById(R.id.profile_username);
        profileUsername.setText(user.getUsername());
        editProfile = myFragment.findViewById(R.id.profile_edit_profile_button);
        viewPurchasesHistory = myFragment.findViewById(R.id.profile_view_purchase_history);
        viewPurchasesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PurchasesActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        processingsIcon = myFragment.findViewById(R.id.profile_processing_icon);
        counterIcon = myFragment.findViewById(R.id.profile_counter_icon);
        completeIcon = myFragment.findViewById(R.id.profile_complete_icon);
        pendingIcon = myFragment.findViewById(R.id.profile_pending_icon);
        queueIcon = myFragment.findViewById(R.id.profile_queue_icon);


        return myFragment;
    }
}
