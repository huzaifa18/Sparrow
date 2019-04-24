package com.example.bracketsol.sparrow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Utils.Utils;
import com.example.bracketsol.sparrow.Volley.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class Login extends AppCompatActivity {


    private static View view;
    TextInputEditText username, password;
    Button nextButton;
    String getname, getpass;
    ProgressBar simpleProgressBar;
    Handler handler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        //init
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        nextButton = findViewById(R.id.login_button);
        handler = new Handler();

        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getname = username.getText().toString();
                getpass = password.getText().toString();

                //Log.e("TAG", getname + getpass);


                //simpleProgressBar.setVisibility(View.VISIBLE);
                checkValidate();

// Create and start a new Thread
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        } // Just catch the InterruptedException

                        // Now we use the Handler to post back to the main thread
                        handler.post(new Runnable() {
                            public void run() {
                                // Set the View's visibility back on the main UI Thread
                                simpleProgressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }).start();


//                JsonParser parser = new JsonParser();
//                JSONObject object = new JSONObject();
//
//                try {
//                    object.put("username", getname);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    object.put("password", "waqas100");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Call<User> call = apiInterface.Login(object);
//
//                call.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//
//                    }
//                });
//
////
//                apiInterface.Login(object).toString();
////


//                RequestBody username = RequestBody.create(MediaType.parse("text/plain"), getname);
//                String pass= RequestBody.create(MediaType.parse("text/plain"), getpass);

//                Gson gson = new GsonBuilder()
//                        .setLenient()
//                        .create();
//
//                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                        .connectTimeout(220, TimeUnit.SECONDS)
//                        .readTimeout(220, TimeUnit.SECONDS)
//                        .writeTimeout(220, TimeUnit.SECONDS)
//                        .build();
//
//                //creating retrofit object
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://social-funda.herokuapp.com/api/")
//                        .client(okHttpClient)
//                        .addConverterFactory(GsonConverterFactory.create(gson))
//                        .build();
//
//
//                //apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//                Toast.makeText(Login.this, "ok" + getpass + getname, Toast.LENGTH_SHORT).show();
//
//
//                ApiInterface service = retrofit.create(ApiInterface.class);
//
//
//                Call<User> call = service.Login(getname, getpass);
//
//
//                Log.e("check11", "message" + call.request().body().toString());
//
//                call.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        Toast.makeText(Login.this, "dfkmdlf", Toast.LENGTH_SHORT).show();
//                        if (response.isSuccessful())
//                            Log.e("check11", "message" + response.body().message);
//
//                        {
//                            Toast.makeText(Login.this, "" + response, Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        Log.e("check11", "checkval failure" + t.getMessage());
//
//                    }
//                });
//
////                User user = new User("1", getname, getpass);
////                Call<ResponseBody> call = apiInterface.Login(user);
////                call.enqueue(new Callback<ResponseBody>() {
////                    @Override
////                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////
////                        if (response.isSuccessful()) {
////                            ResponseBody jsonResponse = response.body();
////                            Toast.makeText(Login.this, "okkkkkkkkkkkkk", Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(Login.this, "not", Toast.LENGTH_SHORT).show();
////                        }
////
////                        Toast.makeText(Login.this, "ok", Toast.LENGTH_SHORT).show();
////                        try {
////                            String resString = response.body().string();
////
////                            Log.e("check11", "message" + response);
////                            Toast.makeText(Login.this, "" + response, Toast.LENGTH_SHORT).show();
////
////                            JSONObject resJson = new JSONObject(resString);
////                            Toast.makeText(Login.this, "ok" + getpass + getname, Toast.LENGTH_SHORT).show();
////                            Toast.makeText(Login.this, "" + resJson.getString("message"), Toast.LENGTH_LONG).show();
////
////                            //Log.e("check11", "message" + resJson.getJSONObject("data"));
////                            if (resJson.getJSONObject("data") != null) {
////                                JSONObject data = resJson.getJSONObject("data");
////                                Log.e("check11", "message" + resJson.getJSONObject("user"));
////                                Toast.makeText(Login.this, "" + getname + getpass, Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(Login.this, FindFriends.class);
////                                startActivity(intent);
////
//////                            SaveSharedPreference.getInstance(getContext()).setLoggedIn(getContext(), true);
//////                            Intent intent = new Intent(getContext(), HomeActivity.class);
//////                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
//////                            startActivity(intent);
////
//////                            Intent intent = new Intent(getContext(), HomeActivity.class);
//////
////// startActivity(intent);
////
//////                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//////                            SharedPreferences.Editor editor = sharedPreferences.edit();
//////                            Gson gson = new Gson();
//////                            User user = new User(data.getInt("id"), data.getString("name"), data.getString("email"), data.getString("phone"), data.getString("cnic"), data.getString("img_url"), data.getString("type"), data.getString("status") );
//////                            String json = gson.toJson(user);
//////                            editor.putString(USER_PREF, json);
//////                            editor.apply();
////
//////                            User user = new User(data.getInt("id"), data.getString("name"), data.getString("email"), data.getString("phone"), data.getString("cnic"), data.getString("img_url"), data.getString("type"), data.getString("status"));
//////                            //User user = new User(12, "asdfas", "asd@asdf.com", "456", "4546", "asdf", "asdf", "asdf");
//////
//////                            SaveSharedPreference.getInstance(getContext()).addUsertoPref(user);
//////                            //saveSharedPreference.addUsertoPref(user);
////                                //saveSharedPreference.getStudent();
////                                //saveSharedPreference.addUsertoPref(user);
//////                            user.getId();
//////                            user.getFullName();
//////
//////                            Toast.makeText(getContext(), ""+json, Toast.LENGTH_SHORT).show();
//////                            Toast.makeText(getContext(), ""+user.getFullName()+user.getId(), Toast.LENGTH_SHORT).show();
////
////                            } else {
////                                Toast.makeText(Login.this, "asdfe", Toast.LENGTH_SHORT).show();
////                            }
////
////
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                            Log.e("check11", "checkval onresponse" + e.getMessage());
////
////
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                            Log.e("check11", "checkval onresponse" + e.getMessage());
////
////                        }
////                        //User user = response.body();
////                        //Toast.makeText(getActivity(), "response"+ user, Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onFailure(Call<ResponseBody> call, Throwable t) {
////
////                        Log.e("check11", "checkval failure" + t.getMessage());
////                        Toast.makeText(Login.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                });
////
////
////            }
////        });


            }
        });
    }

    private void callLoginService(final String user, final String pass) {

        // Tag used to cancel the request
        String cancel_req_tag = "register";
        //show pregress here

        //Toast.makeText(this, "" + user + pass, Toast.LENGTH_SHORT).show();


        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/auth/login", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.e("TAG", "Login Response: " + response.toString());


                try {

                    JSONObject jObj = new JSONObject(response);

                    String message = jObj.getString("message");


                    Log.e("TAG", "Message: " + message);

                    String user = jObj.getString("user");
                    Log.e("TAG", "Message: " + user);

                    //int userid = jObj.getInt("_id");

                    JSONObject userdata = jObj.getJSONObject("user");

                    //Log.e("TAG",""+userdata);

                    int userid = userdata.getInt("_id");
                    String username = userdata.getString("username");
                    String email = userdata.getString("email");
                    String phone = userdata.getString("phone_no");
                    String password = userdata.getString("password");

                    Prefs.addPrefsForLogin(getApplicationContext(), userid, username, email, phone, password);

                    Log.e("TAG", "" + Prefs.getUserIDFromPref(Login.this));

                    //Log.e("TAG",""+);

                    Toast.makeText(Login.this, "" + userdata.getInt("_id"), Toast.LENGTH_SHORT).show();

                    simpleProgressBar.setVisibility(View.VISIBLE);


                    Intent intent = new Intent(Login.this, FindFriends.class);
                    startActivity(intent);

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
                simpleProgressBar.setVisibility(View.VISIBLE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user);
                params.put("password", pass);

                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }


    private void checkValidate() {
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getname);

        // Check if all strings are null or not
        if (getname.equals("") || getname.length() == 0
                || getpass.equals("") || getpass.length() == 0) {
            simpleProgressBar.setVisibility(View.VISIBLE);


            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else if (!m.find()) {
            Toast.makeText(this, "Your Email Id is Invalid", Toast.LENGTH_SHORT).show();
            simpleProgressBar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Sign in successful.", Toast.LENGTH_SHORT)
                    .show();
            simpleProgressBar.setVisibility(View.VISIBLE);
            callLoginService(username.getText().toString(), password.getText().toString());
        }
    }

}



