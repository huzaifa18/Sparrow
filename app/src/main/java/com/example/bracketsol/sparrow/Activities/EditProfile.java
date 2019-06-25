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
import android.view.View;
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

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class EditProfile extends AppCompatActivity {

    EditText name, bio, blog, profession, dob, email;
    Button bt_boy;
    Button bt_girl;
    Boolean gender;
    File file;
    final int CODE_IMAGE = 0;
    TextView change_profile;
    String getName, getBio, getBlog, getProfession, getEmail;
    CircleImageView civ;
    String postPath = "";
    ImageView tick;

    ApiInterface apiInterface;
    Call<ResponseBody> uploadData;

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
        getBio = intent.getStringExtra("bio");
        getBlog = intent.getStringExtra("blog");
        getEmail = intent.getStringExtra("email");
        getProfession = intent.getStringExtra("profession");
    }

    private void init() {

        gender = true;
        bt_boy = findViewById(R.id.bt_boy);
        bt_girl = findViewById(R.id.bt_girl);
        tick = findViewById(R.id.iv_tick);

        name = findViewById(R.id.et_name);
        bio = findViewById(R.id.et_bio);
        blog = findViewById(R.id.et_blog);
        email = findViewById(R.id.et_email);
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

    }

    private void clickListeneres() {

        bt_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                uploadDataToServer();
            }
        });
    }

    private void uploadDataToServer() {
        // uploadData = apiInterface.updataProfileData(getName,getEmail,"032123234",getProfession,getBio,getBlog)
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

}