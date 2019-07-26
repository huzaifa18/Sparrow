package com.example.bracketsol.sparrow.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Activities.EditProfile;
import com.example.bracketsol.sparrow.Activities.showPicture;
import com.example.bracketsol.sparrow.Adapter.ProfileAdapter;
import com.example.bracketsol.sparrow.Model.ModelProfile;
import com.example.bracketsol.sparrow.MyApp;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ProfileFragment extends Fragment {

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


    Button bt_edit,btn_logout;

    TextView username, bio, profession, blog, email;

    String geterror, getmessage;

    String getProfileData, getUsername, getEmil, getPhonenumber, getProfession, getPicurl, getName, getBlog, getDob, getGender, getStatement;

    ApiInterface apiInterface;
    Call<ResponseBody> getProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.profile_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.ppl_you_know_list);
        arrayList = new ArrayList<>();

        bt_edit = view.findViewById(R.id.bt_edit);
        btn_logout = view.findViewById(R.id.logout);

        profileAdapter = new ProfileAdapter(arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(profileAdapter);

        pro_img = view.findViewById(R.id.civ_profile);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        username = view.findViewById(R.id.username_profile);
        email = view.findViewById(R.id.tv_email);
        blog = view.findViewById(R.id.tv_blog);
        profession = view.findViewById(R.id.tv_profession);
        bio = view.findViewById(R.id.tv_bio);

        settingsDialog = new Dialog(getContext());

        Listeners();

        prepareMovieData();

        getProfile();

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

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.clearPrefData(getContext());
                System.exit(0);
            }
        });
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                intent.putExtra("username", getName);
                intent.putExtra("email", getEmil);
                intent.putExtra("phonenumber", getPhonenumber);
                intent.putExtra("profession", getProfession);
                intent.putExtra("picurl", getPicurl);
                intent.putExtra("full_name", getName);
                intent.putExtra("blog", getBlog);
                intent.putExtra("dob", getDob);
                intent.putExtra("gender", getGender);
                intent.putExtra("statement", getStatement);
                startActivity(intent);

            }
        });

    }

    private void loadPhoto(ImageView imageView) {

        ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.image_layout,
                (ViewGroup) view.findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });


        imageDialog.create();
        imageDialog.show();
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
        getProfile = apiInterface.getProfile(Prefs.getUserIDFromPref(MyApp.getContext()));
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
                    Log.i("TAG", getUsername);
                    getEmil = profileData.getString("email");
                    getPhonenumber = profileData.getString("phone_no");
                    getProfession = profileData.getString("profession");
                    getPicurl = profileData.getString("picture_url");
                    getName = profileData.getString("full_name");
                    getDob = profileData.getString("date_of_birth");
                    getBlog = profileData.getString("blog");
                    getGender = profileData.getString("gender");
                    getStatement = profileData.getString("statement");
                    Log.i("TAG", getUsername);
                    Log.i("TAG", getEmil);
                    Log.i("TAG", getPhonenumber);
                    Log.i("TAG", getProfession);
                    Log.i("TAG", getPicurl);
                    Log.i("TAG", getName);
                    Log.i("TAG", getDob);
                    Log.i("TAG", getGender);
                    Log.i("TAG", getStatement);

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

        //        ModelProfile account = new ModelProfile(R.drawable.ic_seo, "kamal Rafiq");
        //        arrayList.add(account);
        //        account = new ModelProfile(R.drawable.ic_seo, "sajid Rahim");
        //        arrayList.add(account);
        //        account = new ModelProfile(R.drawable.ic_seo, "waseem Azeem");
        //        arrayList.add(account);
        //        profileAdapter.notifyDataSetChanged();

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