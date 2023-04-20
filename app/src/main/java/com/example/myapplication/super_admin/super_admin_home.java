package com.example.myapplication.super_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.voters.sa_home;
import com.example.myapplication.voters.sa_profile;
import com.example.myapplication.voters.sa_vote;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class super_admin_home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_home);
        bottomNavigationView=findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:


                        return true;
                    case R.id.item2:
                        Intent i= new Intent(getApplicationContext(), superadmin_add_admin.class);
                        startActivity(i);


                        return true;
                    case R.id.item3:

                        return true;




                }
                return false;
            }
        });
    }
}