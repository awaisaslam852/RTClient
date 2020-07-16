package com.example.rtclient.Adapters;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rtclient.Dashboard.ViewPost;
import com.example.rtclient.Helpers.PostLastTimeAgo;
import com.example.rtclient.ModelClasses.FeedData;
import com.example.rtclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
public class RecAdapter_MyTopics_frag extends RecyclerView.Adapter<RecAdapter_MyTopics_frag.ViewHolder> {

    /*...........RecyclerView Adapter For Fragment5 Menu items list Waiter.............*/

    private Activity mContext;
    private List<FeedData> mUsers;
    ///
    private FirebaseAuth auth ;
    private String userUid ;


    public RecAdapter_MyTopics_frag(Activity mContext, List<FeedData> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public RecAdapter_MyTopics_frag.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_mytopics_fragment, parent, false);
        return new RecAdapter_MyTopics_frag.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter_MyTopics_frag.ViewHolder holder, int position) {
        final FeedData data = mUsers.get(position);
        auth = FirebaseAuth.getInstance();
        userUid = auth.getCurrentUser().getUid();

        holder.tv1_title.setText(data.getTitle());
        holder.tv2_subtitle.setText(data.getNews_description());
        holder.tv3_country.setText(data.getCountry());
        holder.tv4_state.setText(data.getState());

        /*.......Get Time......*/
        String postTimeAgo = PostLastTimeAgo.getTimeAgo(data.getPosted_timestamp(), mContext);
        holder.tv5_time.setText(postTimeAgo);

        Glide.with(mContext).load(data.getShareLink()).placeholder(R.drawable.z2).into(holder.imageview);
        if (data.getMedia_type().equals("video")){
            holder.videoIcon.setVisibility(View.VISIBLE);
        }else {
            holder.videoIcon.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewPost.class);
                intent.putExtra("postId", data.getId());
                intent.putExtra("title", data.getTitle());
                intent.putExtra("description", data.getNews_description());
                intent.putExtra("country", data.getCountry());
                intent.putExtra("state", data.getState());
                intent.putExtra("time", data.getPosted_timestamp());
                intent.putExtra("mediaLink", data.getShareLink());
                intent.putExtra("mediaType", data.getMedia_type());
                mContext.startActivity(intent);

            }
        });

        /*....*/

        holder.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog getDialog = new ProgressDialog(mContext);
                getDialog.setMessage("Loading Please wait...");
                getDialog.setCancelable(false);
                getDialog.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clients").child(userUid).child("Saved");

                String pushId = reference.push().getKey();

                FeedData feedData = new FeedData(
                        data.getId(),
                        data.getPosted_timestamp(),
                        data.getCategory(),
                        data.getCountry(),
                        ""+pushId,
                        data.getInk_url(),
                        data.getLocation(),
                        data.getMedia_type(),
                        data.getNews_description(),
                        data.getShareLink(),
                        data.getState(),
                        data.getSubtitle(),
                        data.getTitle()
                );

                reference.child(pushId).setValue(feedData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            getDialog.dismiss();
                            Toast.makeText(mContext, "Succeed ! News has added to your Saved list", Toast.LENGTH_SHORT).show();
                        }else {
                            getDialog.dismiss();
                            Toast.makeText(mContext, "Failed ! may some network issue.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1_title, tv2_subtitle, tv3_country, tv4_state, tv5_time ;
        ImageView imageview, videoIcon, bookmarkBtn, shareBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1_title    = itemView.findViewById(R.id.tv1_title_recycler_item_mytopics_frag);
            tv2_subtitle = itemView.findViewById(R.id.tv2_subtitle_recycler_item_mytopics_frag);
            tv3_country  = itemView.findViewById(R.id.tv3_country_recycler_item_mytopics_frag);
            tv4_state    = itemView.findViewById(R.id.tv4_state_recycler_item_mytopics_frag);
            tv5_time     = itemView.findViewById(R.id.tv5_time_recycler_item_mytopics_frag);
            imageview    = itemView.findViewById(R.id.iv_recycler_item_mytopics_frag);
            videoIcon    = itemView.findViewById(R.id.play_btn_recycler_item_mytopics_frag);
            bookmarkBtn  = itemView.findViewById(R.id.btn_bookmark_recycler_item_mytopics_frag);
            shareBtn     = itemView.findViewById(R.id.btn_share_recycler_item_mytopics_frag);


        }
    }
}