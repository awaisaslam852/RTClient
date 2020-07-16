package com.example.rtclient.Adapters;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.rtclient.Dashboard.ViewPost;
import com.example.rtclient.Helpers.PostLastTimeAgo;
import com.example.rtclient.ModelClasses.FeedData;
import com.example.rtclient.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class RecAdapter_Feed_frag extends RecyclerView.Adapter<RecAdapter_Feed_frag.ViewHolder> {

    /*...........RecyclerView Adapter For Fragment5 Menu items list Waiter.............*/

    private Activity mContext;
    private List<FeedData> mUsers;
    ///
    private FirebaseAuth auth ;


    public RecAdapter_Feed_frag(Activity mContext, List<FeedData> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public RecAdapter_Feed_frag.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_feed_fragment, parent, false);
        return new RecAdapter_Feed_frag.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter_Feed_frag.ViewHolder holder, int position) {
        final FeedData data = mUsers.get(position);
        auth = FirebaseAuth.getInstance();

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





    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1_title, tv2_subtitle, tv3_country, tv4_state, tv5_time ;
        ImageView imageview, videoIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1_title    = itemView.findViewById(R.id.tv1_title_recycler_item_feed_frag);
            tv2_subtitle = itemView.findViewById(R.id.tv2_subtitle_recycler_item_feed_frag);
            tv3_country  = itemView.findViewById(R.id.tv3_country_recycler_item_feed_frag);
            tv4_state    = itemView.findViewById(R.id.tv4_state_recycler_item_feed_frag);
            tv5_time     = itemView.findViewById(R.id.tv5_time_recycler_item_feed_frag);
            imageview    = itemView.findViewById(R.id.iv_recycler_item_feed_frag);
            videoIcon    = itemView.findViewById(R.id.play_btn_recycler_item_feed_frag);


        }
    }
}