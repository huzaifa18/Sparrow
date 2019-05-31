package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Adapter.StatusPostAdapter;
import com.example.bracketsol.sparrow.Adapter.StoryAdapter;
import com.example.bracketsol.sparrow.Classes.BottomNavigationViewHelper;
import com.example.bracketsol.sparrow.DisFragment;
import com.example.bracketsol.sparrow.Fragments.NotificationFragment;
import com.example.bracketsol.sparrow.Fragments.PrrofileFragment;
import com.example.bracketsol.sparrow.MessageActivity.ChatsListingMain;
import com.example.bracketsol.sparrow.MessageActivity.MessagingService;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.Model.StoryModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.SocialLife.ModelSocial;
import com.example.bracketsol.sparrow.SocialLife.SocialLifeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bracketsol.sparrow.MyApp.getContext;

public class HomeActivity extends AppCompatActivity {


    private static FragmentManager fragmentManager;
    RecyclerView storyRecyclerview, statuspostRecyclerview;
    ArrayList<StoryModel> storyArraylist;
    ArrayList<StatusPostingModel> statusArraylist;
    StoryAdapter storyAdapter;
    StatusPostAdapter statusPostAdapter;
    LinearLayoutManager manager;
    ImageButton chatbtn;
    MessagingService messagingService;
    ProgressBar simpleProgressBar;
    ApiInterface apiInterface;
    Call<ResponseBody> getSocialCall;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_account:
                    return true;
                case R.id.navigation_discussion:
                    fragmentTransaction
                            //.beginTransaction()
                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                            .replace(R.id.frame_container, new DisFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
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
        init();
        getStoryData();
        getPostsData();
        GoToChat();
    }

    private void init() {
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
        storyAdapter = new StoryAdapter(this, storyArraylist);
        statusPostAdapter = new StatusPostAdapter(this, statusArraylist);
        storyRecyclerview.setAdapter(storyAdapter);
        manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        storyRecyclerview.setLayoutManager(manager);
        statuspostRecyclerview.setAdapter(statusPostAdapter);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        statuspostRecyclerview.setLayoutManager(manager);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    }

    private void getStoryData() {
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

    }

    private void getPostsData() {
        getSocialCall = apiInterface.getAllPosts();
        getSocialCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    Log.e("TAG", "ok");
                    JSONArray array = resJson.getJSONArray("posts");
                    Log.e("TAG", "ok");


                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        Log.e("TAG", "post_id" + product.getInt("post_id"));
                        Log.e("TAG", "sender_id" + product.getInt("sender_id"));
                        Log.e("TAG", "sender_name" + product.getString("sender_name"));
                        Log.e("TAG", "sender_pic" + product.getString("sender_pic"));
                        Log.e("TAG", "content" + product.getString("content"));
                        Log.e("TAG", "background" + product.getString("background"));
                        Log.e("TAG", "type" + product.getString("type"));
                        Log.e("TAG", "attachment" + product.getString("attachment"));
                        Log.e("TAG", "total_likes" + product.getInt("total_likes"));
                        Log.e("TAG", "total_comments" + product.getInt("total_comments"));
                        Log.e("TAG", "total_views" + product.getInt("total_views"));
                        Log.e("TAG", "created_at" + product.getString("created_at"));


                        int post_id = product.getInt("post_id");
                        int sender_id = product.getInt("sender_id");
                        String sender_name = product.getString("sender_name");
                        String sender_pic = product.getString("sender_pic");
                        String content = product.getString("content");
                        String background = product.getString("background");
                        String type = product.getString("type");
                        String attachment = product.getString("attachment");
                        int total_likes = product.getInt("total_likes");
                        int total_comments = product.getInt("total_comments");
                        int total_views= product.getInt("total_views");
                        String created_at = product.getString("created_at");
                        //simpleProgressBar.setVisibility(View.GONE);
                        Log.i("url", "https://s3.amazonaws.com/social-funda-bucket/" + attachment);
                        StatusPostingModel statusPostingModel = new StatusPostingModel(sender_name, sender_pic,content,attachment,total_likes,
                                total_comments,total_views);

//                        simpleProgressBar.setVisibility(View.GONE);
                        statusArraylist.add(statusPostingModel);
                    }
                    statuspostRecyclerview.setAdapter(statusPostAdapter);
                    statuspostRecyclerview.scrollToPosition(statusArraylist.size());
                    statusPostAdapter.notifyDataSetChanged();
                    manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
                    // mLayoutManager.setReverseLayout(true);
                    //mLayoutManager.setStackFromEnd(true);
                    statuspostRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    statuspostRecyclerview.setLayoutManager(manager);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("TAG", "checkval " + e.getMessage());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "checkval onresponse" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
//
//        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp,
//                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
//                R.drawable.ic_bookmark, "Ahmad", "lahore"));
//        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_target, R.drawable.ic_more_horiz_black_24dp,
//                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
//                R.drawable.ic_bookmark, "Ahmad", "lahore"));
//        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_man, R.drawable.ic_more_horiz_black_24dp,
//                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
//                R.drawable.ic_bookmark, "Ahmad", "lahore"));
//        statusArraylist.add(new StatusPostingModel(R.drawable.demo, R.drawable.ic_man3, R.drawable.ic_more_horiz_black_24dp,
//                R.drawable.ic_dislike, R.drawable.ic_comment, R.drawable.ic_senddd,
//                R.drawable.ic_bookmark, "Ahmad", "lahore"));
//        statusPostAdapter.notifyDataSetChanged();
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
        NotificationFragment notificationFragment = new NotificationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
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
        super.onResume();
    }


    public void GoToChat() {
        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ChatsListingMain.class);
                startActivity(intent);
            }
        });

    }

}
