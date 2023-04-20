package com.example.myapplication.voters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pl.droidsonroids.gif.GifImageView;

public class sa_validate extends AppCompatActivity {
    TextInputEditText sa_id;
    String id_read;
    boolean playing = false;
    MediaPlayer mp;
    GifImageView done;
    Button vali,nxt;
    public int count;
    public static sa_variables sa_va;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_validate);

        sa_id=findViewById(R.id.uniq_id);
        done=findViewById(R.id.imageView11);
        done.setVisibility(View.INVISIBLE);
        vali=findViewById(R.id.button2);
        nxt=findViewById(R.id.button4);
         mp = MediaPlayer.create(sa_validate.this,R.raw.success);
         nxt.setVisibility(View.INVISIBLE);
//Listen on play button
//        play.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                if(!playing){
//                    mp.start();
//                    playing=true;
//                }
//            }
//        });

    }
    public void validate(View v){
        id_read=sa_id.getText().toString();

        FirebaseDatabase db= FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference node=db.getReference("Christ").child("Voters");
        // Query check_name=node.orderByChild().equalTo(id_read);
        // Toast.makeText(sa_MainActivity2.this,, Toast.LENGTH_SHORT).show();

        node.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(id_read)){
                    DatabaseReference check_status=node.child(id_read).child("valid_status");

                    check_status.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(Integer.parseInt(snapshot.getValue().toString())==1){

                                Toast.makeText(getApplicationContext(), "Id Already Validated", Toast.LENGTH_SHORT).show();

                            }

                            else{

                                sa_va=new sa_variables(id_read);


                                Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_SHORT).show();
                                done.setVisibility(View.VISIBLE);
                                vali.setVisibility(View.INVISIBLE);
                                nxt.setVisibility(View.VISIBLE);
                                if(!playing){
                                    mp.start();
                                    playing=true;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                else{
                    Toast.makeText(getApplicationContext(), "Invalid unique id", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void next(View v) {

        Intent i= new Intent(sa_validate.this, sa_upload.class);
        startActivity(i);
    }

}