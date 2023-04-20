package com.example.myapplication.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.candidates.userlist2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_home extends AppCompatActivity {



    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    String email;
    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:


                        return true;
                    case R.id.item2:
                       Intent i= new Intent(getApplicationContext(), userlist2.class);
                        startActivity(i);


                        return true;
                    case R.id.item3:
//                        i= new Intent(sa_MainActivity2.this,sa_MainActivity2.class);
//                        startActivity(i);

                        return true;
                    case R.id.item4:
                        i= new Intent(getApplicationContext(),admin_add_users.class);
                        startActivity(i);

                        return true;




                }
                return false;
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
        Intent i = new Intent(getApplicationContext(),admin_login.class);
        startActivity(i);
        finish();
    }

}