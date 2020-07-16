package com.example.rtclient.WelcomeScreens.Fragments;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rtclient.Dashboard.Dashboard;
import com.example.rtclient.Helpers.PrefManager;
import com.example.rtclient.ModelClasses.UserData;
import com.example.rtclient.R;
import com.example.rtclient.WelcomeScreens.WelcomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isapanah.awesomespinner.AwesomeSpinner;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
public class WelcomeSlide3 extends Fragment {

    private PowerSpinnerView spinnerView1, spinnerView2 ;
    private PrefManager prefManager;
    private String firstLoginOrNot ;
    private Button nextBtn ;
    ///
    private FirebaseAuth auth ;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_slide3, null);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        auth = FirebaseAuth.getInstance();
        prefManager = new PrefManager(getActivity());


        spinnerView1 = rootView.findViewById(R.id.spinner_country_slide3);
        spinnerView2 = rootView.findViewById(R.id.spinner_state_slide3);
        nextBtn = rootView.findViewById(R.id.btn_next_w3);

        spinnerView2.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int i, String s) {
                Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            }
        });



        TextView tvv = rootView.findViewById(R.id.tvvv);
        tvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        /*......Getting Countries.......*/
        Locale[] locales;
        locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();

            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries);
        for (String country : countries) {
            System.out.println(country);
        }

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerView1.setItems(countries);
        spinnerView2.setItems(countries);


        /*.............................................*/
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLoginOrNot = "yes";
                authenticatingUser();
            }
        });






        /*..............................*/
        return rootView ;
    }

    private void authenticatingUser(){
        final ProgressDialog getDialog = new ProgressDialog(getActivity());
        getDialog.setMessage("Loading Please wait...");
        getDialog.show();
        auth.signInAnonymously()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = auth.getCurrentUser();
                            creatingDatabase(user.getUid(), getDialog);
                        } else {
                            getDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void creatingDatabase(String userUid, final Dialog getDialog){

        /*.......Getting Date & Time Strings........*/
        Long currentTime = System.currentTimeMillis(); //getting current time in millis

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clients").child(userUid).child("AccountInfo");

        UserData userData = new UserData(
                ""+userUid,
                ""+currentTime.toString()
        );

        reference.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    getDialog.dismiss();
                    launchHomeScreen();
                }else {
                    getDialog.dismiss();
                }
            }
        });



    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(getActivity(), Dashboard.class);
        intent.putExtra("yn",firstLoginOrNot);
        startActivity(intent);
        if (getActivity()!=null) {
            getActivity().finish();
        }
    }

}

