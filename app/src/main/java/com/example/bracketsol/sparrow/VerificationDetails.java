package com.example.bracketsol.sparrow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class VerificationDetails extends AppCompatActivity {

    ImageButton nextButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_details);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        nextButton = findViewById(R.id.nextt_button);

    }

    public void click(View view) {
        view.getId();
        Intent intent = new Intent(VerificationDetails.this, SetProfilePicture.class);
        startActivity(intent);
    }
}
