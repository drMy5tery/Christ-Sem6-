package com.example.myapplication.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class admin_login extends AppCompatActivity {

    EditText etId,etPass;
    String sa_votid,sa_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rj_activity_admin_login);
        etId=findViewById(R.id.editTextTextEmailAddress);
        etPass=findViewById(R.id.editTextPassword);
    }
    public void admin_validate(View view){
        Intent intent = new Intent(getApplicationContext(),admin_validation.class);
        startActivity(intent);
    }
    public void adminLogin(View view) {
        Intent i = new Intent(getApplicationContext(), admin_home.class);
        startActivity(i);
    }

    /*    // Write a message to the database
        sa_votid=etId.getText().toString();
        sa_pass=etPass.getText().toString();
        sa_pass=sha256(sa_pass);



        DatabaseReference ref = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        Query applesQuery = ref.child("Christ").child("Admin").orderByChild("eMail").equalTo(sa_votid);

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
                            Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_add_users.class);
                            startActivity(i);
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
/*

 */