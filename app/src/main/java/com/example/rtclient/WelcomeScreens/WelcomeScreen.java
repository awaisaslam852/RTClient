package com.example.rtclient.WelcomeScreens;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rtclient.Dashboard.Dashboard;
import com.example.rtclient.Helpers.PrefManager;
import com.example.rtclient.Helpers.Utils;
import com.example.rtclient.R;
import com.example.rtclient.WelcomeScreens.Fragments.WelcomeSlide1;
import com.example.rtclient.WelcomeScreens.Fragments.WelcomeSlide2;
public class WelcomeScreen extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    public static  LinearLayout dotsLayout;
    public static TextView[] dots;
//    private int[] layouts;
    private PrefManager prefManager;
    private String firstLoginOrNot ;
    private FrameLayout frameLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            firstLoginOrNot = "no";
            launchHomeScreen();
        }
        setContentView(R.layout.activity_welcome_screen);

        fragmentManager.beginTransaction().replace(R.id.welcome_frame_container,
                new WelcomeSlide1(), Utils.WelcomeSlide1).commit();

        dotsLayout =  findViewById(R.id.layoutDots);

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        addBottomDots(0, this, colorsActive, colorsInactive);



    }



    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(WelcomeScreen.this, Dashboard.class);
        intent.putExtra("yn",firstLoginOrNot);
        startActivity(intent);
        finish();
    }

//--------------------------------------------------------------------------------------------------


    public void addBottomDots(int currentPage, Context context, int[] colorsActive, int[] colorsInactive) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

@Override
public void onBackPressed() {
    Fragment welcome2 = fragmentManager.findFragmentByTag(Utils.WelcomeSlide2);
    Fragment welcome3 = fragmentManager.findFragmentByTag(Utils.WelcomeSlide3);

    if (welcome2 != null)
        replaceWelcome1();
    else if (welcome3 != null)
        replaceWelcome2();
    else
        super.onBackPressed();
}
    public void replaceWelcome1() {
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        addBottomDots(0, this, colorsActive, colorsInactive);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.welcome_frame_container, new WelcomeSlide1(),
                        Utils.WelcomeSlide1).commit();
    }
    public void replaceWelcome2() {
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        addBottomDots(1, this, colorsActive, colorsInactive);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.welcome_frame_container, new WelcomeSlide2(),
                        Utils.WelcomeSlide2).commit();
    }

}
