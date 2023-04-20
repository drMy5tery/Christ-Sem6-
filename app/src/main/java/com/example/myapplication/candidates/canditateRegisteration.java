package com.example.myapplication.candidates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class canditateRegisteration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canditate_registeration);
    }
    public void conduct(View v) {
        Intent i= new Intent(getApplicationContext(),candidateDetails.class);
        startActivity(i);
    }

}