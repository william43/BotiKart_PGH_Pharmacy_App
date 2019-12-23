package com.example.pgh_pharmacy_app.activity.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.MainActivity;
import com.example.pgh_pharmacy_app.adapter.shoppingFragment.loginSplashTutorials.IntroViewPagerAdapter;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.ScreenItem;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginSplashTutorialActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private UserModel user;
    IntroViewPagerAdapter introViewPagerAdapter ;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim ;
    TextView tvSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // when this activity is about to be launch we need to check if its openened before or not

        /*
        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class );
            startActivity(mainActivity);
            finish();


        }
        */

        setContentView(R.layout.activity_login_splash_tutorial);

        // hide the action bar


        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // fill list screen

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Welcome To BotiKart","Welcome to BotiKart, abot-kamay ang ating pambansang botika. Your digitalized Step by Step Process of the Pharmacy at PGH",R.drawable.login_splash));
        mList.add(new ScreenItem("Primary Function","The BotiKart App's Primary Function is to automate the process of buying and getting your inquiry about certain prescriptions. The current screen is split into 4 Instances",R.drawable.tutorials_screen));
        mList.add(new ScreenItem("Tutorials Fragment","The first Bottom Navigation represented by Tutorials is the page for beginners and would like to re learn the main functionalies of the application",R.drawable.img3));
        mList.add(new ScreenItem("Shopping Fragment","The second Bottom Navigation represented by Shopping Cart is the start of the buying process, where the list of available drugs. The said functionality is split into two",R.drawable.shopping_screen));
        mList.add(new ScreenItem("List of Available Drugs","The first one is the manual means of input for the drugs, you either search or scan thru the scrollable panel and select the drug that you were looking for. If the said drug is not present, then it is not apart of the PGH's list of drugs",R.drawable.shopping_screen));
        mList.add(new ScreenItem("Upload your Receipt","The second one is uploading a copy of your receipt. This process involves an admin user who would verify the said receipt and provide you a notification whether it was approved or not",R.drawable.upload_receipt_shopping_screen));
        mList.add(new ScreenItem("Home Fragment","The third Bottom Navigation represented by home is the place where you would get news about PGH",R.drawable.home_screen));
        mList.add(new ScreenItem("Social Services Fragment","The last Bottom Navigation represented by Social Services is the place where the list of discounts and social services are present that PGH caters to",R.drawable.services_screen ));

        // setup viewpager
        screenPager =findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        // next button click Listner

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == mList.size()-1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();


                }



            }
        });

        // tablayout add change listener


        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1) {

                    loaddLastScreen();

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        // Get Started button click listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                if(user.getType().equals("patient")){

                    mainActivity.putExtra("user", (PatientModel)user);
                }
                else{

                    mainActivity.putExtra("user", (OneTimeUsersModel)user);
                }
                mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
               // savePrefsData();
                finish();



            }
        });

        // skip button click listener

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });



    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;



    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);



    }
}