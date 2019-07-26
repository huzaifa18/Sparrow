package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.Utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    ApiInterface apiInterface;
    Call<ResponseBody> uploadData;

    EditText et_old;
    EditText et_new;
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();

    }

    private void init() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        et_old = findViewById(R.id.et_old);
        et_new = findViewById(R.id.et_new);
        btn_confirm = findViewById(R.id.btn_confirm);

        clickListeners();

    }

    private void clickListeners() {

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_old.getText().toString().isEmpty()){

                    et_old.setError("This Field can not be empty!");

                } else if (et_new.getText().toString().isEmpty()){

                    et_new.setError("This Field can not be empty!");

                } else {

                    changePassword(et_old.getText().toString(), et_new.getText().toString());

                }
            }
        });

    }

    private void changePassword(String oldPassword, String newPassword) {

        uploadData = apiInterface.getChangePassword(oldPassword,newPassword);

        uploadData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resString = null;
                try {
                    resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    String error = resJson.getString("error");
                    String msg = resJson.getString("message");
                    Log.e("UP", "Response: " + resString);

                    startActivity(new Intent(ChangePassword.this, HomeActivity.class));

                    //setData();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
