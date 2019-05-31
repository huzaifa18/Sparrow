package com.example.bracketsol.sparrow.MessageActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Adapter.RecentChatsListingAdapter;
import com.example.bracketsol.sparrow.Model.RecentChatModel;
import com.example.bracketsol.sparrow.MyApp;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.Utils.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsListingMain extends AppCompatActivity {

    RecyclerView rv_main;
    RecentChatsListingAdapter chatAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<RecentChatModel> list;
    ArrayList<MessageListModel> list2;
    GetAllMessageAdapter resadapter;
    ImageView iv_search_btn;
    ImageView iv_options_btn;
    ImageButton back_btn;
    Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    ApiInterface apiInterface;
    Call<ResponseBody> call;
    ProgressBar simpleProgressBar;
    FrameLayout frameLayout;
    RelativeLayout nomessagelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        Listeners();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetAllMessage();
        //LoadData();
    }

    private void initialize() {
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        frameLayout = findViewById(R.id.frame);
        toolbar = findViewById(R.id.toolbar);
        back_btn = findViewById(R.id.back_btn);
        recyclerView = (RecyclerView) findViewById(R.id.recent_chat_recyclerview);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        iv_search_btn = (ImageView) findViewById(R.id.search_btn);
        iv_options_btn = (ImageView) findViewById(R.id.options_btn);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        list2 = new ArrayList<MessageListModel>();
        resadapter = new GetAllMessageAdapter(ChatsListingMain.this, list2);
        call = apiInterface.getAllMessage();
        nomessagelayout = findViewById(R.id.no_messages_yet);
        //GetAll();

    }

    private void GetAllMessage() {

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    Log.e("TAG", "ok");


                    JSONArray array = resJson.getJSONArray("messages");
                    Log.e("TAG", "ok");

                    if(array.length()==0){
                        nomessagelayout.setVisibility(View.VISIBLE);
                        Toast.makeText(ChatsListingMain.this, "No message", Toast.LENGTH_SHORT).show();
                        simpleProgressBar.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        Log.e("GAT", "sender_id" + product.getInt("sender_id"));
                        Log.e("GAT", "receiver_id" + product.getInt("receiver_id"));
                        Log.e("GAT", "receiver_id preferece" + Prefs.getUserIDFromPref(MyApp.getContext()));
                        Log.e("TAG", "sender_name" + product.getString("sender_name"));
                        Log.e("TAG", "receiver_name" + product.getString("receiver_name"));
                        Log.e("TAG", "content" + product.getString("content"));
                        Log.e("TAG", "mess" + product.getString("sender_profile"));
                        Log.e("TAG", "mess" + product.getString("receiver_profile"));
                        Log.e("TAG", "mess" + product.getString("created_at"));

                        int sender_id = product.getInt("sender_id");
                        int receiver_id = product.getInt("receiver_id");
                        String sender_name = product.getString("sender_name");
                        String receiver_name = product.getString("receiver_name");
                        String content = product.getString("content");
                        String sender_profile = product.getString("sender_profile");
                        String receiver_profile = product.getString("receiver_profile");
                        String created_at = product.getString("created_at");

                        int getuserid = Prefs.getUserIDFromPref(ChatsListingMain.this);
                        Log.i("userid",""+getuserid);

                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                        SimpleDateFormat destFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a"); //here 'a' for AM/PM


                        Date date = null;
                        try {
                            date = sourceFormat.parse(created_at);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDate = destFormat.format(date);

                        //Toast.makeText(ChatsListingMain.this, formattedDate, Toast.LENGTH_SHORT).show();

                        String name =null;
                        simpleProgressBar.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.GONE);
                        MessageListModel messageListModel = null;
//                        (getuserid==sender_id)? receiver_name : sender_name,

                        if(Prefs.getUserIDFromPref(MyApp.getContext())==sender_id){
                            messageListModel = new MessageListModel(R.drawable.ic_seo,
                                    sender_id,
                                    receiver_id,
                                    receiver_name,
                                    content,
                                    formattedDate);
                        }else if(Prefs.getUserIDFromPref(MyApp.getContext())==receiver_id) {
                            messageListModel = new MessageListModel(R.drawable.ic_seo,
                                    receiver_id,
                                    sender_id,
                                    sender_name,
                                    content,
                                    formattedDate);
                        }


                        Log.e("idchat","senderid"+Prefs.getUserIDFromPref(MyApp.getContext()));
                        Log.e("idchat","receiverid"+receiver_id);

                        if(messageListModel!=null){
                            list2.add(messageListModel);
                            //Toast.makeText(ChatsListingMain.this, "not null", Toast.LENGTH_SHORT).show();
                        }



                        //resarrayList.add(new FindFriendModel(R.drawable.ic_seo, R.drawable.frndship_btn_selector, R.drawable.frndship_btn_selector, product.getString("username"), product.getString("email")));
                    }

                    recyclerView.setAdapter(resadapter);
                    resadapter.notifyDataSetChanged();
                    manager = new LinearLayoutManager(ChatsListingMain.this, LinearLayoutManager.VERTICAL, true);
                    recyclerView.setLayoutManager(manager);

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
                Log.e("TAG", "error" + t.getMessage());
                
            }
        });
    }

    private void Listeners() {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void LoadData() {
        rv_main.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //list.add(new RecentChatModel("Image Path", "Name", "Text", "19/04/2019"));
        }
        list.add(new RecentChatModel(R.drawable.ic_icons8_administrator_male, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Ali haider", "Hi How are you?"));
        list.add(new RecentChatModel(R.drawable.ic_man, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Usman Anwar", "I am great"));
        list.add(new RecentChatModel(R.drawable.ic_man3, R.drawable.frndship_btn_selector, R.drawable.final_selector, "kainaat gul", "Whats up?"));
        list.add(new RecentChatModel(R.drawable.ic_girl, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Rizwan mughal", "Lol"));
        list.add(new RecentChatModel(R.drawable.ic_target, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Javed ali", "Hey there!"));
        list.add(new RecentChatModel(R.drawable.ic_team, R.drawable.frndship_btn_selector, R.drawable.final_selector, "haseeb imtiaz", "hello!"));
        list.add(new RecentChatModel(R.drawable.ic_seo, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Wiliayat jutt", "Hi!"));
        list.add(new RecentChatModel(R.drawable.ic_girl, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Aftab hussain", "Lets go"));
        list.add(new RecentChatModel(R.drawable.ic_target, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Sadaf noreen", "busy right now"));
        list.add(new RecentChatModel(R.drawable.ic_icons8_administrator_male, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Iqbal jutt", "hi"));
        list.add(new RecentChatModel(R.drawable.ic_man3, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Memoona iqbal", "Hey There!"));

        chatAdapter = new RecentChatsListingAdapter(this, list);
        rv_main.setAdapter(chatAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}
