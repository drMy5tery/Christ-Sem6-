package com.example.myapplication.voters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Elect_name;
import com.example.myapplication.R;

public class sa_profile extends AppCompatActivity {
        TextView mail;
    String mailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_profile);

         mailid= sa_home.v_name.getVoter_email();
        Log.d("",mailid);
        mail=findViewById(R.id.textView10);
       mail.setText(mailid);

    }
}