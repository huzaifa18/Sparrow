package com.example.bracketsol.sparrow;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.Adapter.ColorsAdapter;
import com.example.bracketsol.sparrow.Model.ColorsModel;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePost extends AppCompatActivity{

    RecyclerView rv_colors;
    LinearLayoutManager llM;
    ColorsAdapter colorsAdapter;
    ArrayList<ColorsModel> arrColors;

    EditText editText;

    ExpandableLayout expandableLayout,expandableLayoutColors;

    Boolean expand = false;

    ConstraintLayout cl_main;

    ImageView colors_btn;

    ImageButton sendButton;

    ApiInterface apiInterface;

    ProgressBar progressBar;

    Drawable backGround;

    ImageView iv_photo;
    ImageView iv_video;

    final int CODE_IMAGE = 0;
    final int CODE_VIDEO = 1;

    File file;

    ImageView iv_image;
    VideoView vv_video;
    MediaPlayer mPlayer;

    Spinner sp_type_spinner;
    Spinner sp_announcement_type;

    ImageView close_btn;

    String selectedItem = "0";

    String postPath = "";

    TextView tv_startDate;
    TextView tv_endDate;

    String startdate = "";
    String enddate = "";
    String type = "";

    RelativeLayout relativeLayoutType;

    String hasFile = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            init();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {


        sp_type_spinner = findViewById(R.id.sp_type);
        String[] years = {"Post","Announcement"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(CreatePost.this, R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_type_spinner.setAdapter(langAdapter);

        sp_announcement_type = findViewById(R.id.sp_announcement_type);
        String[] types = {"Type1","Type 2","Type3","Type 4"};
        ArrayAdapter<CharSequence> typeAdapter = new ArrayAdapter<CharSequence>(CreatePost.this, R.layout.spinner_text, types );
        typeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_announcement_type.setAdapter(typeAdapter);

        cl_main = findViewById(R.id.cl_main);
        colors_btn = findViewById(R.id.colors_btn);
        rv_colors = findViewById(R.id.rv_colors);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        llM = new LinearLayoutManager(CreatePost.this, LinearLayoutManager.HORIZONTAL,false);
        rv_colors.setLayoutManager(llM);
        arrColors = new ArrayList<>();
        arrColors.add(new ColorsModel(getDrawable(R.drawable.background_splash_color)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg_minion)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg_hearts)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg_water_toons)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.black_white_gradient)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.blue_white_gradient)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg1)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg2)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.background_splash_color)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.black_white_gradient)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.blue_white_gradient)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg1)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg2)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.background_splash_color)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.black_white_gradient)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.blue_white_gradient)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg1)));
        arrColors.add(new ColorsModel(getDrawable(R.drawable.bg2)));

        colorsAdapter = new ColorsAdapter(CreatePost.this,arrColors);
        rv_colors.setAdapter(colorsAdapter);

        close_btn = findViewById(R.id.close_btn);

        editText = findViewById(R.id.editText);

        iv_image = findViewById(R.id.iv_image);
        vv_video = findViewById(R.id.vv_video);

        expandableLayout = findViewById(R.id.expandableLayout);
        expandableLayout.setOrientation(ExpandableLayout.VERTICAL);

        expandableLayoutColors = findViewById(R.id.expandableLayoutColors);
        expandableLayoutColors.setOrientation(ExpandableLayout.HORIZONTAL);

        sendButton = findViewById(R.id.sendbtn_toolbar);
        iv_photo = findViewById(R.id.photo);
        iv_video = findViewById(R.id.video);

        tv_startDate = findViewById(R.id.tv_startDate);
        tv_endDate = findViewById(R.id.tv_endDate);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        backGround = getDrawable(R.drawable.plain_bg);
        editText.setBackground(backGround);

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        relativeLayoutType = findViewById(R.id.relativeLayoutType);

        Animation rightSwipe = AnimationUtils.loadAnimation(CreatePost.this, R.anim.animate_left);
        Animation leftSwipe = AnimationUtils.loadAnimation(CreatePost.this, R.anim.animate_right);

        clickListeners();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void clickListeners() {

        colorsAdapter.setOnColorPickerClickListener(new ColorsAdapter.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(Drawable colorCode) {
                editText.setBackground(colorCode);
                backGround = colorCode;
                Log.e("TAG","Color Clicked: " + colorCode);
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!expandableLayoutColors.isExpanded()){
                    expandableLayoutColors.toggle();
                }
            }
        });

        colors_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotateAnim = AnimationUtils.loadAnimation(CreatePost.this, R.anim.rotate);
                colors_btn.setAnimation(rotateAnim);
                expandableLayoutColors.toggle();
            }
        });

        cl_main.setOnTouchListener(new OnSwipeTouchListener(CreatePost.this){
            public void onSwipeTop() {
                //Toast.makeText(CameraActivity.this, "top", Toast.LENGTH_SHORT).show();
                if (!expandableLayout.isExpanded()) {
                    expandableLayout.setDuration(500);
                    expandableLayout.setParallax(1);
                    expandableLayout.expand(true);
                    Log.e("TAG", "Up!");
                }
            }
            public void onSwipeRight() {
                //Toast.makeText(CameraActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                //Toast.makeText(CameraActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                //Toast.makeText(CameraActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                if (expandableLayout.isExpanded()) {
                    expandableLayout.setDuration(500);
                    expandableLayout.setParallax(0);
                    expandableLayout.collapse(true);
                    Log.e("TAG", "Down!");
                }
            }


        });

        expandableLayoutColors.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                //expandableLayoutColors.setRotation(expansionFraction);
            }
        });

        expandableLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG","Click!");
                validations();
            }
        });

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,CODE_IMAGE);
            }
        });

        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,CODE_VIDEO);
            }
        });

        vv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vv_video.requestFocus();

                if(vv_video!=null && !vv_video.isPlaying()){
                    vv_video.start();
                }

                if(vv_video!=null && vv_video.isPlaying()){
                    vv_video.stopPlayback();
                }

            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iv_image.getVisibility() == View.VISIBLE) {
                    iv_image.setVisibility(View.GONE);
                    rv_colors.setVisibility(View.VISIBLE);
                    colors_btn.setVisibility(View.VISIBLE);
                }

                if (vv_video.getVisibility() == View.VISIBLE) {
                    vv_video.setVisibility(View.GONE);
                    rv_colors.setVisibility(View.VISIBLE);
                    colors_btn.setVisibility(View.VISIBLE);
                }

                hasFile = "0";
                close_btn.setVisibility(View.GONE);

            }
        });

        sp_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG","Spinner: " + i);

                if (i == 0){

                    tv_startDate.setVisibility(View.GONE);
                    tv_endDate.setVisibility(View.GONE);
                    relativeLayoutType.setVisibility(View.GONE);

                } else {

                    tv_startDate.setVisibility(View.VISIBLE);
                    tv_endDate.setVisibility(View.VISIBLE);
                    relativeLayoutType.setVisibility(View.VISIBLE);

                }

                selectedItem = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tv_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar myCalendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        startdate = year+"-"+monthOfYear+"-"+dayOfMonth;

                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        tv_startDate.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreatePost.this,  datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        tv_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar myCalendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        enddate = year+"-"+monthOfYear+"-"+dayOfMonth;

                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        tv_endDate.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreatePost.this,  datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

    }

    private void validations() {

        if (editText.equals("")){

            editText.setError("This Field Cannot be Empty!");
            Log.e("TAG","Edit Text Empty!");

        } else if (backGround == null){

            Log.e("TAG","BackGround Null!");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backGround = getDrawable(R.drawable.plain_bg);
                validations();
            }

        } else if (selectedItem.equals("")){

            Log.e("TAG","Selected Item empty!");
        } else if (startdate.equals("") && selectedItem.equals("1")){

            tv_startDate.setError("Please Select Start Date");
            Log.e("TAG","Please Select Start Date");
        } else if (enddate.equals("") && selectedItem.equals("1")){
            tv_startDate.setError("Please Select End Date");
            Log.e("TAG","Please Select End Date");
        } else if (sp_announcement_type.getSelectedItem().toString().equals("Type") && selectedItem.equals("1")){
            Log.e("TAG","Announcement Type not Selected");
        } else if (selectedItem.equals("1")){
            Log.e("TAG","Announcement!");
            progressBar.setVisibility(View.VISIBLE);
            sendAnnouncement();

        } else {

            progressBar.setVisibility(View.VISIBLE);
            sendPost();

        }

    }

    private void sendPost() {

        Log.e("TAG","Send Post!");

        File fileUp = new File(postPath);

        MultipartBody.Part filePart;

        Log.e("TAG","postPath: " + postPath);
        Log.e("TAG","fileUp: " + fileUp.getName());
        Log.e("TAG","Mime Type: " + getMimeType(fileUp.getName()));
        Log.e("TAG","Type: " + String.valueOf(sp_type_spinner.getId()));
        Log.e("TAG","Content: " + editText.getText().toString());
        Log.e("TAG","hasFile: " + hasFile);
        Log.e("TAG","backGround: " + backGround.toString());

        if (hasFile.equals("1")) {

            MultipartBody.Part filePart1 = MultipartBody.Part.createFormData("fileUpload", fileUp.getName(), RequestBody.create(MediaType.parse(getMimeType(fileUp.getName())), fileUp));

            filePart = filePart1;

        } else {

            filePart = null;

        }

        Call<ResponseBody> call = apiInterface.sendPost(String.valueOf(sp_type_spinner.getId()), editText.getText().toString(),Integer.parseInt(hasFile),backGround.toString(),filePart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);

                try {

                    String responseStr = response.body().string();

                    Log.e("TAG","Response: " + responseStr);

                    JSONObject jsonObject = new JSONObject(responseStr);

                    String message = jsonObject.getString("message");

                    Log.e("TAG","Message: " + message);


                    Toast.makeText(CreatePost.this,"Message: " + message,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(CreatePost.this, HomeActivity.class));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("TAG","Error Response: " + t.getMessage());

            }
        });

    }

    private void sendAnnouncement() {

        Log.e("TAG","Send Announcement!");

        File fileUp = new File(postPath);

        MultipartBody.Part filePart;

        Log.e("TAG","postPath: " + postPath);
        Log.e("TAG","fileUp: " + fileUp.getName());
        Log.e("TAG","Mime Type: " + getMimeType(fileUp.getName()));
        Log.e("TAG","Type: " + String.valueOf(sp_type_spinner.getId()));
        Log.e("TAG","Content: " + editText.getText().toString());
        Log.e("TAG","hasFile: " + hasFile);
        Log.e("TAG","backGround: " + backGround.toString());
        Log.e("TAG","startdate: " + startdate);
        Log.e("TAG","enddate: " + enddate);

        if (hasFile.equals("1")) {

            MultipartBody.Part filePart1 = MultipartBody.Part.createFormData("fileUpload", fileUp.getName(), RequestBody.create(MediaType.parse(getMimeType(fileUp.getName())), fileUp));

            filePart = filePart1;

        } else {

            filePart = null;

        }

        //Call<ResponseBody> call = apiInterface.sendAnnouncement(sp_announcement_type.getSelectedItem().toString(), editText.getText().toString(),startdate,enddate, Integer.parseInt(hasFile), backGround.toString(),filePart);
        Call<ResponseBody> call = apiInterface.sendAnnouncement(editText.getText().toString(),startdate,enddate, Integer.parseInt(hasFile), 0,0,filePart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);

                try {

                    String responseStr = response.body().string();

                    Log.e("TAG","Response: " + responseStr);

                    JSONObject jsonObject = new JSONObject(responseStr);

                    String message = jsonObject.getString("message");

                    Log.e("TAG","Message: " + message);

                    Toast.makeText(CreatePost.this,"Message: " + message,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(CreatePost.this, HomeActivity.class));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("TAG","Error Response: " + t.getMessage());

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

                if (vv_video.getVisibility()==View.VISIBLE){
                    vv_video.setVisibility(View.GONE);
                }
                iv_image.setVisibility(View.VISIBLE);
                file = new File(selectedMediaUri.toString());
                Glide.with(CreatePost.this).load(selectedMediaUri).into(iv_image);

                editText.setBackground(getDrawable(R.drawable.plain_bg));
                rv_colors.setVisibility(View.GONE);
                colors_btn.setVisibility(View.GONE);

                close_btn.setVisibility(View.VISIBLE);

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
                    Glide.with(CreatePost.this).load(BitmapFactory.decodeFile(mediaPath)).into(iv_image);
                    cursor.close();


                    postPath = mediaPath;
                }

            } else if (selectedMediaUri.toString().contains("video") && requestCode == CODE_VIDEO) {
                //handle video

                editText.setBackground(getDrawable(R.drawable.plain_bg));
                rv_colors.setVisibility(View.GONE);

                colors_btn.setVisibility(View.GONE);

                if (iv_image.getVisibility()==View.VISIBLE){
                    iv_image.setVisibility(View.GONE);
                }

                vv_video.setVisibility(View.VISIBLE);
                file = new File(selectedMediaUri.toString());
                MediaController mc = new MediaController(CreatePost.this);
                vv_video.setMediaController(mc);

                vv_video.setVideoURI(selectedMediaUri);

                close_btn.setVisibility(View.VISIBLE);

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
                    Glide.with(CreatePost.this).load(BitmapFactory.decodeFile(mediaPath)).into(iv_image);
                    cursor.close();


                    postPath = mediaPath;
                }

            }

            hasFile = "1";

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