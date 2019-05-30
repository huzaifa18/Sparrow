package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Adapter.StatusPostAdapter;
import com.example.bracketsol.sparrow.Adapter.StoryAdapter;
import com.example.bracketsol.sparrow.Classes.BottomNavigationViewHelper;
import com.example.bracketsol.sparrow.CreatePost;
import com.example.bracketsol.sparrow.DisFragment;
import com.example.bracketsol.sparrow.Fragments.NotificationFragment;
import com.example.bracketsol.sparrow.Fragments.PrrofileFragment;
import com.example.bracketsol.sparrow.MessageActivity.ChatsListingMain;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.Model.StoryModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.SocialLife.SocialLifeFragment;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {


    private static FragmentManager fragmentManager;
    RecyclerView storyRecyclerview, statuspostRecyclerview;
    ArrayList<StoryModel> storyArraylist;
    ArrayList<StatusPostingModel> statusArraylist;

    StoryAdapter storyAdapter;
    StatusPostAdapter statusPostAdapter;
    LinearLayoutManager manager;

    ImageButton chatbtn;

    FloatingActionButton fab;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ;
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    fragmentTransaction
//                            //.beginTransaction()
//                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                            .replace(R.id.frame_container, new SetFragment());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_account:
//                    fragmentTransaction
//                            //.beginTransaction()
//                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                            .replace(R.id.frame_container, new NotificationFragment());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();

//                    return true;

                    return true;
                case R.id.navigation_discussion:
                    fragmentTransaction
                            //.beginTransaction()
                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                            .replace(R.id.frame_container, new DisFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                    return true;
//                case R.id.navigation_notification:
//                    fragmentTransaction
//                            //.beginTransaction()
//                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                            .replace(R.id.frame_container, new DisFragment());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                    return true;
                case R.id.navigation_profile:
                    fragmentTransaction
                            //.beginTransaction()
                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                            .replace(R.id.frame_container, new SocialLifeFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        storyRecyclerview = findViewById(R.id.story_recyclerview);
        statuspostRecyclerview = findViewById(R.id.status_recyclerview);

        chatbtn = findViewById(R.id.chat_ib);
        storyArraylist = new ArrayList<StoryModel>();
        statusArraylist = new ArrayList<StatusPostingModel>();
//
//        Collections.reverse(storyArraylist);

        storyAdapter = new StoryAdapter(this, storyArraylist);
        statusPostAdapter = new StatusPostAdapter(this, statusArraylist);

        storyRecyclerview.setAdapter(storyAdapter);
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        storyRecyclerview.setLayoutManager(manager);

        statuspostRecyclerview.setAdapter(statusPostAdapter);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
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


        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
                R.drawable.ic_bookmark, "Ahmad", "lahore"));
        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_target, R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
                R.drawable.ic_bookmark, "Ahmad", "lahore"));
        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_man, R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
                R.drawable.ic_bookmark, "Ahmad", "lahore"));
        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_man3, R.drawable.ic_more_horiz_black_24dp,
                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
                R.drawable.ic_bookmark, "Ahmad", "lahore"));
        statusPostAdapter.notifyDataSetChanged();




        //
        //
        GoToChat();

        init();

    }

    private void init() {

        fab = findViewById(R.id.fab);

        setOnClicklisteners();
    }

    private void setOnClicklisteners() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreatePost.class));
            }
        });

    }

    public void showToolbar(){
        View layout;
        layout = findViewById(R.id.home_toolbar);
        layout.setVisibility(View.VISIBLE);

    }
    public void hideToolbar(){
        View layout;
        layout = findViewById(R.id.home_toolbar);
        layout.setVisibility(View.GONE);

    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment

        NotificationFragment notificationFragment = new NotificationFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    //.remove(fragment.)
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack("a")
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
        showToolbar();
        super.onResume();
    }

    public void GoToChat() {

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Intent intent = new Intent(HomeActivity.this, ChatsListingMain.class);
                startActivity(intent);
//                Intent intent = new Intent(HomeActivity.this, ChatsListingMain.class);
//                startActivity(intent);
            }
        });

    }

}
