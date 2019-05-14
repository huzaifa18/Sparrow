package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Utils.Utils;
import com.example.bracketsol.sparrow.Volley.AppSingleton;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
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
    TextView createAccounttxt;
    String getname, getpass;
    ProgressBar simpleProgressBar;
    Handler handler;
    private Socket mSocket;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        //init
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        try {
            mSocket = IO.socket("https://social-funda.herokuapp.com/");
        } catch (URISyntaxException e) {
        }
        createAccounttxt = findViewById(R.id.create_account_textview);
        createAccounttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Login.this, VerificationDetails.class);
                startActivity(mainIntent);
            }
        });


        username.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    username.clearFocus();
                    password.requestFocus();
                    return true;
                }
                return false;
            }
        });
        nextButton = findViewById(R.id.login_button);
        handler = new Handler();

        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getname = username.getText().toString();
                getpass = password.getText().toString();
                checkValidate();

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(5000);
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
            }
        });
    }

    private void callLoginService(final String user, final String pass) {

        String cancel_req_tag = "register";
        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/auth/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = jObj.getString("message");
                    Log.e("TAG", "Message: " + message);

                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_SHORT).show();
                    simpleProgressBar.setVisibility(View.GONE);

                    String user = jObj.getString("user");
                    Log.e("TAG", "Message: " + user);
                    JSONObject userdata = jObj.getJSONObject("user");

                    int userid = userdata.getInt("_id");
                    String username = userdata.getString("username");
                    String email = userdata.getString("email");
                    String phone = userdata.getString("phone_no");
                    String password = userdata.getString("password");

                    String auth = jObj.getString("token");
                    Log.e("TAG", "" + auth);

                    Prefs.addPrefsForLogin(getApplicationContext(), userid, username, email, phone, password, auth);

                    mSocket.connect();
                    JSONObject getUnameforOnline = new JSONObject();
                    try {
                        getUnameforOnline.put("room", "global");
                        getUnameforOnline.put("user", username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mSocket.emit("online", getUnameforOnline);
                    Log.i("TAG", "sendMessage: 1" + mSocket.emit("join private chat", getUnameforOnline));

                    Log.e("TAG", "" + Prefs.getUserIDFromPref(Login.this));
                    Toast.makeText(Login.this, "" + userdata.getInt("_id"), Toast.LENGTH_SHORT).show();
                    simpleProgressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(Login.this, HomeActivity.class);
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
                        "No internet, Please try again later", Toast.LENGTH_LONG).show();
                //hid pregress here
                simpleProgressBar.setVisibility(View.GONE);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
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
            simpleProgressBar.setVisibility(View.GONE);

        } else {
            Toast.makeText(this, "Ok auth.", Toast.LENGTH_SHORT)
                    .show();
            simpleProgressBar.setVisibility(View.VISIBLE);
            callLoginService(username.getText().toString(), password.getText().toString());
        }
    }

}



