package com.example.bracketsol.sparrow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class Welcome extends AppCompatActivity {
    Toolbar toolbar;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,SetProfilePicture.class);
                startActivity(intent);
            }
        });
    }
}
