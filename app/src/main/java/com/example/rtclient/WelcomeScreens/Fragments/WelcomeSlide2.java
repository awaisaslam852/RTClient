package com.example.rtclient.WelcomeScreens.Fragments;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rtclient.Helpers.Utils;
import com.example.rtclient.R;
import com.example.rtclient.WelcomeScreens.WelcomeScreen;

import static android.content.Context.MODE_PRIVATE;
public class WelcomeSlide2 extends Fragment {

    private static FragmentManager fragmentManager;
    private Button btn1_dont, btn2_allow, nextBtn ;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_slide2, null);
        fragmentManager = getActivity().getSupportFragmentManager();


        btn1_dont  = rootView.findViewById(R.id.btn1_dont_welcome2);
        btn2_allow = rootView.findViewById(R.id.btn2_allow_welcome2);
        nextBtn = rootView.findViewById(R.id.btn_next_w2);

        final WelcomeScreen wl = new WelcomeScreen();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
                int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

                wl.addBottomDots(2, getActivity(), colorsActive, colorsInactive);
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.welcome_frame_container, new WelcomeSlide3(),
                                Utils.WelcomeSlide3).commit();
            }
        });
        //////////

        btn1_dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1_dont.setBackgroundDrawable(getResources().getDrawable(R.drawable.w2_btn_bg_selected));
                btn2_allow.setBackgroundDrawable(getResources().getDrawable(R.drawable.w2_btn_bg_unselected));
                btn1_dont.setTextColor(getResources().getColor(R.color.whiteColor));
                btn2_allow.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (getActivity()!=null) {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Notifications", MODE_PRIVATE).edit();
                    editor.putString("token", "not");
                    editor.apply();
                }
            }
        });

        btn2_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1_dont.setBackgroundDrawable(getResources().getDrawable(R.drawable.w2_btn_bg_unselected));
                btn2_allow.setBackgroundDrawable(getResources().getDrawable(R.drawable.w2_btn_bg_selected));
                btn1_dont.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn2_allow.setTextColor(getResources().getColor(R.color.whiteColor));
                if (getActivity()!=null) {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Notifications", MODE_PRIVATE).edit();
                    editor.putString("token", "subscribed");
                    editor.apply();
                }
            }
        });


        return rootView ;
    }



}

