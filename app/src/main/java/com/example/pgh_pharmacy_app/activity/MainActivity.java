package com.example.pgh_pharmacy_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.pgh_pharmacy_app.CounterCode;
import com.example.pgh_pharmacy_app.HorizontalNumberPicker;
import com.example.pgh_pharmacy_app.PendedCode;
import com.example.pgh_pharmacy_app.R;
import com.example.pgh_pharmacy_app.activity.Login.LoginSplashActivity;
import com.example.pgh_pharmacy_app.activity.ShoppingCart.CheckShoppingCartActivity;
import com.example.pgh_pharmacy_app.dialogs.shopping.PrescribedShoppingCartDialog;
import com.example.pgh_pharmacy_app.dialogs.shopping.ShoppingCartDialog;
import com.example.pgh_pharmacy_app.fragment.MainActivty.DrawerLayout.AboutFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.DrawerLayout.NotificationsFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.DrawerLayout.ProfileFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.DrawerLayout.SettingsFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity.HomeFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity.ServicesFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity.ShoppingFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.MainActivity.TutorialsFragment;
import com.example.pgh_pharmacy_app.fragment.MainActivty.ShoppingFragment.OverTheCounterFragment;
import com.example.pgh_pharmacy_app.model.AdminModel;
import com.example.pgh_pharmacy_app.model.MedicineModel;
import com.example.pgh_pharmacy_app.model.OneTimeUsersModel;
import com.example.pgh_pharmacy_app.model.PatientModel;
import com.example.pgh_pharmacy_app.model.PrescribedMedicineModel;
import com.example.pgh_pharmacy_app.model.ShoppingCartModel;
import com.example.pgh_pharmacy_app.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ShoppingCartDialog.BottomSheetListener, PrescribedShoppingCartDialog.PrescribedBottomSheetListener {

    private DrawerLayout drawerLayout;
    private EditText searchET;
    private BottomNavigationView bottomNav;
    private UserModel user;
    private ImageView shoppingCart;
    private TextView textHeader;
    private LinearLayout searchBar;
    public ArrayList<ShoppingCartModel> shoppingCartList = new ArrayList<>();
    public DatabaseReference shoppingcartDatabaseRef;
    public DatabaseReference randomRef;
    private Toolbar toolbar;
    public String randomnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent = getIntent();
        user = intent.getExtras().getParcelable("user");


        toolbar = findViewById(R.id.toolbar);
        searchBar = findViewById(R.id.search_bar_over_the_counter);
        searchBar.setVisibility(View.INVISIBLE);
        textHeader = findViewById(R.id.main_activity_header_name);
        textHeader.setText("Tutorials Page");
        shoppingCart = findViewById(R.id.shoppingcartIV);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shoppingIntent = new Intent(getApplicationContext(), CheckShoppingCartActivity.class);
                if(user.getType().equals("admin")) {
                    shoppingIntent.putExtra("user", (AdminModel)user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Admin",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user.getType().equals("patient")){
                    shoppingIntent.putExtra("user", (PatientModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Patient",
                            Toast.LENGTH_SHORT).show();
                }
                else if(user.getType().equals("onetime")){
                    shoppingIntent.putExtra("user", (OneTimeUsersModel) user);
                    shoppingIntent.putParcelableArrayListExtra("shoppinglist", shoppingCartList);
                    Toast.makeText(getApplicationContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(shoppingIntent);
            }
        });
        searchET = findViewById(R.id.search_bar_main);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShoppingFragment fragment = (ShoppingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if(fragment != null){

                    fragment.onTextChange(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //I added this if statement to keep the selected fragment when rotating the device

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TutorialsFragment()).commit();
        }

        drawerLayout = findViewById(R.id.main_drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);


        randomRef = FirebaseDatabase.getInstance().getReference().child("random").child(user.getId());
        randomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                randomnum = String.valueOf(dataSnapshot.getValue(String.class));
                String[] temp = randomnum.split(" ");


                if (temp[0].equalsIgnoreCase("Pro")) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New Notification")
                            .setContentText("Notifications")
                            .setAutoCancel(true);
                    Intent intent1 = new Intent(MainActivity.this, CounterCode.class);
                    PendingIntent intent2 = PendingIntent.getActivity(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(intent2);

                    NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
// notificationID allows you to update the notification later on.mNotificationManager.notify(notificationID, mBuilder.build());
                    Toast.makeText(getApplicationContext(), temp[0], Toast.LENGTH_LONG).show();
                }
                else if(temp[0].equalsIgnoreCase("Pen")){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New Notification")
                            .setContentText("Notifications")
                            .setAutoCancel(true);
                    Intent intent1 = new Intent(MainActivity.this, PendedCode.class);
                    PendingIntent intent2 = PendingIntent.getActivity(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(intent2);

                    NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());
// notificationID allows you to update the notification later on.mNotificationManager.notify(notificationID, mBuilder.build());
                    Toast.makeText(getApplicationContext(), temp[0], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(getFragmentManager().getBackStackEntryCount() == 0) {
            Intent intent = new Intent(getApplicationContext(), LoginSplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
                getFragmentManager().popBackStack();
                updateTitle();
        }

    }


    public void updateTitle(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        bottomNav.setSelectedItemId(R.id.nav_home);
        if(f instanceof HomeFragment){
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
        else if(f instanceof ShoppingFragment){
            bottomNav.setSelectedItemId(R.id.nav_medicines);
        }
        else if(f instanceof ServicesFragment){
            bottomNav.setSelectedItemId(R.id.nav_services);
        }
        else if(f instanceof TutorialsFragment){
            bottomNav.setSelectedItemId(R.id.nav_tutorials);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        textHeader.setText("Home Page");
                        searchBar.setVisibility(View.INVISIBLE);
                        textHeader.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_medicines:
                        selectedFragment = new ShoppingFragment();
                        textHeader.setVisibility(View.INVISIBLE);
                        searchBar.setVisibility(View.VISIBLE);
                        searchET.setHint("Search Medicines");
                        break;
                    case R.id.nav_services:
                        selectedFragment = new ServicesFragment();
                        searchBar.setVisibility(View.VISIBLE);
                        textHeader.setVisibility(View.INVISIBLE);
                        searchET.setHint("Search Social Services");
                        break;
                    case R.id.nav_tutorials:
                        selectedFragment = new TutorialsFragment();
                        textHeader.setText("Tutorials Page");
                        textHeader.setVisibility(View.VISIBLE);
                        searchBar.setVisibility(View.INVISIBLE);
                        break;
                }

                selectedFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",user);
        switch (menuItem.getItemId()){

            case R.id.drawer_profile:
                fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                textHeader.setText("Profile Page");
                textHeader.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);
                break;
            case R.id.drawer_profile_pic:
                break;
            case R.id.drawer_about:
                fragment = new AboutFragment();
                fragment.setArguments(bundle);
                textHeader.setText("About Us Page");
                textHeader.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);
                break;
            case R.id.drawer_notifications:
                fragment = new NotificationsFragment();
                fragment.setArguments(bundle);
                textHeader.setText("Notifications Page");
                textHeader.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);
                break;
            case R.id.drawer_settings:
                fragment = new SettingsFragment();
                fragment.setArguments(bundle);
                textHeader.setText("Settings Page");
                textHeader.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);
                break;

        }
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                fragment).commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onButtonClicked(String text, MedicineModel m, int quantity) {
        if(user instanceof PatientModel) {
            //Toast.makeText(this, "Is Patient", Toast.LENGTH_SHORT).show();
            shoppingcartDatabaseRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart");

            // New Key per Product
            String keyProduct = shoppingcartDatabaseRef.child(user.getId()).push().getKey();
            ShoppingCartModel s = new ShoppingCartModel(keyProduct, quantity, m, true, "cart");
            shoppingcartDatabaseRef.child(user.getId()).child(keyProduct).setValue(s);

            if (text.equals("checkout")) {
                Intent shoppingIntent = new Intent(getApplicationContext(), CheckShoppingCartActivity.class);
                if (user.getType().equals("admin")) {
                    shoppingIntent.putExtra("user", (AdminModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Admin",
                            Toast.LENGTH_SHORT).show();
                } else if (user.getType().equals("patient")) {
                    shoppingIntent.putExtra("user", (PatientModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Patient",
                            Toast.LENGTH_SHORT).show();
                } else if (user.getType().equals("onetime")) {
                    shoppingIntent.putExtra("user", (OneTimeUsersModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(shoppingIntent);
            }
        }
        else{
            //Toast.makeText(this, "Is One Time", Toast.LENGTH_SHORT).show();
            ShoppingCartModel s = new ShoppingCartModel("00000", quantity, m, true, "cart");
            shoppingCartList.add(s);
        }

    }

    public void updateHeader(int i){
        if(i == 0){
            searchBar.setVisibility(View.INVISIBLE);
            textHeader.setText("Prohibited Drugs");
            textHeader.setVisibility(View.INVISIBLE);
        }
        else if(i == 1){
            searchBar.setVisibility(View.VISIBLE);
            searchET.setText("Search for Over The Counter Drugs");
            textHeader.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            /*
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);

             */
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            ShoppingFragment fragment = (ShoppingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            Uri uri = Uri.fromFile(new File(image.getPath()));
            Log.d("URI ", "hehe3 " +uri);
            if(fragment != null){

                fragment.onLoadPicture(image, uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onButtonPresClicked(String text, MedicineModel m, int quantity) {

        if(user instanceof PatientModel) {
            //Toast.makeText(this, "Is Patient", Toast.LENGTH_SHORT).show();
            shoppingcartDatabaseRef = FirebaseDatabase.getInstance().getReference().child("shoppingcart");

            // New Key per Product
            String keyProduct = shoppingcartDatabaseRef.child(user.getId()).push().getKey();
            ShoppingCartModel s = new ShoppingCartModel(keyProduct, quantity, m, true, "cart");
            shoppingcartDatabaseRef.child(user.getId()).child(keyProduct).setValue(s);

            if (text.equals("checkout")) {
                Intent shoppingIntent = new Intent(getApplicationContext(), CheckShoppingCartActivity.class);
                if (user.getType().equals("admin")) {
                    shoppingIntent.putExtra("user", (AdminModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Admin",
                            Toast.LENGTH_SHORT).show();
                } else if (user.getType().equals("patient")) {
                    shoppingIntent.putExtra("user", (PatientModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an Patient",
                            Toast.LENGTH_SHORT).show();
                } else if (user.getType().equals("onetime")) {
                    shoppingIntent.putExtra("user", (OneTimeUsersModel) user);
                    Toast.makeText(getApplicationContext(),
                            "Is an One Time User",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(shoppingIntent);
            }
        }
        else{
            //Toast.makeText(this, "Is One Time", Toast.LENGTH_SHORT).show();
            if(m instanceof PrescribedMedicineModel){
                ShoppingCartModel s = new ShoppingCartModel("00000", quantity, (PrescribedMedicineModel)m, true, "cart");
                shoppingCartList.add(s);
            }
            else {
                ShoppingCartModel s = new ShoppingCartModel("00000", quantity, m, true, "cart");
                shoppingCartList.add(s);
            }
        }
    }
}
