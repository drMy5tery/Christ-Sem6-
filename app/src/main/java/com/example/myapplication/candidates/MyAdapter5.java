package com.example.myapplication.candidates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.datas;
import com.example.myapplication.voters.candlist;

import java.util.ArrayList;

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.MyViewHolder> {

    Context context;

    ArrayList<String> list;
    public static datas data;

    public MyAdapter5(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_election_name,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String user = list.get(position);
        holder.electName.setText(user);
        //
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), user, Toast.LENGTH_SHORT).show();
                data=new datas(user);
                Intent i= new Intent(context, candlist.class);
                context.startActivity(i);



            }
        });


    }

    @Override
    public int getItemCount() {
        //Log.e("", list.toString());
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView electName;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            electName = itemView.findViewById(R.id.tvElectName);
            //age =
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

}

