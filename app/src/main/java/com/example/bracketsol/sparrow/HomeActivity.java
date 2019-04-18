package com.example.bracketsol.sparrow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.bracketsol.sparrow.Model.Adapter.StatusPostAdapter;
import com.example.bracketsol.sparrow.Model.Adapter.StoryAdapter;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.Model.StoryModel;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {


    RecyclerView storyRecyclerview,statuspostRecyclerview;
    ArrayList<StoryModel> storyArraylist;
    ArrayList<StatusPostingModel> statusArraylist;

    StoryAdapter storyAdapter;
    StatusPostAdapter statusPostAdapter;
    LinearLayoutManager manager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.search:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        storyRecyclerview = findViewById(R.id.story_recyclerview);
        statuspostRecyclerview = findViewById(R.id.status_recyclerview);
        storyArraylist = new ArrayList<StoryModel>();
        statusArraylist = new ArrayList<StatusPostingModel>();
//
//        Collections.reverse(storyArraylist);

        storyAdapter = new StoryAdapter(this, storyArraylist);
        statusPostAdapter = new StatusPostAdapter(this,statusArraylist);

        storyRecyclerview.setAdapter(storyAdapter);
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        storyRecyclerview.setLayoutManager(manager);

        statuspostRecyclerview.setAdapter(statusPostAdapter);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true);
        statuspostRecyclerview.setLayoutManager(manager);

//        //manager.setReverseLayout(true);
//        manager.setStackFromEnd(true);


        storyArraylist.add(new StoryModel(R.drawable.ic_seo, "Your Story"));
        storyArraylist.add(new StoryModel(R.drawable.ic_icons8_administrator_male, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_man, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_man3, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_girl, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_target, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_team, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_man3, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_seo, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_target, "hassan ali"));
        storyArraylist.add(new StoryModel(R.drawable.ic_girl, "Maria"));

        Collections.reverse(storyArraylist);
        storyAdapter.notifyDataSetChanged();


        statusArraylist.add(new StatusPostingModel(R.drawable.demo,R.drawable.ic_girl,R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike,R.drawable.ic_comment,R.drawable.ic_senddd,
                R.drawable.ic_bookmark,"Ahmad","lahore"));
        statusArraylist.add(new StatusPostingModel(R.drawable.demo,R.drawable.ic_target,R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike,R.drawable.ic_comment,R.drawable.ic_senddd,
                R.drawable.ic_bookmark,"Ahmad","lahore"));
        statusArraylist.add(new StatusPostingModel(R.drawable.demo,R.drawable.ic_man,R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike,R.drawable.ic_comment,R.drawable.ic_senddd,
                R.drawable.ic_bookmark,"Ahmad","lahore"));
        statusArraylist.add(new StatusPostingModel(R.drawable.demo,R.drawable.ic_man3,R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike,R.drawable.ic_comment,R.drawable.ic_senddd,
                R.drawable.ic_bookmark,"Ahmad","lahore"));
        statusPostAdapter.notifyDataSetChanged();


        //
        //


    }

    public void addList(ArrayList<StoryModel> items) {
        storyArraylist.addAll(0, items);

        storyRecyclerview.smoothScrollToPosition(0);
        storyAdapter.notifyDataSetChanged();

    }

}
