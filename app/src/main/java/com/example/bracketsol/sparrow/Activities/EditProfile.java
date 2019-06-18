package com.example.bracketsol.sparrow.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bracketsol.sparrow.R;

public class EditProfile extends AppCompatActivity {

    Button bt_boy;
    Button bt_girl;
    Boolean gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
    }

    private void init() {

        gender = true;
        bt_boy = findViewById(R.id.bt_boy);
        bt_girl = findViewById(R.id.bt_girl);

        clickListeneres();
    }

    private void clickListeneres() {

        bt_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender){

                } else {
                    bt_boy.setBackground(getResources().getDrawable(R.drawable.btn_filled));
                    bt_boy.setTextColor(getResources().getColor(R.color.colorwhite));
                    bt_girl.setBackground(getResources().getDrawable(R.drawable.btn_hollow));
                    bt_girl.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                gender = true;
            }
        });

        bt_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender){
                    bt_boy.setBackground(getResources().getDrawable(R.drawable.btn_hollow));
                    bt_boy.setTextColor(getResources().getColor(R.color.colorPrimary));
                    bt_girl.setBackground(getResources().getDrawable(R.drawable.btn_filled));
                    bt_girl.setTextColor(getResources().getColor(R.color.colorwhite));
                } else {

                }
                 gender = false;
            }
        });

    }

}