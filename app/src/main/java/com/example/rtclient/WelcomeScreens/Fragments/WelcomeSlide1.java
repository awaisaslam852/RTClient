package com.example.rtclient.WelcomeScreens.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rtclient.Helpers.Utils;
import com.example.rtclient.R;
import com.example.rtclient.WelcomeScreens.WelcomeScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
public class WelcomeSlide1 extends Fragment {

    private static FragmentManager fragmentManager;
    private Button nextBtn ;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_slide1, null);
//        View rootView = inflater.inflate(R.layout.welcome_slide1, container, false);
//        fragmentManager = getChildFragmentManager();
        fragmentManager = getActivity().getSupportFragmentManager();


        nextBtn = rootView.findViewById(R.id.btn_next_w1);
        final WelcomeScreen wl = new WelcomeScreen();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
                int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

                wl.addBottomDots(1, getActivity(), colorsActive, colorsInactive);

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.welcome_frame_container, new WelcomeSlide2(),
                                Utils.WelcomeSlide2).commit();
            }
        });


        /*..............................*/
        return rootView ;
    }



}

