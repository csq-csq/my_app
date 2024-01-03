package com.example.my_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

public class MediaPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        playerView = findViewById(R.id.videoView);
        Intent intent = getIntent();
        Log.d("MediaPlayerActivity", "Received intent: " + intent);
        Log.d("MediaPlayerActivity", "Intent extras: " + intent.getExtras());
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        String video_src=getIntent().getStringExtra("video_src");
        Log.d("MediaPlayerActivity", "Received video_src: " +video_src );
        Uri videoUri = Uri.parse(getIntent().getStringExtra("src"));

        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            player.prepare();
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            player.prepare();
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            player.setPlayWhenReady(false);
        }
    }
}

