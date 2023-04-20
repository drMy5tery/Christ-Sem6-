package com.example.myapplication.voters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dataHolder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class sa_register extends AppCompatActivity {
    ImageView sa_iv1;
    TextInputEditText nm,cs,du,ro;
    Button btn;

    private static final int pic_id = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_register);

        nm=findViewById(R.id.inp1);
        cs=findViewById(R.id.inp2);
        du=findViewById(R.id.inp3);
        ro=findViewById(R.id.name);
        btn=findViewById(R.id.button6);







    }
    public void connect(View v){

        //String uniq=sa_validate.sa_va.getFuid();
        String uniq="e44bf5a8-892c-4e2a";
        String email=ro.getText().toString().trim();
        String name=nm.getText().toString().trim();
        String organization=cs.getText().toString().trim();
        String password_en=du.getText().toString().trim();
        String password=sha256(password_en);
        dataHolder obj=new dataHolder(name,"Christ University",password,email,uniq,1);

        FirebaseDatabase db= FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference node=db.getReference("Christ").child("Voters");
        Query applesQuery = db.getReference("Christ").child("Voters").orderByChild("eMail").equalTo(email);
        if(!email.isEmpty()&&!name.isEmpty()&&!organization.isEmpty()&&!password.isEmpty()){
        //    Toast.makeText(sa_register.this, "func", Toast.LENGTH_SHORT).show();
            Log.d("",email);
            if(organization.equals(password_en)) {
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Toast.makeText(sa_register.this, "func", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            if (appleSnapshot.exists()) {
                                // appleSnapshot.getRef().removeValue();
                                node.child(uniq).setValue(obj);
                                Toast.makeText(sa_register.this, "Registeration successfull", Toast.LENGTH_SHORT).show();
                                ro.setText("");
                                nm.setText("");
                                cs.setText("");
                                du.setText("");
                                // db.getReference("Christ").child("Voters").child(uniq).child("valid_status").setValue("1");
                                //Toast.makeText(sa_validate.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), sa_login.class);
                                startActivity(i);
                            }
                            else
                            {
                               // Log.d("","Nope");
                                Toast.makeText(getApplicationContext(), " Email Does Not Exist", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "Passwords does not match!!", Toast.LENGTH_LONG).show();

            }


        }
        else{
            Toast.makeText(sa_register.this, "All the fields are required", Toast.LENGTH_SHORT).show();

        }


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user");
//
//        myRef.setValue("Hello");
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

}