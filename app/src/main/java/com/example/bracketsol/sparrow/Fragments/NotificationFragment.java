package com.example.bracketsol.sparrow.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.Adapter.NotificationAdapter;
import com.example.bracketsol.sparrow.Model.NotificationModel;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    private static View view;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    TextView toolbarTextView_next;
    RecyclerView recyclerView;
    ArrayList<NotificationModel> notificationModelArrayList;
    NotificationAdapter notificationAdapter;
    LinearLayoutManager manager;
    Context context;
    ApiInterface apiInterface;
    Call<ResponseBody> getNotiCall;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout rl_friend_requests;
    int page, total_pages = 0;

    public NotificationFragment() {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = (View) inflater.inflate(R.layout.notification_fragment, container, false);
        init();
        return view;
    }

    private void init() {

        fragmentManager = getActivity().getSupportFragmentManager();
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        recyclerView = view.findViewById(R.id.find_recycler_view_noti);

        rl_friend_requests = view.findViewById(R.id.rl_friend_requests);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        notificationModelArrayList = new ArrayList<NotificationModel>();
        notificationAdapter = new NotificationAdapter(getContext(), notificationModelArrayList);

        recyclerView.setAdapter(notificationAdapter);
        manager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);

        clickListeners();
        swipeListener();
        getAllNotifications();

    }

    private void clickListeners() {

        rl_friend_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(),"Recent Friend Requests!" , Toast.LENGTH_LONG).show();

            }
        });

    }

    private void swipeListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                //progressBar.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
                notificationModelArrayList.clear();
                page++;
                getAllNotifications();
            }
        });

    }

    private void getAllNotifications() {

        getNotiCall = apiInterface.getAllNoti(page);
        getNotiCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                try {
                    String resString = response.body().string();
                    Log.e("TAG", "Res: " + resString);
                    JSONObject resJson = new JSONObject(resString);
                    //total_pages = resJson.getInt("total_pages");
                    //has_next = resJson.getBoolean("has_next");
                    //Log.e("TAG", "total_pages " + total_pages);
                    JSONArray array = resJson.getJSONArray("notifications");
                    Log.e("TAG", "ok");


                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        Log.e("TAG", "_id" + product.getInt("_id"));
                        //Log.e("TAG", "receiver_id" + product.getInt("receiver_id"));
                        Log.e("TAG", "content" + product.getString("content"));
                        Log.e("TAG", "user_id" + product.getString("user_id"));
                        Log.e("TAG", "username" + product.getString("username"));
                        Log.e("TAG", "picture_url" + product.getString("picture_url"));
                        Log.e("TAG", "is_read" + product.getString("is_read"));
                        //Log.e("TAG", "date" + product.getString("date"));
                        Log.e("TAG", "reference_id" + product.getString("reference_id"));
                        Log.e("TAG", "type" + product.getString("type"));
                        Log.e("TAG", "created_at" + product.getString("created_at"));

                        int _id = product.getInt("_id");
                        int user_id = product.getInt("user_id");
                        String username = product.getString("username");
                        String picture_url = "https://s3.amazonaws.com/social-funda-bucket/" + product.getString("picture_url");
                        String content = product.getString("content");
                        String is_read = product.getString("is_read");
                        int reference_id = product.getInt("reference_id");
                        int type = product.getInt("type");
                        String created_at = product.getString("created_at");

                        notificationModelArrayList.add(new NotificationModel(_id,user_id,username,picture_url,content,is_read,reference_id,type,created_at));

                    }

                    notificationAdapter.notifyDataSetChanged();

                } catch (IOException e) {
                    //progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                    Log.e("TAG", "checkval " + e.getMessage());


                } catch (JSONException e) {
                    //progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                    Log.e("TAG", "checkval onresponse" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Failed To Retrieve Data", Toast.LENGTH_SHORT).show();
            }
        });

    }


}