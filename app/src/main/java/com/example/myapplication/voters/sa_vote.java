package com.example.myapplication.voters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.candidates.MyAdapter2;
import com.example.myapplication.candidates.MyAdapter5;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sa_vote extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter5 myAdapter;
    public ArrayList<String> electlist;
    ImageView image;
    TextView textt;
    // String eename=MyAdapter2.data.getEname();
    //String eename="aaa";
    final Context context = this;
    DatabaseReference refer1 = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Electionss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_vote);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.userList);
        recyclerView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.item2);
        image=findViewById(R.id.imageView23);
        textt=findViewById(R.id.textView23);

        database = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Electionss");
        electlist=new ArrayList<>();
        printChildrenCount(database);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter5(this,electlist);
        recyclerView.setAdapter(myAdapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Intent i= new Intent(sa_vote.this,sa_home.class);
                        startActivity(i);

                        return true;
                    case R.id.item2:
                         i= new Intent(sa_vote.this,sa_vote.class);
                        startActivity(i);


                        return true;
                    case R.id.item3:
                        i= new Intent(sa_vote.this,sa_profile.class);
                        startActivity(i);

                        return true;




                }
                return false;
            }
        });


//        refer1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    Candidate user = dataSnapshot.getValue(Candidate.class);
//                    list.add(user);
//
//
//                }
//                myAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
    private void getElectNames() {
        refer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Result will be holded Here

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                   // Toast.makeText(getApplicationContext(), userkey, Toast.LENGTH_SHORT).show();
                    electlist.add(userkey); //add result into array list
                }
                myAdapter.notifyDataSetChanged();
                Log.e("", electlist.get(0));
            }


            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void printChildrenCount(DatabaseReference ref) {
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    long childrenCount = task.getResult().getChildrenCount();
                    Log.d("", "childrenCount: " + childrenCount);
                    if(childrenCount>=1){
                        recyclerView.setVisibility(View.VISIBLE);
                        image.setVisibility(View.INVISIBLE);
                        textt.setVisibility(View.INVISIBLE);
                        getElectNames();
                        //Log.d("bjhasbdhjbajbdjkas", String.valueOf(electlist.size()));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_SHORT).show();
                        image.setVisibility(View.VISIBLE);
                        textt.setVisibility(View.VISIBLE);
                    }

                } else {
                    Log.d("", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });
    }
    /**private void recycle(){
     refer1.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    for (DataSnapshot dataSnapshot : snapshot.getChildren()){

    Candidate user = dataSnapshot.getValue(Candidate.class);
    electlist.add(user);


    }
    myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    });
     }**/

}
