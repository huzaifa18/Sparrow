package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Utils.Utils;
import com.example.bracketsol.sparrow.Volley.AppSingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by bracketsol on 4/12/2019.
 */

public class SignUp extends AppCompatActivity {

    ImageButton nextButton;
    Animation animShake;
    ApiInterface apiInterface;

    TextInputEditText username, phone, email, password;
    String myLog = "myLog";
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    FrameLayout progressBarHolder;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        inititialize();

    }

    private void inititialize() {

        progressBarHolder = (FrameLayout) findViewById(R.id.up_progressBarHolder);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        username = findViewById(R.id.up_username);
        phone = findViewById(R.id.up_phone);
        email = findViewById(R.id.up_email);
        password = findViewById(R.id.up_password);
        nextButton = findViewById(R.id.nextt_button);

        //Generating token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    token = task.getResult().getToken();
                    Log.i("tk", "token: " + token);
                } else {
                    Log.i("tk", "No token: " + task.getException().getMessage());
                }
            }
        });

        clickListeners();

    }

    private void clickListeners() {

        username.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    username.clearFocus();
                    email.requestFocus();
                    return true;
                }
                return false;
            }
        });

        email.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    email.clearFocus();
                    phone.requestFocus();
                    return true;
                }
                return false;
            }
        });

        phone.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on Enter key press
                    phone.clearFocus();
                    password.requestFocus();
                    return true;
                }
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(SignUp.this,Login.class);
//                startActivity(intent);

                Toast.makeText(SignUp.this, "" + username.getText().toString() + phone.getText().toString() + email.getText().toString() + password.getText().toString(), Toast.LENGTH_SHORT).show();

                String getuname = username.getText().toString();
                String getemail = email.getText().toString();
                String getpasword = password.getText().toString();
                String getphone = phone.getText().toString();

                /*Intent intent = new Intent(SignUp.this, SetProfilePicture.class);
                startActivity(intent);*/
                checkValidate();

            }
        });

    }

    private void checkValidate() {
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(email.getText().toString());
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        boolean failFlag = false;
        // TODO Auto-generated method stub
        if(username.getText().toString().equals("")) {
            username.setAnimation(animShake);
            failFlag = true;
            username.setError( "username is required" );
        } else if(email.getText().toString().length() == 0) {
            email.setAnimation(animShake);
            failFlag = true;
            email.setError( "email is required" );
        } else if(phone.getText().toString().length() == 0) {
            failFlag = true;
            phone.setError( "Phone number is required" );
            phone.setAnimation(animShake);
        } else if(password.getText().toString().length() == 0) {
            failFlag = true;
            password.setAnimation(animShake);
            password.setError("Password is required");
        } else if (failFlag == false) {
            //new SignUp.MyTask().execute();
            SignUp(username.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
        }

    }

    private void SignUp(final String username, final String email, final String phone, final String password) {

        progressBarHolder.setVisibility(View.VISIBLE);
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
                    StringBuilder sb = new StringBuilder();
                    String message = jObj.getString("error");
                    String message_two = jObj.getString("message");
                    String message_three = jObj.getString("user_id");

                    Log.e("TAG", "MessageTwo: " + message_two);
                    sb.append(message_two);
                    Log.e("TAG", "Message: " + message);
                    sb.append(message);

                    if (message.equals("0")) {

                        Toast.makeText(SignUp.this, "Successfully registered" + message_two, Toast.LENGTH_SHORT).show();
                        int userid = jObj.getInt("user_id");
                        Prefs.addPrefsForUserId(SignUp.this, userid);
                        Log.i("token", "userid" + userid);

                        if (Prefs.getUserIDFromPref(SignUp.this) != -1 && Prefs.gettUserUDID(SignUp.this) != null) {

                            sendRegistrationToServer(token, Prefs.getUserIDFromPref(SignUp.this));

                        } else {

                            Log.i("token", "user not registered");

                        }
                    } else {

                        Toast.makeText(SignUp.this, ""+message_two, Toast.LENGTH_SHORT).show();
                        progressBarHolder.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error);
                StringBuilder sb = new StringBuilder();
                Toast.makeText(getApplicationContext(),
                        "Server Connection Fail", Toast.LENGTH_LONG).show();
                progressBarHolder.setVisibility(View.GONE);
                //hid pregress here
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url

                Map<String, String> params = new HashMap<String, String>();
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


    private void sendRegistrationToServer(String token, int userId) {
        Call<ResponseBody> sendTokenToServer = apiInterface.sendTokenToServer(token, "Android", userId);
        sendTokenToServer.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    progressBarHolder.setVisibility(View.GONE);
                    String responsestr = response.body().string();
                    JSONObject jsonObject = new JSONObject(responsestr);
                    String message = jsonObject.getString("message");
                    Log.i("token", "userid" + message);

                    Intent intent = new Intent(SignUp.this, PhoneAuth.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBarHolder.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                    progressBarHolder.setVisibility(View.GONE);
                }

                /*Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBarHolder.setVisibility(View.GONE);
            }
        });

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
