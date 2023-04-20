package com.example.myapplication.voters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class sa_login extends AppCompatActivity {
    TextInputEditText sa_t1,sa_t2;
    Button sa_b1;
    String sa_votid,sa_pass, org_name;
    private DatabaseReference df;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org_name="Christ";
        setContentView(R.layout.activity_sa_login);
        sa_b1=findViewById(R.id.sb_button_login);
        sa_t1=findViewById(R.id.uniq_id);
        sa_t2=findViewById(R.id.sb_in_password);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        sa_votid = sharedpreferences.getString(EMAIL_KEY, null);
        sa_pass = sharedpreferences.getString(PASSWORD_KEY, null);
    }

    public void loginUser(View v){

        sa_votid=sa_t1.getText().toString();
        sa_pass=sa_t2.getText().toString();
        sa_pass=sha256(sa_pass);



        DatabaseReference ref = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        Query applesQuery = ref.child(org_name).child("Voters").orderByChild("email").equalTo(sa_votid);

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
                        if(check_pass.equals(sa_pass)){
                        Toast.makeText(sa_login.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            // below two lines will put values for
                            // email and password in shared preferences.
                            editor.putString(EMAIL_KEY, sa_votid);
                            editor.putString(PASSWORD_KEY, sa_pass);

                            // to save our data with key and value.
                            editor.apply();
                        Intent i = new Intent(getApplicationContext(), sa_home.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(sa_login.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(sa_login.this, "Invalid username", Toast.LENGTH_SHORT).show();
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




//        if(sa_votid.isEmpty() || sa_pass.isEmpty()){
//            Toast.makeText(sa_MainActivity2.this, "Both the fields are required!!", Toast.LENGTH_SHORT).show();
//        }
//        else{
//
//
//            Toast.makeText(sa_MainActivity2.this, "Successfull", Toast.LENGTH_SHORT).show();
//        }


    }

    public void register_screen(View v){
        Intent i= new Intent(sa_login.this, sa_validate.class);
        startActivity(i);
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
        }
    }

    protected void onStart() {
        super.onStart();
        if (sa_votid != null && sa_pass != null) {
            Intent i = new Intent(sa_login.this, sa_home.class);
            startActivity(i);
        }
    }
}

