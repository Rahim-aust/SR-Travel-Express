package com.example.srtravelexpress;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomerPaymentSystemActivity extends AppCompatActivity {

    public String fullname, email, phone, gender;
    //declaration
    Toolbar toolbar;
    TextView paymentNum, transId;
    TextView selected_seat, total_amount;
    ImageView paymentInstruction;
    EditText TransId;
    String x1;
    String busid, from, to, date, cost, seatamount, username, time;
    String tempo;
    FirebaseUser firebaseUser;
    DatabaseReference DBref;
    private FirebaseAuth authProfile;
    private ArrayList<String> seats = new ArrayList<>();
    private RadioGroup group;
    private RadioButton Bkash, Nagad, Rocket;
    private Button ConPay, CanPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment_system);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        DBref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/");

        Intent intent = getIntent();
        busid = intent.getStringExtra("busid");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        cost = intent.getStringExtra("cost");
        seatamount = intent.getStringExtra("amount");

        seats = intent.getStringArrayListExtra("seats");
        selected_seat = findViewById(R.id.selected_seat);
        total_amount = findViewById(R.id.total_amount);
        tempo = "";
        for (int i = 0; i < seats.size(); i++) {
            tempo = Add(tempo, " ", seats.get(i));

        }
        selected_seat.setText(Add("Selected Seat: ", tempo));
        total_amount.setText(Add("Total Amount : ", cost));


        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        group = findViewById(R.id.Payment_group);
        paymentInstruction = findViewById(R.id.payment_system);
        paymentNum = findViewById(R.id.pay_num);
        transId = findViewById(R.id.tranID);
        TransId = findViewById(R.id.TransID);
        Bkash = findViewById(R.id.bkash_pay);
        Nagad = findViewById(R.id.nagad_pay);
        Rocket = findViewById(R.id.rocket_pay);
        ConPay = findViewById(R.id.Confirm_Ticket);
        CanPay = findViewById(R.id.Cancel_Ticket);

        getSupportActionBar().setTitle("          Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TransId.setVisibility(View.INVISIBLE);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.bkash_pay:
                        paymentNum.setText("Paymentable Number is 01521xxxxx" + "\n" + "Please follow the steps");
                        paymentInstruction.setImageDrawable(getResources().getDrawable(R.drawable.bkashsystem1));
                        transId.setText("Transaction Number: ");
                        TransId.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nagad_pay:
                        paymentNum.setText("Paymentable Number is 01521xxxxx" + "\n" + "Please follow the steps");
                        paymentInstruction.setImageDrawable(getResources().getDrawable(R.drawable.nagadpayment));
                        transId.setText("Transaction Number: ");
                        TransId.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rocket_pay:
                        paymentNum.setText("Paymentable Number is 01521xxxxx" + "\n" + "Please follow the steps");
                        paymentInstruction.setImageDrawable(getResources().getDrawable(R.drawable.rocketpayment1));
                        transId.setText("Transaction Number: ");
                        TransId.setVisibility(View.VISIBLE);
                        break;
                    default:
                        TransId.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        ConPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TransId.getText().toString().isEmpty()) {
                    Toast.makeText(CustomerPaymentSystemActivity.this, "Please confirm your payment fast", Toast.LENGTH_SHORT).show();
                    TransId.setError("Transaction Id is required!");
                    TransId.requestFocus();
                } else {

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

                            }

                            Tickets tickets = new Tickets(TransId.getText().toString(), email, phone, busid, from, to, date, cost, seatamount, fullname, time, seats);
                            String userID = firebaseUser.getUid();
                            DBref.child("TicketDetails").child(userID).setValue(tickets);
                            for (int i = 0; i < seats.size(); i++) {
                                if (seats.get(i).equals("A1")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("a1").setValue("false");
                                }
                                if (seats.get(i).equals("A2")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("a2").setValue("false");
                                }
                                if (seats.get(i).equals("A3")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("a3").setValue("false");
                                }
                                if (seats.get(i).equals("A4")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("a4").setValue("false");
                                }

                                if (seats.get(i).equals("B1")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("b1").setValue("false");
                                }
                                if (seats.get(i).equals("B2")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("b2").setValue("false");
                                }
                                if (seats.get(i).equals("B3")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("b3").setValue("false");
                                }
                                if (seats.get(i).equals("B4")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("b4").setValue("false");
                                }

                                if (seats.get(i).equals("C1")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("c1").setValue("false");
                                }
                                if (seats.get(i).equals("C2")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("c2").setValue("false");
                                }
                                if (seats.get(i).equals("C3")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("c3").setValue("false");
                                }
                                if (seats.get(i).equals("C4")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("c4").setValue("false");
                                }


                                if (seats.get(i).equals("D1")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("d1").setValue("false");
                                }
                                if (seats.get(i).equals("D2")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("d2").setValue("false");
                                }
                                if (seats.get(i).equals("D3")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("d3").setValue("false");
                                }
                                if (seats.get(i).equals("D4")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("d4").setValue("false");
                                }


                                if (seats.get(i).equals("E1")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("e1").setValue("false");
                                }
                                if (seats.get(i).equals("E2")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("e2").setValue("false");
                                }
                                if (seats.get(i).equals("E3")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("e3").setValue("false");
                                }
                                if (seats.get(i).equals("E4")) {
                                    DBref.child("BUSTWO").child(date).child(busid).child("e4").setValue("false");
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(CustomerPaymentSystemActivity.this, "Something went wrong! User's details are not available at this moment", Toast.LENGTH_SHORT).show();

                        }
                    });


                    Toast.makeText(CustomerPaymentSystemActivity.this, "Your ticket is booked. Thank you", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(CustomerPaymentSystemActivity.this, CustomerBookingActivity.class));
                    finishAffinity();
                }

            }
        });

        CanPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAleartDialoge();
            }
        });


    }


    private void CreateAleartDialoge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure to  cancel?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CustomerPaymentSystemActivity.this, "Your process is cancel. Please try again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CustomerPaymentSystemActivity.this, CustomerBookingActivity.class));
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CustomerPaymentSystemActivity.this, "Please confirm your payment to book ticket.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CustomerPaymentSystemActivity.this, CustomerPaymentSystemActivity.class));
                finish();
            }
        });
        builder.create();
        builder.show();
    }


    public String Add(String a, String b) {
        String bb = a.concat(b);
        return bb;
    }

    public String Add(String a, String b, String c) {
        String bb = a.concat(b.concat(c));
        return bb;
    }


}