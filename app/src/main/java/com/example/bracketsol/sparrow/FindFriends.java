package com.example.bracketsol.sparrow;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.Adapter.FindFriendAdapter;
import com.example.bracketsol.sparrow.Adapter.FindFriendAdapterTry;
import com.example.bracketsol.sparrow.Model.FindFriendModel;
import com.example.bracketsol.sparrow.Model.FindFriendModelTry;
import com.example.bracketsol.sparrow.Volley.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindFriends extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTextView_next;
    RecyclerView recyclerView;
    ArrayList<FindFriendModelTry> resarrayList;
    FindFriendAdapterTry resadapter;
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
        recyclerView = findViewById(R.id.find_recycler_view);
        resarrayList = new ArrayList<FindFriendModelTry>();
        GetAll();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //new AsyncCaller().execute();
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
                        Log.e("TAG", "mess" + product.getString("_id"));
                        Log.e("TAG", "mess" + product.getString("username"));
                        Log.e("TAG", "mess" + product.getString("email"));
                        Log.e("TAG", "mess" + product.getString("password"));


                        resarrayList.add(new FindFriendModelTry(R.drawable.ic_seo, product.getString("username"), product.getString("email")));

                        //resarrayList.add(new FindFriendModel(R.drawable.ic_seo, R.drawable.frndship_btn_selector, R.drawable.frndship_btn_selector, product.getString("username"), product.getString("email")));
                    }
                    resadapter = new FindFriendAdapterTry(FindFriends.this, resarrayList);
                    recyclerView.setAdapter(resadapter);
                    manager = new LinearLayoutManager(FindFriends.this, LinearLayoutManager.VERTICAL, true);
                    ItemTouchHelper itemTouchHelper = new
                            ItemTouchHelper(new FindFriendAdapterTry.SwipeToDeleteCallback(resadapter));
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
                //hid pregress here
            }
        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", username);
//                params.put("password", password);
//                params.put("email", email);
//
//                return params;
//            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


//                    resarrayList.add(new FindFriendModel(R.drawable.ic_icons8_administrator_male, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Ali haider", "98 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_man, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Usman Anwar", "54 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_man3, R.drawable.frndship_btn_selector, R.drawable.final_selector, "kainaat gul", "1 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_girl, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Rizwan mughal", "2 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_target, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Javed ali", "9 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_team, R.drawable.frndship_btn_selector, R.drawable.final_selector, "haseeb imtiaz", "6 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_seo, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Wiliayat jutt", "5 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_girl, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Aftab hussain", "22 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_target, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Sadaf noreen", "12 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_icons8_administrator_male, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Iqbal jutt", "0 mutual friend"));
//                    resarrayList.add(new FindFriendModel(R.drawable.ic_man3, R.drawable.frndship_btn_selector, R.drawable.final_selector, "Memoona iqbal", "23 mutual friend"));
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
