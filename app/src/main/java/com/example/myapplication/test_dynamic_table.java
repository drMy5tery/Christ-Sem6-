package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class test_dynamic_table extends AppCompatActivity {
    int i=0;
    LinearLayout parentlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dynamic_table);
        parentlayout = (LinearLayout)findViewById(R.id.parent_layout);


    }

    public View myViewReview() {

        View v; // Creating an instance for View Object
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.row_facility_review, null);
        TextView row_reviewer_name = (TextView) v.findViewById(R.id.textView10);
        TextView row_review_text = (TextView) v.findViewById(R.id.textView12);
        return v;
    }
    public void add_user(View view){
        parentlayout.addView(myViewReview());
    }
}
