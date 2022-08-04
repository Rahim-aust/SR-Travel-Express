package com.example.srtravelexpress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyViewHolder> {



    Context context;
    ArrayList<Bus> list;

    public BusAdapter(Context context, ArrayList<Bus> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.busitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Bus bus=list.get(position);
       holder.busidd.setText(bus.getBusId());
       holder.from.setText(bus.getFrom());
       holder.time.setText(bus.getTime());
       holder.to.setText(bus.getTo());
       holder.date.setText(bus.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView busidd,time,from,to,date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            busidd=itemView.findViewById(R.id.busiditem);
            time=itemView.findViewById(R.id.timeitem);
            from=itemView.findViewById(R.id.fromitem);
            to=itemView.findViewById(R.id.toitem);
            date=itemView.findViewById(R.id.dateitem);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view)
        {
              int position=getAdapterPosition();
              Intent intent=new Intent(context,CustomerSeatSelectionActivity.class);
              intent.putExtra("busid",list.get(position).getBusId());
              intent.putExtra("from",list.get(position).getFrom());
              intent.putExtra("to",list.get(position).getTo());
              intent.putExtra("date",list.get(position).getDate());
              intent.putExtra("time",list.get(position).getTime());
              context.startActivity(intent);
        }

    }
}
