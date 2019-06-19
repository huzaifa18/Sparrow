package com.example.bracketsol.sparrow.Tryy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.R;
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
                            Intent intent = new Intent(PhoneAuth.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
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

}