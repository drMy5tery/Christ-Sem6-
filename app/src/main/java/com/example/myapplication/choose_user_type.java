package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.admin.admin_login;
import com.example.myapplication.super_admin.super_admin_login;
import com.example.myapplication.voters.sa_login;

public class choose_user_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);
    }
    public void admin_login(View v){
        Intent i= new Intent(choose_user_type.this, admin_login.class);
        startActivity(i);
    }
    public void sAdmin_login(View v){
        Intent i= new Intent(choose_user_type.this, super_admin_login.class);
        startActivity(i);
    }
    public void voter_login(View v){
        Intent i= new Intent(choose_user_type.this, sa_login.class);
        startActivity(i);
    }
}