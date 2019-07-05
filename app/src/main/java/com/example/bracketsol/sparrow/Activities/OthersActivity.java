package com.example.bracketsol.sparrow.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Adapter.ProfileAdapter;
import com.example.bracketsol.sparrow.Model.ModelProfile;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Utils.RoundRectCornerImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bracketsol.sparrow.MyApp.getContext;

public class OthersActivity extends AppCompatActivity {

    private static View view;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    TextView toolbarTextView_next;
    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    ArrayList<ModelProfile> arrayList;
    LinearLayoutManager manager;
    RoundRectCornerImageView pro_img;
    Context context;
    ImageView pop_image;
    Dialog settingsDialog;
    boolean zoomOut = true;

    Button bt_add_friend;

    TextView username, bio, profession, blog, email;

    String geterror, getmessage;

    String getProfileData, getUsername, getEmil, getPhonenumber, getProfession, getPicurl, getName, getBlog, getDob, getGender, getStatement;

    ApiInterface apiInterface;
    Call<ResponseBody> getProfile;

    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        userId = Integer.parseInt(getIntent().getStringExtra("user_id"));
        Log.e("TAG", "user_id: " + userId);

        init();

    }

    private void init() {
        recyclerView = findViewById(R.id.ppl_you_know_list);
        arrayList = new ArrayList<>();

        bt_add_friend = findViewById(R.id.bt_edit);

        profileAdapter = new ProfileAdapter(arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(OthersActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(OthersActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(profileAdapter);

        pro_img = findViewById(R.id.civ_profile);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        username = findViewById(R.id.username_profile);
        email = findViewById(R.id.tv_email);
        blog = findViewById(R.id.tv_blog);
        profession = findViewById(R.id.tv_profession);
        bio = findViewById(R.id.tv_bio);

        settingsDialog = new Dialog(OthersActivity.this);

        Listeners();

        getProfile();

        prepareMovieData();

    }

    private void Listeners() {

        pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                pro_img.buildDrawingCache();
                Bitmap bmap = pro_img.getDrawingCache();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                Intent intent = new Intent(getContext(), showPicture.class);
                intent.putExtra("picture", image);
                startActivity(intent);

            }

        });

        bt_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(OthersActivity.this, EditProfile.class);
                intent.putExtra("username", getUsername);
                intent.putExtra("email", getEmil);
                intent.putExtra("phonenumber", getPhonenumber);
                intent.putExtra("profession", getProfession);
                intent.putExtra("picurl", getPicurl);
                intent.putExtra("name", getName);
                intent.putExtra("blog", getBlog);
                intent.putExtra("dob", getDob);
                intent.putExtra("gender", getGender);
                intent.putExtra("statement", getStatement);
                startActivity(intent);*/

            }
        });

    }

    private void prepareMovieData() {

        ModelProfile account = new ModelProfile(R.drawable.ic_seo, "Kamal Rafiq");
        arrayList.add(account);
        account = new ModelProfile(R.drawable.ic_seo, "Sajid Rahim");
        arrayList.add(account);
        account = new ModelProfile(R.drawable.ic_seo, "Waseem Azeem");
        arrayList.add(account);
        profileAdapter.notifyDataSetChanged();

    }

    private void getProfile() {
        Log.e("TAG", "Get Profile user_id: " + userId);

        getProfile = apiInterface.getProfile(userId);
        getProfile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resString = null;
                try {
                    resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    geterror = resJson.getString("error");
                    getmessage = resJson.getString("message");
                    Log.e("TAG", geterror + getmessage);

                    getProfileData = resJson.getString("profile");
                    Log.e("TAG", "Message: " + getProfileData);
                    JSONObject profileData = resJson.getJSONObject("profile");

                    getUsername = profileData.getString("username");
                    Log.e("TAG", getUsername);
                    getEmil = profileData.getString("email");
                    getPhonenumber = profileData.getString("phone_no");
                    getProfession = profileData.getString("profession");
                    getPicurl = profileData.getString("picture_url");
                    getName = profileData.getString("name");
                    getDob = profileData.getString("date_of_birth");
                    getBlog = profileData.getString("blog");
                    getGender = profileData.getString("gender");
                    getStatement = profileData.getString("statement");
                    Log.e("TAG", getUsername);
                    Log.e("TAG", getEmil);
                    Log.e("TAG", getPhonenumber);
                    Log.e("TAG", getProfession);
                    Log.e("TAG", getPicurl);
                    Log.e("TAG", getName);
                    Log.e("TAG", getDob);
                    Log.e("TAG", getGender);
                    Log.e("TAG", getStatement);

                    setData();

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

    private void setData() {

        username.setText(getName);
        bio.setText(getStatement);
        profession.setText(getProfession);
        email.setText(getEmil);
        blog.setText(getBlog);
        Glide.with(getContext())
                .load("https://s3.amazonaws.com/social-funda-bucket/"+getPicurl)
                .into(pro_img);

    }

}