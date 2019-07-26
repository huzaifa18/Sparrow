package com.example.bracketsol.sparrow.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    Call<ResponseBody> uploadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();

    }

    private void init() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Spinner sp_type_spinner = findViewById(R.id.sp_type);
        String[] years = {"Suggestion","Complaint"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(ReportActivity.this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_type_spinner.setAdapter(langAdapter);

        TextView yes = findViewById(R.id.yesCall);
        TextView no = findViewById(R.id.noCall);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sp_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG","Spinner: " + i);

                if (i == 0){



                } else {



                }

                //selectedItem = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void feedback(String content, String sub_content, String type) {

        uploadData = apiInterface.getfeedbackAndComplaint(content,sub_content,type);

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
