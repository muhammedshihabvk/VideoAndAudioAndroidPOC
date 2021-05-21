package com.shabsudemy.videoaudiosample;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    Button button;
    MediaPlayer mediaPlayer;
    SeekBar seekBarVolume,seekBarDuration;
    TextView durationText;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView);
        button = (Button) findViewById(R.id.stopButton);
        MediaController mediaController = new MediaController(this);
        seekBarVolume = (SeekBar) findViewById(R.id.volumeSeekBar);
        seekBarDuration = (SeekBar) findViewById(R.id.musicDurationSeekBar);
        durationText = (TextView) findViewById(R.id.musicDurationText);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.demovideo);
//        videoView.start();

        mediaPlayer = MediaPlayer.create(this, R.raw.audiobg);
        mediaPlayer.start();

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //take maximum music valume
        Log.i("player", String.valueOf(maxVolume));
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i("player", String.valueOf(maxVolume));


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBarDuration.setProgress(mediaPlayer.getCurrentPosition(),true);
            }
        },0,500);


        //set maximum value of seekbar as volume
        seekBarVolume.setMax(maxVolume);
        seekBarVolume.setProgress(curVolume);

        seekBarDuration.setMax(mediaPlayer.getDuration());


        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("player", String.valueOf(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("player", "touchked");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("player", "untouched");
            }
        });


        seekBarDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                        mediaPlayer.start();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    button.setText("START AUDIO");
                    Log.i("player", mediaPlayer.toString());
                } else {
                    Log.i("player", mediaPlayer.toString());
//                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.audiobg);
                    mediaPlayer.start();
                    button.setText("PAUSE AUDIO");
                }
            }

        });




    }
}