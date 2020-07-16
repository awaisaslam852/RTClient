package com.example.rtclient.Dashboard;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.rtclient.Helpers.PostLastTimeAgo;
import com.example.rtclient.R;
public class ViewPost extends AppCompatActivity {

    private ImageView iv_cover, backBtn, play_video_btn ;
    private TextView tv1_title, tv2_descrpt, tv3_country, tv4_state, tv5_time ;
    private VideoView videoView;
    private Uri vedioUri;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);


        iv_cover    = findViewById(R.id.iv_view_post);
        backBtn     = findViewById(R.id.back_btn_view_post);
        videoView   = findViewById(R.id.vv_view_post);
        tv1_title   = findViewById(R.id.tv1_title_view_post);
        tv2_descrpt = findViewById(R.id.tv2_subtitle_view_post);
        tv3_country = findViewById(R.id.tv3_country_title_view_post);
        tv4_state   = findViewById(R.id.tv4_state_view_post);
        tv5_time    = findViewById(R.id.tv5_time_view_post);
        play_video_btn   = findViewById(R.id.play_btn_view_post);

        int postId = getIntent().getIntExtra("postId",0);
        long time  = getIntent().getLongExtra("time", 0);
        String title     = getIntent().getStringExtra("title");
        String descrip   = getIntent().getStringExtra("description");
        String country   = getIntent().getStringExtra("country");
        String state     = getIntent().getStringExtra("state");
        String mediaType = getIntent().getStringExtra("mediaType");
        final String mediaUrl  = getIntent().getStringExtra("mediaLink");

        /*...Setting Data...*/
        if (title!=null){
            tv1_title.setText(title);
        }
        if (descrip!=null){
            tv2_descrpt.setText(descrip);
        }
        if (country!=null){
            tv3_country.setText(country);
        }
        if (state!=null){
            tv4_state.setText(state);
        }
        /*.......Get Time......*/
        String postTimeAgo = PostLastTimeAgo.getTimeAgo(time, this);
        tv5_time.setText(postTimeAgo);

        if (mediaType!=null) {
            if (mediaType.equals("image")) {
                Glide.with(this).load(mediaUrl).placeholder(R.drawable.z2).into(iv_cover);
                iv_cover.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                play_video_btn.setVisibility(View.GONE);
            } else {
                iv_cover.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                play_video_btn.setVisibility(View.VISIBLE);
                Glide.with(this).load(mediaUrl).placeholder(R.drawable.z2).into(iv_cover);

                iv_cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialogVideo = new Dialog(ViewPost.this);
                        dialogVideo.setContentView(R.layout.play_video_dialog_view_post);
//                        dialogVideo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                        dialogVideo.getWindow().setBackgroundDrawableResource(R.color.blackColor);
                        dialogVideo.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT);

                        final VideoView vv_dialog = dialogVideo.findViewById(R.id.vv_dialog_view_post);
                        final ProgressBar progressBar    = dialogVideo.findViewById(R.id.progress_bar_vv_view_post);
                        final ImageView playBtn_dialog   = dialogVideo.findViewById(R.id.play_btn_vv_view_post);
                        final ImageView pauseBtn_dialog  = dialogVideo.findViewById(R.id.pause_btn_vv_view_post);
                        final ImageView closedialogBtn   = dialogVideo.findViewById(R.id.close_dialog_btn_dialog_view_post);
                        final SeekBar seekbar_dialog     = dialogVideo.findViewById(R.id.seekbar_dialog_view_post);

                        vedioUri= Uri.parse(mediaUrl);
                        vv_dialog.setVideoURI(vedioUri);
                        vv_dialog.requestFocus();
                        mediaController=new MediaController( ViewPost.this);

                        if (vedioUri!=null) {
                            playBtn_dialog.setVisibility(View.GONE);
                            vv_dialog.start();
                        }
                        vv_dialog.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.setOnVideoSizeChangedListener( new MediaPlayer.OnVideoSizeChangedListener() {
                                    @Override
                                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
                                        if (mediaPlayer.isPlaying()){
                                            progressBar.setVisibility(View.GONE);
                                            pauseBtn_dialog.setVisibility(View.GONE);
                                            playBtn_dialog.setVisibility(View.GONE);

                                            vv_dialog.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (vv_dialog.isPlaying()){
                                                        pauseBtn_dialog.setVisibility(View.VISIBLE);
                                                        playBtn_dialog.setVisibility(View.GONE);
                                                    }
                                                    /*.......*/
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                pauseBtn_dialog.setVisibility(View.GONE);
                                                            }
                                                        }, 1500);
                                                }
                                            });
                                        }

                                        /*.......*/
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                pauseBtn_dialog.setVisibility(View.GONE);
                                            }
                                        },1500);
                                    }
                                } );
                            }
                        } );

                        vv_dialog.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                playBtn_dialog.setVisibility(View.VISIBLE);
                                pauseBtn_dialog.setVisibility(View.GONE);
                                closedialogBtn.setVisibility(View.VISIBLE);
                            }
                        });

                        playBtn_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (vedioUri!=null) {
                                    playBtn_dialog.setVisibility(View.GONE);
                                    pauseBtn_dialog.setVisibility(View.GONE);
                                    vv_dialog.start();
                                }
                            }
                        });
                        /*....*/

                        /*.....*/
                        pauseBtn_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pauseBtn_dialog.setVisibility(View.GONE);
                                playBtn_dialog.setVisibility(View.VISIBLE);
                                vv_dialog.pause();
                            }
                        });


                        /*....*/
                        closedialogBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogVideo.dismiss();
                            }
                        });

                        /*........*/
                        dialogVideo.show();

                    }
                });
            /*......*/


            }
        }////////

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
