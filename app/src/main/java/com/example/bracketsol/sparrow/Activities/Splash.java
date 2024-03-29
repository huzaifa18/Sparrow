package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.bracketsol.sparrow.R;

public class Splash extends AppCompatActivity {

    ProgressBar mprogressBar;
    public final  int SPLASH_LENGTH = 600;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mprogressBar = new ProgressBar(this);
        mprogressBar.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                sharedPreferences = getSharedPreferences("betaar_user", 0);
                String value = sharedPreferences.getString("username",null);
                if(value == null){
                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Intent mainIntent = new Intent(Splash.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_LENGTH);

    }

}