package com.example.srtravelexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerSeatSelectionActivity extends AppCompatActivity {
    //declaration
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    CheckBox A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,D1,D2,D3,D4,E1,E2,E3,E4;
    TextView Camount,Ccost;
    int tot=0,cost=0;
    String busid,from,to,date,time;
    private Button Cpayment,Cdone;
    int ager=0,ekhon=0;
    private ArrayList<String> Seats=new ArrayList<>();
    private ArrayList<String> Now=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_seat_selection);

        Intent intent=getIntent();

        busid=intent.getStringExtra("busid");
        from=intent.getStringExtra("from");
        to=intent.getStringExtra("to");
        date=intent.getStringExtra("date");
        time=intent.getStringExtra("time");



        A1=findViewById(R.id.A1);
        A2=findViewById(R.id.A2);
        A3=findViewById(R.id.A3);
        A4=findViewById(R.id.A4);

        B1=findViewById(R.id.B1);
        B2=findViewById(R.id.B2);
        B3=findViewById(R.id.B3);
        B4=findViewById(R.id.B4);

        C1=findViewById(R.id.C1);
        C2=findViewById(R.id.C2);
        C3=findViewById(R.id.C3);
        C4=findViewById(R.id.C4);

        D1=findViewById(R.id.D1);
        D2=findViewById(R.id.D2);
        D3=findViewById(R.id.D3);
        D4=findViewById(R.id.D4);

        E1=findViewById(R.id.E1);
        E2=findViewById(R.id.E2);
        E3=findViewById(R.id.E3);
        E4=findViewById(R.id.E4);
        Cdone=findViewById(R.id.OK);


        Camount=findViewById(R.id.amount);
        Ccost=findViewById(R.id.cost);
        // DatabaseReference DBref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/").child("BUSTWO").child(date);


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cpayment = findViewById(R.id.Customer_payment);

        getSupportActionBar().setTitle("        Seat Selection");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference DBref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/").child("BUSTWO").child(date);

        DBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    BusSeat bss = datasnapshot.getValue(BusSeat.class);
                    if(bss.getBusId().equals(busid))
                    {
                          if(bss.getA1().equals("false"))
                          {
                              Seats.add("A1");
                              A1.setChecked(true);
                              A1.setEnabled(false);

                              ager++;
                          }
                          if(bss.getA2().equals("false"))
                          {
                              Seats.add("A2");
                              A2.setChecked(true);
                              A2.setEnabled(false);
                              ager++;
                          }
                          if(bss.getA3().equals("false"))
                          {
                              Seats.add("A3");
                              A3.setChecked(true);
                              A3.setEnabled(false);
                              ager++;
                         }
                        if(bss.getA4().equals("false"))
                        {
                            Seats.add("A4");
                            A4.setChecked(true);
                            A4.setEnabled(false);
                            ager++;
                        }

                        if(bss.getB1().equals("false"))
                        {
                            Seats.add("B1");
                            B1.setChecked(true);
                            B1.setEnabled(false);
                            ager++;
                        }
                        if(bss.getB2().equals("false"))
                        {
                            Seats.add("B2");
                            B2.setChecked(true);
                            B2.setEnabled(false);
                            ager++;
                        }
                        if(bss.getB3().equals("false"))
                        {
                            Seats.add("B3");
                            B3.setChecked(true);
                            B3.setEnabled(false);
                            ager++;
                        }
                        if(bss.getB4().equals("false"))
                        {
                            Seats.add("B4");
                            B4.setChecked(true);
                            B4.setEnabled(false);
                            ager++;
                        }

                        if(bss.getC1().equals("false"))
                        {
                            Seats.add("C1");
                            C1.setChecked(true);
                            C1.setEnabled(false);
                            ager++;
                        }
                        if(bss.getC2().equals("false"))
                        {
                            Seats.add("C2");
                            C2.setChecked(true);
                            C2.setEnabled(false);
                            ager++;
                        }
                        if(bss.getC3().equals("false"))
                        {
                            Seats.add("C3");
                            C3.setChecked(true);
                            C4.setEnabled(false);
                            ager++;
                        }
                        if(bss.getC4().equals("false"))
                        {
                            Seats.add("C4");
                            C4.setChecked(true);
                            C4.setEnabled(false);
                            ager++;
                        }

                        if(bss.getD1().equals("false"))
                        {
                            Seats.add("D1");
                            D1.setChecked(true);
                            D1.setEnabled(false);
                            ager++;
                        }
                        if(bss.getD2().equals("false"))
                        {
                            Seats.add("D2");
                            D2.setChecked(true);
                            D2.setEnabled(false);
                            ager++;
                        }
                        if(bss.getD3().equals("false"))
                        {
                            Seats.add("D4");
                            D3.setChecked(true);
                            D3.setEnabled(false);
                            ager++;
                        }
                        if(bss.getD4().equals("false"))
                        {
                            Seats.add("D4");
                            D4.setChecked(true);
                            D4.setEnabled(false);
                            ager++;
                        }

                        if(bss.getE1().equals("false"))
                        {
                            Seats.add("E1");
                            E1.setChecked(true);
                            E1.setEnabled(false);
                            ager++;
                        }
                        if(bss.getE2().equals("false"))
                        {
                            Seats.add("E2");
                            E2.setChecked(true);
                            E2.setEnabled(false);
                            ager++;
                        }
                        if(bss.getE3().equals("false"))
                        {
                            Seats.add("E3");
                            E3.setChecked(true);
                            E3.setEnabled(false);
                            ager++;
                        }
                        if(bss.getE4().equals("false"))
                        {
                            Seats.add("E4");
                            E4.setChecked(true);
                            E4.setEnabled(false);
                            ager++;
                        }



                       break;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        Cdone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //String a=A1.isChecked();
                //Log.d("jhamela",String.valueOf(Seats.size()));
                Now.clear();
                if(A1.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("A1")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("A1");

                    }
                }

                if(A2.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("A2")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("A2");
                    }
                }

                if(A3.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("A3")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("A3");
                    }
                }

                if(A4.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("A4")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("A4");
                    }
                }

                if(B1.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("B1")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("B1");

                    }
                }

                if(B2.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("B2")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("B2");
                    }
                }

                if(B3.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("B3")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("B3");
                    }
                }

                if(B4.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("B4")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("B4");
                    }
                }

                if(C1.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("C1")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("C1");

                    }
                }

                if(C2.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("C2")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("C2");
                    }
                }

                if(C3.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("C3")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("C3");
                    }
                }

                if(C4.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("C4")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("C4");
                    }
                }

                if(D1.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("D1")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("D1");

                    }
                }

                if(D2.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("D2")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("D2");
                    }
                }

                if(D3.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("D3")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("D3");
                    }
                }

                if(D4.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("D4")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("D4");
                    }
                }


                if(E1.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("E1")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("E1");

                    }
                }

                if(E2.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("E2")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("E2");
                    }
                }

                if(E3.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("E3")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("E3");
                    }
                }

                if(E4.isChecked())
                {
                    boolean f=false;
                    for(int i=0;i<Seats.size();i++)
                    {
                        if(Seats.get(i).equals("E4")) {f=true;break;}
                    }
                    if(!f)
                    {
                        Now.add("E4");
                    }
                }
                if(Now.size()==0)
                {
                    Camount.setText("Amount");
                    Ccost.setText("Cost");
                    Toast.makeText(CustomerSeatSelectionActivity.this, "No Tickets selected", Toast.LENGTH_SHORT).show();
                }
                else if(Now.size()>4)
                {
                    Camount.setText("Amount");
                    Ccost.setText("Cost");
                    Toast.makeText(CustomerSeatSelectionActivity.this, "More than 4 seat selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    tot=Now.size();
                    cost=tot*450;

                    Camount.setText(String.valueOf(tot));
                    Ccost.setText(String.valueOf(cost));
                }


            }
        });


        Cpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totre=Now.size();
                if(totre==0)
                {
                    Camount.setText("Amount");
                    Ccost.setText("Cost");
                    Toast.makeText(CustomerSeatSelectionActivity.this, "You are not done with your selection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), CustomerPaymentSystemActivity.class);
                    intent.putExtra("busid",busid);
                    intent.putExtra("from",from);
                    intent.putExtra("to",to);
                    intent.putExtra("date",date);
                    intent.putExtra("time",time);
                    intent.putExtra("cost",String.valueOf(cost));
                    intent.putExtra("amount",String.valueOf(tot));
                    intent.putStringArrayListExtra("seats",Now);
                    startActivity(intent);
                }
            }
        });
    }
}