package com.example.bracketsol.sparrow;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.Utils.Utils;
import com.example.bracketsol.sparrow.Volley.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class VerificationDetails extends AppCompatActivity {

    ImageButton nextButton;
    Toolbar toolbar;

    TextInputEditText username, phone, email, password;
    //spinner
    String myLog = "myLog";

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_details);
        progressBarHolder = (FrameLayout) findViewById(R.id.up_progressBarHolder);

        username = findViewById(R.id.up_username);
        phone = findViewById(R.id.up_phone);
        email = findViewById(R.id.up_email);
        password = findViewById(R.id.up_password);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Back clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        nextButton = findViewById(R.id.nextt_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new VerificationDetails.MyTask().execute();

//                Intent intent = new Intent(VerificationDetails.this,Login.class);
//                startActivity(intent);

                Toast.makeText(VerificationDetails.this, "" + username.getText().toString() + phone.getText().toString() + email.getText().toString() + password.getText().toString(), Toast.LENGTH_SHORT).show();

                String getuname = username.getText().toString();
                String getemail = email.getText().toString();
                String getpasword = password.getText().toString();
                String getphone = phone.getText().toString();
                checkValidate();

            }
        });

    }

    public void click(View view) {
        view.getId();
        Intent intent = new Intent(VerificationDetails.this, SetProfilePicture.class);
        startActivity(intent);
    }

    private void SignUp(final String username, final String email, final String phone, final String password) {

        // Tag used to cancel the request
        String cancel_req_tag = "register";
        //show pregress here


        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/users/register", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Signup Response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);

                    //boolean error = jObj.getBoolean("error");


                    String message = jObj.getString("error");

                    Log.e("TAG", "Message: " + message);
                    StringBuilder sb = new StringBuilder();
                    sb.append(message);


                    if (message == "0") {
                        Toast.makeText(VerificationDetails.this, "Successfully registered" + message, Toast.LENGTH_SHORT).show();
                    }
//                    if(message.equals(0)){
//                        Toast.makeText(VerificationDetails.this, "Successfully registered", Toast.LENGTH_SHORT).show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Server Connection Fail", Toast.LENGTH_LONG).show();
                //hid pregress here
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url

                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", username);
//                params.put("email", email);
//                params.put("phone_no", phone);
//                params.put("password", password);

                params.put("username", username);
                params.put("email", email);
                params.put("phone_no", phone);
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

    private void checkValidate() {
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(email.getText().toString());

        // Check if all strings are null or not
        if (username.getText().toString().equals("") || phone.getText().toString().length() == 0 ||
                email.getText().toString().length() == 0 || password.getText().toString().length() == 0) {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else if (!m.find()) {
            Toast.makeText(this, "Your Email Id is Invalid", Toast.LENGTH_SHORT).show();
//        } else if() {
//            Toast.makeText(this, "Do SignUp.", Toast.LENGTH_SHORT)
//                    .show();
//
        } else {
            Toast.makeText(this, "Do sign up", Toast.LENGTH_SHORT).show();
            SignUp(username.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
        }

    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nextButton.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            nextButton.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    Log.d(myLog, "Emulating some task.. Step " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
