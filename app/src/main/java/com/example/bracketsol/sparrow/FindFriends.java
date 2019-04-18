package com.example.bracketsol.sparrow;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Model.Adapter.FindFriendAdapter;
import com.example.bracketsol.sparrow.Model.FindFriendModel;

import java.util.ArrayList;

public class FindFriends extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTextView_next;
    RecyclerView recyclerView;
    ArrayList<FindFriendModel> resarrayList;
    FindFriendAdapter resadapter;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        toolbarTextView_next = findViewById(R.id.next_textview);
        toolbarTextView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(FindFriends.this, HomeActivity.class);
                startActivity(mainIntent);
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new AsyncCaller().execute();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView = findViewById(R.id.find_recycler_view);
            resarrayList = new ArrayList<FindFriendModel>();
            resadapter = new FindFriendAdapter(FindFriends.this, resarrayList);

        }

        @Override
        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(resadapter);
                    manager = new LinearLayoutManager(FindFriends.this, LinearLayoutManager.VERTICAL, true);
                    recyclerView.setLayoutManager(manager);

                    resarrayList.add(new FindFriendModel(R.drawable.ic_icons8_administrator_male, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Ali haider", "98 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_man, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Usman Anwar", "54 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_man3, R.drawable.frndship_btn_selector, R.drawable.final_selector, "kainaat gul", "1 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_girl, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Rizwan mughal", "2 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_target, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Javed ali", "9 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_team, R.drawable.frndship_btn_selector, R.drawable.final_selector, "haseeb imtiaz", "6 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_seo, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Wiliayat jutt", "5 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_girl, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Aftab hussain", "22 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_target, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Sadaf noreen", "12 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_icons8_administrator_male, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Iqbal jutt", "0 mutual friend"));
                    resarrayList.add(new FindFriendModel(R.drawable.ic_man3, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Memoona iqbal", "23 mutual friend"));
                }
            });
            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
        }
    }
}
