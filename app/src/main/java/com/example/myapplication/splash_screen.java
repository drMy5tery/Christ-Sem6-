package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash_screen extends AppCompatActivity {

    ImageView image;
    //TextView logo;
    ImageView logo;
    //TextView slogan;
    ImageView slogan;
    Animation sideAnim, bottomAnim;

    private static int SPLASH_TIMER = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        image = findViewById(R.id.sb_logo_splash);
        logo = findViewById(R.id.sb_uppertext);
        slogan = findViewById(R.id.sb_bottomtext);

        //Animations

        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        image.setAnimation(sideAnim);
        logo.setAnimation(bottomAnim);
        //  slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), choose_user_type.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIMER);

    }
}