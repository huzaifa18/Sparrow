package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Tryy.PhoneAuth;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Utils.Utils;
import com.example.bracketsol.sparrow.Volley.AppSingleton;
import com.example.bracketsol.sparrow.webrtcc.ConnectActivity;
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
    EditText username, password;
    Button nextButton, fb_btn;
    TextView createAccounttxt;
    String getname, getpass;
    ProgressBar simpleProgressBar;
    CheckBox passwordchk;
    Handler handler;
    private Socket mSocket;
    Animation animShake ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        //init
        username = findViewById(R.id.phone);
        password = findViewById(R.id.passwordd);
        passwordchk = findViewById(R.id.show_pass_checkbox);
        passwordchk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    password.setInputType(129);
                }
            }
        });

        fb_btn = findViewById(R.id.facebook_btn_signin);

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

        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ConnectActivity.class);
                startActivity(intent);
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
                simpleProgressBar.setVisibility(View.VISIBLE);
                nextButton.setEnabled(false);
                checkValidate();
            }
        });
    }

    private void callLoginService(final String user, final String pass) {

        String cancel_req_tag = "register";
        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/auth/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                simpleProgressBar.setVisibility(View.GONE);
                nextButton.setEnabled(true);
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = jObj.getString("message");
                    Log.e("TAG", "Message: " + message);


                    animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    if(message.equals("username or email is invalid.")){
                        username.setAnimation(animShake);
                        username.setError(message);
                    }else if(message.equals("Password is incorrect")){
                        password.setAnimation(animShake);
                        password.setError(message);
                    }


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
                    //Intent intent = new Intent(Login.this, PhoneAuth.class);
                    Intent intent = new Intent(Login.this, HomeActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                simpleProgressBar.setVisibility(View.GONE);
                nextButton.setEnabled(true);
                Log.e("TAG", "Error: " + error);
                Toast.makeText(getApplicationContext(),
                        "No internet, Please try again later", Toast.LENGTH_LONG).show();
                //hid pregress here
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

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            simpleProgressBar.setVisibility(View.GONE);
            nextButton.setEnabled(true);

        } else {
            Toast.makeText(this, "Ok auth.", Toast.LENGTH_SHORT)
                    .show();
            callLoginService(username.getText().toString(), password.getText().toString());
        }
    }

}



