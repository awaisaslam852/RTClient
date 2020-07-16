package com.example.rtclient;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.rtclient.Dashboard.Dashboard;
import com.example.rtclient.WelcomeScreens.WelcomeScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SplashScreen extends AppCompatActivity {

    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

//        auth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        auth = FirebaseAuth.getInstance();

//        FirebaseUser user = auth.getCurrentUser();
//        if (user != null) {
//            Intent intent = new Intent(SplashScreen.this, Dashboard.class);
//            startActivity(intent);
//            finish();
//        } else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
//        }


    }


}