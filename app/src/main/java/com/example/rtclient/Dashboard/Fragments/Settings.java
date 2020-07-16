package com.example.rtclient.Dashboard.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rtclient.R;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends Fragment {

    private RecyclerView recyclerView ;
    private TextView emptyTv ;
    ///
    private FirebaseAuth auth ;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_settings_fragment, null);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

//        auth = FirebaseAuth.getInstance();
        initilize(rootView);



        /*..............................*/
        return rootView ;
}

    public void initilize(View view){



    }

}

