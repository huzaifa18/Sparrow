package com.example.bracketsol.sparrow.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.bracketsol.sparrow.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ContactUs extends AppCompatActivity {

    CardView cv_version,cv_author,cv_description;

    ExpandableLayout el_version,el_author,el_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        init();
    }

    private void init() {

        cv_version = findViewById(R.id.cv_version);
        cv_author = findViewById(R.id.cv_author);
        cv_description = findViewById(R.id.cv_description);

        el_version = findViewById(R.id.el_version);
        el_author = findViewById(R.id.el_author);
        el_description = findViewById(R.id.el_description);

        clickListeners();

    }

    private void clickListeners() {

        cv_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                el_version.toggle();

            }
        });

        cv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                el_author.toggle();

            }
        });

        cv_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                el_description.toggle();

            }
        });

    }

}