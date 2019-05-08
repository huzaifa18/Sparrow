package com.example.bracketsol.sparrow.SocketChat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.Prefs;

public class AddUserActivity extends AppCompatActivity {

    private Button setNickName;
    private TextView userNickName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_activity_add_user);

        userNickName = findViewById(R.id.userNickName);
        setNickName = findViewById(R.id.setNickName);
        Log.e("TAG", "" + Prefs.getUserIDFromPref(AddUserActivity.this));
        userNickName.setText("" + Prefs.getUserIDFromPref(AddUserActivity.this));
        setNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                intent.putExtra("username", userNickName.getText().toString());
                startActivity(intent);
            }
        });
    }
}
