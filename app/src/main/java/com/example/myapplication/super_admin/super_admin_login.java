package com.example.myapplication.super_admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.voters.sa_home;
import com.example.myapplication.voters.sa_login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class super_admin_login extends AppCompatActivity {
    EditText etMail,etPassword;
    String sa_sp_votid,sa_sp_pass, org_name;
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org_name="Christ";
        setContentView(R.layout.rj_activity_super_admin_login);
        etMail=findViewById(R.id.sa_email);
        etPassword=findViewById(R.id.sa_password);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        sa_sp_votid = sharedpreferences.getString(EMAIL_KEY, null);
        sa_sp_pass = sharedpreferences.getString(PASSWORD_KEY, null);


    }
    public void login(View view){
        Intent intent = new Intent(getApplicationContext(),super_admin_home.class);
        startActivity(intent);
        /*sa_sp_votid=etMail.getText().toString();
        sa_sp_pass=etPassword.getText().toString();
        sa_sp_pass=sha256(sa_sp_pass);



        DatabaseReference ref = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        Query applesQuery = ref.child(org_name).child("Super_Admin").orderByChild("email").equalTo(sa_sp_votid);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    if (appleSnapshot.exists()) {
                        String key = appleSnapshot.getKey();
                        //Log.d(key);

                        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                        String check_pass=dataSnapshot.child(key).child("password").getValue(String.class);
                        //Toast.makeText(getApplicationContext(), check_pass, Toast.LENGTH_SHORT).show();
                        if(check_pass.equals(sa_sp_pass)){
                            Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            // below two lines will put values for
                            // email and password in shared preferences.
                            editor.putString(EMAIL_KEY, sa_sp_votid);
                            editor.putString(PASSWORD_KEY, sa_sp_pass);

                            // to save our data with key and value.
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(),super_admin_home.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
                    }


//                    else {
//                        Toast.makeText(getApplicationContext(), " Email Does Not Exist",Toast.LENGTH_LONG).show();
//                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


        
    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }*/
    }
    public void registration(View view){
        Intent intent = new Intent(getApplicationContext(),super_admin_registration.class);
        startActivity(intent);
    }
}
