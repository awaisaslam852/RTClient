package com.example.rtclient.Dashboard;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.rtclient.R;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class FiltersActivity extends AppCompatActivity {

    private ImageView backBtn ;
    private LinearLayout ll_for_feed, ll_for_mytopics ;
    private PowerSpinnerView spinner1_location, spinner2_state, spinner3_country, spinner4_state, spinner5_country ;
    String activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        activity = getIntent().getStringExtra("activity");

        ll_for_feed = findViewById(R.id.ll_for_feed_filters);
        ll_for_mytopics = findViewById(R.id.ll_for_mytopics_filters);
        backBtn = findViewById(R.id.back_btn_filters);
        spinner1_location = findViewById(R.id.spinner1_location_filters);
        spinner2_state    = findViewById(R.id.spinner2_state_filters);
        spinner3_country  = findViewById(R.id.spinner3_country_filters);
        spinner4_state    = findViewById(R.id.spinner4_state_filters);
        spinner5_country  = findViewById(R.id.spinner5_country_filters);

        if (activity!=null){
            if (activity.equals("Feed")){
                ll_for_feed.setVisibility(View.VISIBLE);
                ll_for_mytopics.setVisibility(View.GONE);
            }else {
                ll_for_feed.setVisibility(View.GONE);
                ll_for_mytopics.setVisibility(View.VISIBLE);
            }
        }else {
            finish();
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        spinner2_state.setItems(countries);
        spinner3_country.setItems(countries);
        spinner5_country.setItems(countries);
        spinner4_state.setItems(countries);

    }
}