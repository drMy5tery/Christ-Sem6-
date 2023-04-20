package com.example.myapplication.candidates;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class userlist2 extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter2 myAdapter;
    public ArrayList<String> electlist;
    ImageView image;
    TextView textt;
    // String eename=MyAdapter2.data.getEname();
    //String eename="aaa";
    final Context context = this;
    DatabaseReference refer1 = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Elections");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist2);

        recyclerView = findViewById(R.id.userList);
        recyclerView.setVisibility(View.INVISIBLE);

        image=findViewById(R.id.imageView22);
        textt=findViewById(R.id.textView23);

        database = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Elections");
        electlist=new ArrayList<>();
        printChildrenCount(database);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter2(this,electlist);
        recyclerView.setAdapter(myAdapter);




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
    public void conduct(View v){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.activity_sa_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                String data = userInput.getText().toString();
                                FirebaseDatabase db= FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                DatabaseReference node=db.getReference("Christ").child("Elections").child(data).child("candidates").child("1");
                                node.setValue("Sathya");
//                                 database = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Christ").child("Elections").child(data).child("candidates");
//                                 printChildrenCount(database);

                                Toast.makeText(getApplicationContext(), "Election created successfully!!", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(userlist2.this,userlist2.class);
                                startActivity(i);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}
