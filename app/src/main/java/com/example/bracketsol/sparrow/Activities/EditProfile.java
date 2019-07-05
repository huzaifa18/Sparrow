package com.example.bracketsol.sparrow.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.CreatePost;
import com.example.bracketsol.sparrow.MyApp;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.Utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Part;

public class EditProfile extends AppCompatActivity {

    EditText name, bio, blog, profession, dob, email, et_phone;
    Button bt_boy;
    Button bt_girl;
    Boolean gender;
    File file;
    final int CODE_IMAGE = 0;
    TextView change_profile;
    String getName, getBio, getBlog, getProfession, getEmail, getPhonenumber, getDob, getGender, getStatement, getPicurl;
    CircleImageView civ;
    String postPath = "";
    ImageView tick;

    ApiInterface apiInterface;
    Call<ResponseBody> uploadData;

    Boolean genderBool = true;
    String hasFile = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getPreviousData();
        init();
    }

    private void getPreviousData() {
        Intent intent = getIntent();
        getName = intent.getStringExtra("name");
        getBio = intent.getStringExtra("statement");
        getBlog = intent.getStringExtra("blog");
        getEmail = intent.getStringExtra("email");
        getProfession = intent.getStringExtra("profession");
        getPhonenumber = intent.getStringExtra("phonenumber");
        getDob = intent.getStringExtra("dob");
        getGender = intent.getStringExtra("gender");
        getStatement = intent.getStringExtra("statement");
        getPicurl = intent.getStringExtra("picurl");

    }

    private void init() {

        gender = true;
        bt_boy = findViewById(R.id.bt_boy);
        bt_girl = findViewById(R.id.bt_girl);
        tick = findViewById(R.id.iv_tickk);

        name = findViewById(R.id.et_name);
        bio = findViewById(R.id.et_bio);
        blog = findViewById(R.id.et_blog);
        email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        profession = findViewById(R.id.et_profession);
        dob = findViewById(R.id.et_dob);
        change_profile = findViewById(R.id.tv_change_pp);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        civ = findViewById(R.id.civ_profile);
        setData();

        clickListeneres();
    }

    private void setData() {
        name.setText(getName);
        bio.setText(getBio);
        blog.setText(getBlog);
        email.setText(getEmail);
        profession.setText(getProfession);
        dob.setText(getDob);
        et_phone.setText(getPhonenumber);


        Glide.with(EditProfile.this)
                .load("https://s3.amazonaws.com/social-funda-bucket/" + getPicurl)
                .into(civ);

        if (getGender.equals("male")) {

            genderBool = true;

        } else {

            genderBool = false;

        }

    }

    private void clickListeneres() {

        bt_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!genderBool) {
                    bt_boy.setBackground(getResources().getDrawable(R.drawable.btn_filled));
                    bt_girl.setBackground(getResources().getDrawable(R.drawable.btn_hollow));
                    genderBool = true;
                }

            }
        });

        bt_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (genderBool) {
                    bt_girl.setBackground(getResources().getDrawable(R.drawable.btn_filled));
                    bt_boy.setBackground(getResources().getDrawable(R.drawable.btn_hollow));
                    genderBool = false;
                }

            }
        });

        change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_IMAGE);

            }
        });

        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
                uploadDataToServer();
            }
        });
    }

    private void uploadData() {

        getName = name.getText().toString();
        getEmail = email.getText().toString();
        getPhonenumber = et_phone.getText().toString();
        getProfession = profession.getText().toString();
        getStatement = bio.getText().toString();
        getBlog = blog.getText().toString();
        getDob = dob.getText().toString();

    }

    private void uploadDataToServer() {

        File fileUp = new File(postPath);

        MultipartBody.Part filePart;

        if (hasFile.equals("1")) {

            MultipartBody.Part filePart1 = MultipartBody.Part.createFormData("fileUpload", fileUp.getName(), RequestBody.create(MediaType.parse(getMimeType(fileUp.getName())), fileUp));

            filePart = filePart1;

        } else {

            filePart = null;

        }

        Log.e("UP", "getName: " + getName);
        Log.e("UP", "getEmail: " + getEmail);
        Log.e("UP", "getPhonenumber " + getPhonenumber);
        Log.e("UP", "getProfession: " + getProfession);
        Log.e("UP", "getStatement: " + getStatement);
        Log.e("UP", "getBlog: " + getBlog);
        Log.e("UP", "getDob: " + getDob);
        Log.e("UP", "getGender: " + getGender);
        Log.e("UP", "hasFile: " + hasFile);
        Log.e("UP", "fileUp.getName(): " + fileUp.getName());
        Log.e("UP", "getMimeType(fileUp.getName()): " + getMimeType(fileUp.getName()));

        uploadData = apiInterface.updataProfileData(getName, getEmail, getPhonenumber, getProfession,
                getStatement, getBlog, getDob, getGender);

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

                    startActivity(new Intent(EditProfile.this, HomeActivity.class));

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedMediaUri = data.getData();

            if (selectedMediaUri.toString().contains("image") && requestCode == CODE_IMAGE) {
                //handle image

                file = new File(selectedMediaUri.toString());
                Glide.with(EditProfile.this).load(selectedMediaUri).into(civ);

                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    //imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    Glide.with(EditProfile.this).load(BitmapFactory.decodeFile(mediaPath)).into(civ);
                    cursor.close();
                    postPath = mediaPath;
                }

            }

        }

    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(url));
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            Log.i("mime", "" + type);
        }
        return type;
    }

}