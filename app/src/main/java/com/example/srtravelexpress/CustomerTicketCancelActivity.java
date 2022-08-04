package com.example.srtravelexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerTicketCancelActivity extends AppCompatActivity {

    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    TextView navUsername, navUseremail;
    Button cancel;
    String fullname, email,phone,gender;
    String transactionid,busid,from,to,date,cost,seatamount,time;
    TextView busids,times,froms,tos,seatamounts,transactionids,seatss,costs,usernames,emails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_ticket_cancel);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        busids=findViewById(R.id.busid1);
        times=findViewById(R.id.time1);
        froms=findViewById(R.id.from1);
        tos=findViewById(R.id.to1);
        seatamounts=findViewById(R.id.seatamount1);
        transactionids=findViewById(R.id.transactionid1);
        seatss=findViewById(R.id.seats1);
        costs=findViewById(R.id.cost1);
        //Helper help=new Helper();


        usernames=findViewById(R.id.username1);
        emails=findViewById(R.id.email1);



        cancel=findViewById(R.id.cancelBooking);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_view =(NavigationView)findViewById(R.id.navmenu);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle("        Booking Cancel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("TicketDetails");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Tickets readUserDetails = snapshot.getValue(Tickets.class);
                if (readUserDetails != null) {
                    //busids,times,froms,tos,seatamounts,transactionids,seatss,costs,usernames,emails;

                    busids.setText(Add("Bus ID: ",readUserDetails.getBusid()));
                    times.setText(Add("Time: ",readUserDetails.getTime()));
                    froms.setText(Add("Journey From: ",readUserDetails.getFrom()));
                    tos.setText(Add("Journey to: ",readUserDetails.getTo()));
                    seatamounts.setText(Add("Total Seats: ",readUserDetails.getSeatamount()));
                    transactionids.setText(Add("Transaction Id: ",readUserDetails.getTransactionid()));
                    String temp="";
                    for(int i=0;i<readUserDetails.getSeats().size();i++)
                    {
                        temp=Add(temp," ",readUserDetails.getSeats().get(i));
                    }
                    seatss.setText(Add("Seats: ",temp));
                    costs.setText(Add("Costs: ",readUserDetails.getCost()));
                    usernames.setText(Add("Username: ",readUserDetails.getUsername()));
                    emails.setText(Add("Email: ",readUserDetails.getUsermail()));

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomerTicketCancelActivity.this, "Something went wrong! User's details are not available at this moment", Toast.LENGTH_SHORT).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String userID = firebaseUser.getUid();

                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("TicketDetails");
                referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Tickets readUserDetails = snapshot.getValue(Tickets.class);
                        if (readUserDetails != null) {
                            String userID = firebaseUser.getUid();

                            DatabaseReference DBref2;
                            DBref2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/");

                            busids.setText("No Booking Data......");
                            times.setText("No Booking Data......");
                            froms.setText("No Booking Data......");
                            tos.setText("No Booking Data......");
                            seatamounts.setText("No Booking Data......");
                            transactionids.setText("No Booking Data......");
                            String temp="";
                            for(int i=0;i<readUserDetails.getSeats().size();i++)
                            {
                                temp=Add(temp," ",readUserDetails.getSeats().get(i));
                            }
                            seatss.setText("No Booking Data......");
                            costs.setText("No Booking Data......");
                            usernames.setText("No Booking Data......");
                            emails.setText("No Booking Data......");
                            DBref2.child("TicketDetails").child(userID).removeValue();
                            Toast.makeText(CustomerTicketCancelActivity.this, "Your Booking is Cancelled.Please wait for three working days for your refund", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CustomerTicketCancelActivity.this,CustomerBookingActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {

                            Toast.makeText(CustomerTicketCancelActivity.this, "There is no booking for you", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(CustomerTicketCancelActivity.this, "Something went wrong! User's details are not available at this moment", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


        updatenavHeader();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.menu_home:

                        Intent h = new Intent(CustomerTicketCancelActivity.this, CustomerBookingActivity.class);
                        h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(h);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_profile:
                        Intent p = new Intent(CustomerTicketCancelActivity.this, CustomerProfileActivity.class);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(p);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_Ticket_Information:
                        Intent t = new Intent(CustomerTicketCancelActivity.this, CustomerTicketDetailsActivity.class);
                        t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(t);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_Ticket_Cancel:
                        Intent ct = new Intent(CustomerTicketCancelActivity.this, CustomerTicketCancelActivity.class);
                        ct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ct);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_help:
                        Intent he = new Intent(CustomerTicketCancelActivity.this, CustomerHelpActivity.class);
                        he.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(he);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_customer_care:
                        Intent cc = new Intent(CustomerTicketCancelActivity.this, CustomerCareActivity.class);
                        cc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cc);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_logout:
                        Intent lg = new Intent(CustomerTicketCancelActivity.this, CustomerLoginActivity.class);
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
    }
    public String Add(String a,String b)
    {
        String bb=a.concat(b);
        return bb;
    }

    public String Add(String a,String b,String c)
    {
        String bb=a.concat(b.concat(c));
        return bb;
    }
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