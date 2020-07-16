package com.example.rtclient.Dashboard.Fragments;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rtclient.Adapters.RecAdapter_MyTopics_frag;
import com.example.rtclient.Adapters.RecAdapter_Saved_frag;
import com.example.rtclient.Dashboard.FiltersActivity;
import com.example.rtclient.ModelClasses.FeedData;
import com.example.rtclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class Saved extends Fragment {

    private RecyclerView recyclerView ;
    private TextView emptyTv, clearBtn ;
    private List<FeedData> dataList ;
    ///
    private FirebaseAuth auth ;
    private DatabaseReference reference ;
    private ValueEventListener listener ;
    private String userUid ;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_saved_fragment, null);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        auth = FirebaseAuth.getInstance();


        initilize(rootView);
        /*..............................*/
        return rootView ;
    }

    public void initilize(View view){
        recyclerView = view.findViewById(R.id.recyclerview_id_saved_frag);
        emptyTv = view.findViewById(R.id.empty_tv_saved_frag);
        clearBtn = view.findViewById(R.id.clear_btn_saved_frag);

        dataList = new ArrayList<>();
        //Display
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        /*......*/

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Alert ! Clearing all saved news list ");
                alertDialog.setMessage("Are you sure you want to remove all saved news ?");
                alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clients").child(userUid).child("Saved");
                                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext(), "Succeed! Saved list cleared", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getContext(), "Failed! may some network issue.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

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

//        reference = FirebaseDatabase.getInstance().getReference("newsFeed");
         reference = FirebaseDatabase.getInstance().getReference("Clients").child(userUid).child("Saved");
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
                        RecAdapter_Saved_frag adapter = new RecAdapter_Saved_frag(getActivity(), dataList);
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }});
        } catch (Exception e) { e.printStackTrace(); }
        /*...............................................*/


    }



}

