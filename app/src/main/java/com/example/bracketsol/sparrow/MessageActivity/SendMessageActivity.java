package com.example.bracketsol.sparrow.MessageActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.Activities.FindFriends;
import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.Adapter.FindFriendAdapter;
import com.example.bracketsol.sparrow.Model.FindFriendModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Volley.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SendMessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FindFriendModel> resarrayList;
    FindFriendAdapter resadapter;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        recyclerView = findViewById(R.id.find_recycler_view);
        resarrayList = new ArrayList<FindFriendModel>();
        GetAll();
    }

    private void GetAll() {
        Log.e("TAG", "inside get all");
        // Tag used to cancel the request
        String cancel_req_tag = "register";
        //show pregress here
        StringRequest strReq = new StringRequest(Request.Method.GET, "https://social-funda.herokuapp.com/api/users/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Get all Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray array = jObj.getJSONArray("users");
                    //Log.e("TAG", "mess" +array.length());
                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        Log.e("TAG", "mess" + product.getInt("_id"));
                        Log.e("TAG", "mess" + product.getString("username"));
                        Log.e("TAG", "mess" + product.getString("email"));
                        Log.e("TAG", "mess" + product.getString("password"));

                        resarrayList.add(new FindFriendModel(R.drawable.ic_seo, product.getString("username"), product.getString("email")));
                        //resarrayList.add(new FindFriendModel(R.drawable.ic_seo, R.drawable.frndship_btn_selector, R.drawable.frndship_btn_selector, product.getString("username"), product.getString("email")));
                    }
                    resadapter = new FindFriendAdapter(SendMessageActivity.this, resarrayList);
                    recyclerView.setAdapter(resadapter);
                    manager = new LinearLayoutManager(SendMessageActivity.this, LinearLayoutManager.VERTICAL, true);
                    ItemTouchHelper itemTouchHelper = new
                            ItemTouchHelper(new FindFriendAdapter.SwipeToDeleteCallback(resadapter));
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    recyclerView.setLayoutManager(manager);
                    boolean error = jObj.getBoolean("error");
                    String message = jObj.getString("message");
                    Log.e("TAG", "Message: " + message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error);
                Toast.makeText(getApplicationContext(),
                        "Server Connection Fail", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("TAG", "Error: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nCause " + error.getCause()
                        + "\nnetworkResponse " + error.networkResponse.data.toString()
                        + "\nmessage" + error.getMessage());
                //hid pregress here
            }
        });

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

}
