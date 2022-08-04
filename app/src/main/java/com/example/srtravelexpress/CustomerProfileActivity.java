package com.example.srtravelexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfileActivity extends AppCompatActivity {

    //declaration
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FirebaseUser firebaseUser;
    private String fullname, email, phone, gender;
    private EditText Cname, Cemail, Cphone, Cgender;
    private TextView navUsername, navUseremail;
    private FirebaseAuth authProfile;
    private Button updatep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        Cname = findViewById(R.id.Customer_fullname);
        Cemail = findViewById(R.id.Customer_email);
        Cphone = findViewById(R.id.Customer_phone);
        Cgender = findViewById(R.id.Customer_gender);
        updatep = findViewById(R.id.Update_profile);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(CustomerProfileActivity.this, "Something went wrong! User's details are not available at this moment", Toast.LENGTH_SHORT).show();
        } else {
            showUserProfile(firebaseUser);
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_view = (NavigationView) findViewById(R.id.navmenu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle("           Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updatenavHeader();

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_home:

                        Intent h = new Intent(CustomerProfileActivity.this, CustomerBookingActivity.class);
                        h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(h);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_profile:
                        Intent p = new Intent(CustomerProfileActivity.this, CustomerProfileActivity.class);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(p);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_Ticket_Information:
                        Intent t = new Intent(CustomerProfileActivity.this, CustomerTicketDetailsActivity.class);
                        t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(t);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_Ticket_Cancel:
                        Intent ct = new Intent(CustomerProfileActivity.this, CustomerTicketCancelActivity.class);
                        ct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ct);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_help:
                        Intent he = new Intent(CustomerProfileActivity.this, CustomerHelpActivity.class);
                        he.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(he);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_customer_care:
                        Intent cc = new Intent(CustomerProfileActivity.this, CustomerCareActivity.class);
                        cc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cc);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_logout:
                        Intent lg = new Intent(CustomerProfileActivity.this, CustomerLoginActivity.class);
                        lg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(lg);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        authProfile.signOut();
                        break;
                }


                return true;
            }
        });

        //update button

        Cname.setFocusable(false);
        Cemail.setFocusable(false);
        Cphone.setFocusable(false);
        Cgender.setFocusable(false);
        updatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cname.setFocusableInTouchMode(true);
                Cphone.setFocusableInTouchMode(true);
                Cgender.setFocusableInTouchMode(true);
            }
        });

    }

    //fetch data from db
    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    fullname = readUserDetails.Fullname;
                    email = firebaseUser.getEmail();
                    phone = readUserDetails.Phone;
                    gender = readUserDetails.Gender;

                    Cname.setText(fullname);
                    Cemail.setText(email);
                    Cphone.setText(phone);
                    Cgender.setText(gender);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomerProfileActivity.this, "Something went wrong! User's details are not available at this moment", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //update nave header
    public void updatenavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navmenu);
        View headerView = navigationView.getHeaderView(0);

        navUseremail = headerView.findViewById(R.id.nav_User_email);
        navUsername = headerView.findViewById(R.id.nav_User_name);
        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    fullname = readUserDetails.Fullname;
                    email = firebaseUser.getEmail();


                    navUsername.setText(fullname);
                    navUseremail.setText(email);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                //Toast.makeText(CustomerProfileActivity.this, "Something went wrong! User's details are not available at this moment", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}