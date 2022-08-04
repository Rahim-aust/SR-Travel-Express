package com.example.srtravelexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buslist extends AppCompatActivity {

    RecyclerView recyclerView;

    BusAdapter adapter;
    ArrayList<Bus> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        Intent intent = getIntent();
        String tickets = intent.getStringExtra("ticketdate");
        String todays = intent.getStringExtra("todaysdate");
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");


        recyclerView = findViewById(R.id.buslist);
        //DatabaseReference DBref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/").child("BUSTWO").child(tickets);
        DatabaseReference DBref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sr-travel-express-default-rtdb.firebaseio.com/").child("BUSTWO").child(tickets);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new BusAdapter(this, list);
        recyclerView.setAdapter(adapter);

        DBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int valuere = 1;
                list.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {


                    BusSeat bss = datasnapshot.getValue(BusSeat.class);
                    Bus bs = new Bus(bss.getBusId(), bss.getFrom(), bss.getTo(), bss.getDate(), bss.getTimes());
                    String space=" ";

                    String dbsdate=bs.getDate();
                    if(todays.substring(0,10).equals(tickets)) {

                        String temp=Add(bs.getDate()," ",bs.getTime());
                        if( bs.getFrom().equals(from) && bs.getTo().equals(to))
                        {
                            list.add(bs);
                        }
                    }
                    else
                    {
                        if(bs.getFrom().equals(from) && bs.getTo().equals(to))
                        {
                            list.add(bs);
                        }
                    }

                    //list.add(bs);

                    /*if(valuere<=10) {
                        list.add(bs);
                        valuere++;
                    }*/
                    //}
                    //}
                    //else
                    //{
                    //   String temp=Add(bs.getDate()," ",bs.getTime());
                    //   if(Checkre(temp,todays))
                    //   {
                    //       if(from==bs.getFrom() && to==bs.getDate())
                    //       {
                    //          list.add(bs);
                    //      }
                    //  }

                    // }
                    //else if()
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public boolean Checkre2(String s,String s2)
    {
        int year,month,day,hour,min;
        year=Timere(s,0,4);
        month=Timere(s,5,2);
        day=Timere(s,8,2);
        //hour=Timere(s,10,2);
        //min=Timere(s,12,2);

        int year2,month2,day2,hour2,min2;
        year2=Timere(s2,0,4);
        month2=Timere(s2,5,2);
        day2=Timere(s2,8,2);
        //hour2=Timere(s2,10,2);
        //min2=Timere(s2,12,2);

        if(year>year2) return true;
        else if(year<year2) return false;
        if(month>month2) return true;
        else if(month<month2) return false;
        if(day>day2) return true;
        else if(day<day2) return false;
        return true;
        //if(hour>hour2) return true;
        //else if(hour<hour2) return false;

        //if(min>min2) return true;
        //else if(min<min2) return false;

        //return true;
    }

    public boolean Checkre(String s,String s2)
    {
        int year,month,day,hour,min;
        year=Timere(s,0,4);
        month=Timere(s,5,2);
        day=Timere(s,8,2);
        hour=Timere(s,11,2);
        min=Timere(s,14,2);

        int year2,month2,day2,hour2,min2;
        year2=Timere(s2,0,4);
        month2=Timere(s2,5,2);
        day2=Timere(s2,8,2);
        hour2=Timere(s2,11,2);
        min2=Timere(s2,14,2);

        if(year>year2) return true;
        else if(year<year2) return false;
        if(month>month2) return true;
        else if(month<month2) return false;
        if(day>day2) return true;
        else if(day<day2) return false;
        if(hour>hour2) return true;
        else if(hour<hour2) return false;

        if(min>min2) return true;
        else if(min<min2) return false;

        return true;
    }


    public int Timere(String s,int pos,int len)
    {

        String ss=s.substring(pos,pos+len-1);
        int vals=Integer.parseInt(ss);
        return vals;

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

}




