package com.example.srtravelexpress;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomerBookingActivity extends AppCompatActivity {
    //declaration
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView navUsername, navUseremail;
    String fullname, email;
    String pos1, pos2;
    AutoCompleteTextView ac, ac2;
    AutoCompleteTextView CstartDes, CendDes;
    TextInputLayout cc1;
    TextInputLayout cc2;
    String ticketdate;
    String todaysdate;
    DatabaseReference DBref;
    TextView weluser;
    private Button CselectBus;
    private TextView CJDate;
    private DatePickerDialog piker;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private String[] startDes = new String[]{"Dhaka", "Sylhet", "Rajshahi", "Chittagoan", "Khulna", "Cox's Bazar", "Bandarban", "Mymensingh", "Rangpur"};
    private String[] endDes = new String[]{"Dhaka", "Sylhet", "Rajshahi", "Chittagoan", "Khulna", "Cox's Bazar", "Bandarban", "Mymensingh", "Rangpur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking);

        DBref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/");

        //current user
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_view = (NavigationView) findViewById(R.id.navmenu);
        cc1 = findViewById(R.id.textInputLayout1);
        cc2 = findViewById(R.id.textInputLayout2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //drawer and toggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setTitle("        Searching Buses");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updatenavHeader();

        //navigation view
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_home:

                        Intent h = new Intent(CustomerBookingActivity.this, CustomerBookingActivity.class);
                        h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(h);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_profile:
                        Intent p = new Intent(CustomerBookingActivity.this, CustomerProfileActivity.class);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(p);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_Ticket_Information:
                        Intent t = new Intent(CustomerBookingActivity.this, CustomerTicketDetailsActivity.class);
                        t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(t);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_Ticket_Cancel:
                        Intent ct = new Intent(CustomerBookingActivity.this, CustomerTicketCancelActivity.class);
                        ct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ct);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_help:
                        Intent he = new Intent(CustomerBookingActivity.this, CustomerHelpActivity.class);
                        he.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(he);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_customer_care:
                        Intent cc = new Intent(CustomerBookingActivity.this, CustomerCareActivity.class);
                        cc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cc);
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        break;
                    case R.id.menu_logout:
                        Intent lg = new Intent(CustomerBookingActivity.this, CustomerLoginActivity.class);
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


        CstartDes = findViewById(R.id.Customer_Start_Des);
        CendDes = findViewById(R.id.Customer_End_Des);
        CJDate = findViewById(R.id.Customer_date_piker);
        CselectBus = findViewById(R.id.Customer_Find_buses);
        weluser = findViewById(R.id.welome_user);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, startDes);

        CstartDes.setAdapter(adapter);

        ((AutoCompleteTextView) cc1.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                pos1 = adapter.getItem(position);
            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, endDes);


        CendDes.setAdapter(adapter1);
        ((AutoCompleteTextView) cc2.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                pos2 = adapter1.getItem(position);
            }
        });

        //date piker
        CJDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                piker = new DatePickerDialog(CustomerBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Year, int Month, int DayofMonth) {

                        CJDate.setText("Selected Date: " + DayofMonth + "/" + (Month + 1) + "/" + Year);
                        String aa = Integer.toString(Year);
                        String bb = Integer.toString(Month + 1);
                        String cc = Integer.toString(DayofMonth);
                        String space = " ";
                        if (Month + 1 < 10) bb = Add("0", bb);
                        if (DayofMonth < 10) cc = Add("0", cc);
                        ticketdate = Add(aa, Add(space, Add(bb, Add(space, cc))));

                    }
                }, year, month, day);
                //calendar.get(Calendar.Y, calendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
                piker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                piker.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 24 * 60 * 60 * 6));
                piker.show();
                long yourmilliseconds = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm");
                Date resultdate = new Date(yourmilliseconds);
                todaysdate = sdf.format(resultdate);


            }
        });

        //find buses
        CselectBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query query=DBref.child("BUSTWO").orderByChild("date").equalTo(ticketdate);
                //DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("BUSTWO");
                //query.addListenerForSingleValueEvent(new ValueEventListener()
                //String userID = firebaseUser.getUid();

                //DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("BUSTWO");
                //referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener()
                String X = CstartDes.getText().toString();
                String Y = CendDes.getText().toString();
                if (X.equals(Y)) {
                    Toast.makeText(CustomerBookingActivity.this, "Please select different city", Toast.LENGTH_SHORT).show();
                } else {

                    DBref = FirebaseDatabase.getInstance().getReference("BUSTWO");
                    String userID = firebaseUser.getUid();

                    Query query = DBref.orderByKey().equalTo(ticketdate);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // BusSeat bss=
                            if (snapshot.exists()) {

                                Intent intent22 = new Intent(CustomerBookingActivity.this, Buslist.class);
                                intent22.putExtra("ticketdate", ticketdate);
                                intent22.putExtra("todaysdate", todaysdate);

                                intent22.putExtra("from", pos1);
                                intent22.putExtra("to", pos2);
                                startActivity(intent22);


                                //Toast.makeText(CustomerBookingActivity.this,todaysdate, Toast.LENGTH_SHORT).show();


                            } else {

                                createDataWiseBusList();


                                Intent intent22 = new Intent(CustomerBookingActivity.this, Buslist.class);
                                intent22.putExtra("ticketdate", ticketdate);
                                intent22.putExtra("todaysdate", todaysdate);


                                intent22.putExtra("from", pos1);
                                intent22.putExtra("to", pos2);
                                startActivity(intent22);


                                //Toast.makeText(CustomerBookingActivity.this,todaysdate, Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    //nav header
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

                    weluser.setText("Hi, "+fullname+"\n"+"Welcome to");
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

    public String Add(String a, String b) {
        String bb = a.concat(b);
        return bb;
    }

    public String Add(String a, String b, String c) {
        String bb = a.concat(b.concat(c));
        return bb;
    }


    //Bus list from different places
    public void createDataWiseBusList() {
        String Timere[] = {"10:30", "12:30", "17:30", "23:55"};
        String Startre[] = {"Dhaka", "Sylhet", "Rajshahi", "Chittagoan", "Khulna", "Cox's Bazar", "Bandarban", "Mymensingh", "Rangpur"};
        String Endre[] = {"Dhaka", "Sylhet", "Rajshahi", "Chittagoan", "Khulna", "Cox's Bazar", "Bandarban", "Mymensingh", "Rangpur"};

        String date = ticketdate;
        //date ta rakhsi...intent theke
        //  String Time[]={9:30,12:30,17:30,23:55};
        //String Startre[]={"Dhaka", "Sylhet", "Rajshahi", "Chittagoan", "Khulna", "Cox's Bazar", "Bandarban", "Mymensingh", "Rangpur"};
        //String Endre[]={"Dhaka", "Sylhet", "Rajshahi", "Chittagoan", "Khulna", "Cox's Bazar", "Bandarban", "Mymensingh", "Rangpur"};
        int val = 1;
        for (int i = 0; i <= 3; i++) {

            for (int j = 0; j <= 8; j++) {


                for (int k = 0; k <= 8; k++) {
                    if (j != k) {
                        String BusId = Integer.toString(val);
                        BusSeat bs = new BusSeat(BusId, date, Startre[j], Endre[k], Timere[i], "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true", "true");
                        DBref.child(date).child(BusId).setValue(bs);
                        //BusId =  Add(BusId,Endre[k]);
                        val++;
                        //Add values with
                    }
                }
            }

        }


    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

