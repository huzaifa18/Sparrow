package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bracketsol.sparrow.R;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class SetProfilePicture extends AppCompatActivity {

    Button nextButtonn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profile_pic);

        nextButtonn = findViewById(R.id.skip_prifile_picture);
        nextButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("checkk","setprofile");
                Intent intent = new Intent(SetProfilePicture.this, FindFriends.class);
                startActivity(intent);
            }
        });
    }
}
