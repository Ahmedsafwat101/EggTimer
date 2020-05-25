package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView myrextview;
    Button controllerButton;
    boolean Clicked=false;
    CountDownTimer countDown;
    ImageView imageView;


    public void timeLeft(int progress)
    {
        int minutes= (int)progress/60 ;
        int seconds= progress-minutes*60;
        String Secondview=Integer.toString(seconds);
        if(seconds==0)
        {
            Secondview+="0";
        }
        else if(seconds<10)
        {
            Secondview="0"+Secondview;
        }
        myrextview.setText(Integer.toString(minutes)+ ":" +Secondview);
    }

    public void ResetState()
    {
        Clicked=false;
        timerSeekbar.setEnabled(true);
        timerSeekbar.setProgress(0);
        controllerButton.setText("GO!");
        myrextview.setText("0.00");
        countDown.cancel();

    }

    public  void controlTimer(View view)
    {
        if(Clicked==false) {
          Clicked=true;
          timerSeekbar.setEnabled(false);
          controllerButton.setText("Stop!");

          countDown=new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
              @Override
              public void onTick(long millisUntilFinished) {
                  timeLeft((int) millisUntilFinished / 1000);
              }

              @Override
              public void onFinish() {
                  Log.i("hi", "finished");
                  MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                  mediaPlayer.start();
                   ResetState();

              }
          }.start();
        }
        else
        {
            ResetState();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekbar= (SeekBar) findViewById(R.id.seekBar);
        myrextview= (TextView) findViewById(R.id.textView);
        controllerButton=(Button)findViewById(R.id.button);
        imageView=(ImageView)findViewById(R.id.imageView);
        timerSeekbar.setMax(10*60); // 10 minutes
        timerSeekbar.setProgress(0); //0 seconds
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeLeft(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
