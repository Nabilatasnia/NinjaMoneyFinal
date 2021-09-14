package com.example.ninjamoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.ninjamoney.LoginSignUp.StartScreen;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingMenu extends AppCompatActivity {

    ProgressBar progressBar;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_menu);
        loading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingMenu.this, StartScreen.class));
                finish();
            }
        },1000);
    }

    public void loading() {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask(){
            @Override
            public void run(){
                counter++;
                progressBar.setProgress(counter);
                if(counter == 100)
                    t.cancel();
            }

        };
        t.schedule(tt, 0,10);
    }

}