package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sa_MainActivity3 extends AppCompatActivity {
    ImageView sa_iv1;
    EditText nm,cs,du,ro;
    Button btn;

    private static final int pic_id = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_main3);

            nm=findViewById(R.id.editTextTextPersonName);
            cs=findViewById(R.id.editTextTextPersonName2);
            du=findViewById(R.id.editTextTextPersonName3);
            ro=findViewById(R.id.editTextTextPersonName4);
            btn=findViewById(R.id.button5);







    }
    public void connect(View v){
        Toast.makeText(sa_MainActivity3.this, "done", Toast.LENGTH_SHORT).show();

        String roll=ro.getText().toString().trim();
        String name=nm.getText().toString().trim();
        String course=cs.getText().toString().trim();
        String duration=du.getText().toString().trim();

        //dataHolder obj=new dataHolder(name,course,duration);

        FirebaseDatabase db= FirebaseDatabase.getInstance("https://sample-cb944-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference node= db.getReference("students");
       // node.child(roll).setValue(obj);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user");
//
//        myRef.setValue("Hello");
    }




}
