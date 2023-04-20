package com.example.myapplication.voters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Elect_name;
import com.example.myapplication.R;
import com.example.myapplication.candidates.MyAdapter2;
import com.example.myapplication.candidates.MyAdapter3;
import com.example.myapplication.candidates.MyAdapter5;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sa_home extends AppCompatActivity {

    int LABEL_VISIBILITY_LABELED = BottomNavigationView.LABEL_VISIBILITY_LABELED;
    int LABEL_VISIBILITY_UNLABELED = BottomNavigationView.LABEL_VISIBILITY_UNLABELED;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    String email;
    TextView namee,orgname;
    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    public static Elect_name v_name;

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
        setContentView(R.layout.activity_sa_home);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        namee=findViewById(R.id.textView7);
        orgname=findViewById(R.id.textView21);
        recyclerView = findViewById(R.id.userList);
        recyclerView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.item1);
        image=findViewById(R.id.imageView23);
        textt=findViewById(R.id.textView23);

        database = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Electionss");
        electlist=new ArrayList<>();
        printChildrenCount(database);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter5(this,electlist);
        recyclerView.setAdapter(myAdapter);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null);

        v_name=new Elect_name(email);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:


                        return true;
                    case R.id.item2:
                        Intent i= new Intent(sa_home.this,sa_vote.class);
                        startActivity(i);


                        return true;
                    case R.id.item3:
                       i= new Intent(sa_home.this,sa_profile.class);
                        startActivity(i);

                        return true;




                }
                return false;
            }
        });
        DatabaseReference db = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Christ").child("Voters");
        Query query = db.orderByChild("email").equalTo(email);//.orderByValue().equalTo("Salary");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    String eType = userSnapshot.child("name").getValue(String.class);
                    String eType2 = userSnapshot.child("organization").getValue(String.class);
                    namee.setText(eType.toUpperCase());
                    orgname.setText(eType2.toUpperCase());
                    //Log.d("",eType);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


    }
    public void logout(View v) {

        // calling method to edit values in shared prefs.
        SharedPreferences.Editor editor = sharedpreferences.edit();

        // below line will clear
        // the data in shared prefs.
        editor.clear();

        // below line will apply empty
        // data to shared prefs.
        editor.apply();

        // starting mainactivity after
        // clearing values in shared preferences.
        Intent i = new Intent(sa_home.this,sa_login.class);
        startActivity(i);
        finish();
    }
    private void getElectNames() {
        refer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Result will be holded Here

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String userkey  = dsp.getKey();
                    //Toast.makeText(getApplicationContext(), userkey, Toast.LENGTH_SHORT).show();
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
}