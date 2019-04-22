package com.example.bracketsol.sparrow;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.Volley.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class Login extends AppCompatActivity {


    TextInputEditText username, password;
    Button nextButton;
    String getname, getpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        //init
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        nextButton = findViewById(R.id.login_button);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getname = username.getText().toString();
                getpass = password.getText().toString();

                Log.e("TAG", getname + getpass);

                callLoginService(username.getText().toString(), password.getText().toString());
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

    private void callLoginService(final String username, final String password) {

        // Tag used to cancel the request
        String cancel_req_tag = "register";
        //show pregress here


        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/auth/login", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Login Response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);

                    //boolean error = jObj.getBoolean("error");

                    String message = jObj.getString("message");


                    JSONObject user = jObj.getJSONObject("user");

                    String id = user.getString("_id");
                    String username = user.getString("username");
                    String email = user.getString("email");
                    String password = user.getString("password");
                    String phone_no = user.getString("phone_no");
                    String profession = user.getString("profession");
                    String picture_url = user.getString("picture_url");
                    String created_at = user.getString("created_at");

                    Log.e("TAG",id);

                    String token = jObj.getString("token");


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

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

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
}


