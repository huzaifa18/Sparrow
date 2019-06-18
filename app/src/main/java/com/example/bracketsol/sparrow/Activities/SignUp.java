package com.example.bracketsol.sparrow.Activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Volley.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {


    TextInputEditText username, email, password;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(username.getText().toString(),email.getText().toString(),password.getText().toString());
            }
        });

    }

    private void SignUp(final String username, final String email, final String password) {

        // Tag used to cancel the request
        String cancel_req_tag = "register";
        //show pregress here


        StringRequest strReq = new StringRequest(Request.Method.POST, "https://social-funda.herokuapp.com/api/users/register", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Login Response: " + response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);

                    //boolean error = jObj.getBoolean("error");

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

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);

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
