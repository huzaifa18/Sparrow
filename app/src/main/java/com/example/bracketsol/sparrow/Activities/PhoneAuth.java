package com.example.bracketsol.sparrow.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.example.bracketsol.sparrow.Service.DummyService;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Volley.AppSingleton;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {

    private static final String TAG = "TAG";

    EditText et_phone, code;
    TextView tv_title, tv_short_title;
    Button btn_submit;
    FirebaseAuth.AuthStateListener mAuthListner;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthCredential mCredential;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private Socket mSocket;
    Animation animShake;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {

        try {
            mSocket = IO.socket("https://social-funda.herokuapp.com/");
        } catch (URISyntaxException e) {
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        et_phone = (EditText) findViewById(R.id.et_phoneNumber);
        code = (EditText) findViewById(R.id.verification_code);
        tv_short_title = (TextView) findViewById(R.id.short_title);
        tv_title = (TextView) findViewById(R.id.title);
        btn_submit = (Button) findViewById(R.id.bt_submit);

        btnListener();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(PhoneAuth.this, HomeActivity.class));
                    finish();
                }
            }
        };

    }

    private void btnListener() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_submit.getText().equals("Send Code")) {
                    if (et_phone.getText().length() == 11) {
                        verifyByPhone("+92" + et_phone.getText().toString());
                    } else {
                        et_phone.setError("Please Enter a Valid Phone Number!");
                    }
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.e(TAG, "Verification ID: " + mVerificationId);
                    Log.e(TAG, "Verification code: " + code.getText().toString());
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code.getText().toString());
                    signInWithPhoneAuthCredential(credential);

                }

            }
        });

    }

    private void verifyByPhone(final String phoneNumber) {
        et_phone.setVisibility(View.GONE);
        tv_title.setText("Enter Code ");
        tv_short_title.setVisibility(View.VISIBLE);
        tv_short_title.setText(phoneNumber);
        code.setVisibility(View.VISIBLE);
        btn_submit.setText("Verify");

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.e(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.e(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.e(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                //mResendToken = token;

                // ...
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallbacks);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user = task.getResult().getUser();
                            Log.e(TAG, "Display Name: " + user.getDisplayName());
                            Log.e(TAG, "Email: " + user.getEmail());
                            Log.e(TAG, "Phone Number: " + user.getPhoneNumber());
                            Log.e(TAG, "Photo URL: " + user.getPhotoUrl());
                            Log.e(TAG, "Provider ID: " + user.getProviderId());
                            Log.e(TAG, "UID: " + user.getUid());
                            Log.e(TAG, "Last Sign In: " + user.getMetadata().getLastSignInTimestamp());
                            callLoginService(Prefs.getUserNameFromPref(PhoneAuth.this),Prefs.getPasswordFromPref(PhoneAuth.this));
                            /*Intent intent = new Intent(PhoneAuth.this, HomeActivity.class);
                            startActivity(intent);
                            finish();*/
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(PhoneAuth.this, "Failed: " + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void callLoginService(final String user, final String pass) {
        //startService(new Intent(this, BackgroundService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            this.startForegroundService(new Intent(PhoneAuth.this, DummyService.class));
        else
            this.startService(new Intent(PhoneAuth.this, DummyService.class));

        String cancel_req_tag = "register";
        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/auth/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = jObj.getString("message");
                    Log.e("TAG", "Message: " + message);
                    animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    if (message.equals("username or email is invalid.")) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PhoneAuth.this, "" + message, Toast.LENGTH_LONG).show();
                        //username.setAnimation(animShake);
                       //username.setError(message);
                    } else if (message.equals("Password is incorrect")) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PhoneAuth.this, "" + message, Toast.LENGTH_LONG).show();
                        //password.setAnimation(animShake);
                        //password.setError(message);
                    }

                    String user = jObj.getString("user");
                    Log.e("TAG", "Message: " + user);
                    JSONObject userdata = jObj.getJSONObject("user");
                    int userid = userdata.getInt("_id");
                    String getusername = userdata.getString("username");
                    String getfullname = userdata.getString("full_name");
                    String email = userdata.getString("email");
                    String phone = userdata.getString("phone_no");
                    String getprofession = userdata.getString("profession");
                    String auth = jObj.getString("token");
                    Log.e("TAG", "" + auth);

                    Prefs.addPrefsForLogin(getApplicationContext(), userid, getusername, getfullname, email, phone, getprofession, auth);
                    //database.InsertDataToInternalDatabase(internalId, userid, getusername, getfullname, email, phone, getprofession);

                    mSocket.connect();
                    JSONObject getUnameforOnline = new JSONObject();

                    try {
                        getUnameforOnline.put("room", "global");
                        getUnameforOnline.put("user", getusername);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSocket.emit("online", getUnameforOnline);
                    Log.i("TAG", "sendMessage: 1" + mSocket.emit("join private chat", getUnameforOnline));
                    Log.e("TAG", "" + Prefs.getUserIDFromPref(PhoneAuth.this));
                    Toast.makeText(PhoneAuth.this, "" + userdata.getInt("_id"), Toast.LENGTH_SHORT).show();
                    /*Login.LongOperation longOperation = new Login.LongOperation();
                    longOperation.execute("");*/
                    Intent intent = new Intent(PhoneAuth.this, HomeActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //nextButton.setEnabled(true);
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

    /*@SuppressLint("StaticFieldLeak")
    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            database.getUsersData(Login.this);
            Log.i("userInternal", "" + database.gettUserIDFromDB(Login.this));
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Login.this, "executed", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }*/

}