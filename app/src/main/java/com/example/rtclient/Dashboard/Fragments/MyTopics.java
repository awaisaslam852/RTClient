package com.example.rtclient.Dashboard.Fragments;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rtclient.Adapters.RecAdapter_Feed_frag;
import com.example.rtclient.Adapters.RecAdapter_MyTopics_frag;
import com.example.rtclient.Dashboard.FiltersActivity;
import com.example.rtclient.ModelClasses.FeedData;
import com.example.rtclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class MyTopics extends Fragment {

    private RecyclerView recyclerView ;
    private TextView emptyTv ;
    private List<FeedData> dataList ;
    private ImageView filterBtn ;
    ///
    private FirebaseAuth auth ;
    private DatabaseReference reference ;
    private ValueEventListener listener ;
    private String userUid ;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_mytopics_fragment, null);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        auth = FirebaseAuth.getInstance();


        initilize(rootView);
        /*..............................*/
        return rootView ;
    }

    public void initilize(View view){
        recyclerView = view.findViewById(R.id.recyclerview_id_mytopics_frag);
        emptyTv = view.findViewById(R.id.empty_tv_mytopics_frag);
        filterBtn = view.findViewById(R.id.filter_btn_mytopics_frag);

        dataList = new ArrayList<>();
        //Display
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        /*......*/

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FiltersActivity.class);
                intent.putExtra("activity", "MyTopics");
                startActivity(intent);
            }
        });

    }

    /*.......OnStart Method Loads all database from Firebase.......*/

    @Override
    public void onStart() {
        super.onStart();
        // data is found
        final ProgressDialog getDialog = new ProgressDialog(getActivity());
        getDialog.setMessage("Loading Please wait...");
//        getDialog.show();

        if (auth.getCurrentUser()!=null) {
            userUid = auth.getCurrentUser().getUid();
        }

        reference = FirebaseDatabase.getInstance().getReference("newsFeed");
//        reference = FirebaseDatabase.getInstance().getReference("feeddetails");
        try {
            listener =  reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FeedData feedData = snapshot.getValue(FeedData.class);
                        dataList.add(feedData);
                    }
                    if (dataList.isEmpty()){
                        getDialog.dismiss();
                        recyclerView.setVisibility(View.INVISIBLE);
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    else {
                        getDialog.dismiss();
                        recyclerView.setVisibility(View.VISIBLE);
                        emptyTv.setVisibility(View.INVISIBLE);
                        RecAdapter_MyTopics_frag adapter = new RecAdapter_MyTopics_frag(getActivity(), dataList);
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }});
        } catch (Exception e) { e.printStackTrace(); }
        /*...............................................*/


    }



}

