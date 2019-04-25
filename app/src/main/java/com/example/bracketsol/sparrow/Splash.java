package com.example.bracketsol.sparrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    ProgressBar mprogressBar;
    public final  int SPLASH_LENGTH = 600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mprogressBar = new ProgressBar(this);
        mprogressBar.setBackgroundColor(getResources().getColor(R.color.colorwhite));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,Login.class);
               startActivity(mainIntent);
                finish();
            }
        }, SPLASH_LENGTH);

    }
}
