package com.example.bracketsol.sparrow.MessageActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.BottomSheetGallery.BSImagePicker;
import com.example.bracketsol.sparrow.BottomSheetGallery.BSVideoPicker;
import com.example.bracketsol.sparrow.Emoticons.ChatListAdapter;
import com.example.bracketsol.sparrow.Emoticons.EmoticonsGridAdapter;
import com.example.bracketsol.sparrow.Emoticons.EmoticonsPagerAdapter;
import com.example.bracketsol.sparrow.MessageActivity.AudioRecorder.AudioRecordView;
import com.example.bracketsol.sparrow.MyApp;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.example.bracketsol.sparrow.SocketChat.MessageAdapter;
import com.example.bracketsol.sparrow.SocketChat.MessageFormat;
import com.example.bracketsol.sparrow.Utils.Prefs;
import com.example.bracketsol.sparrow.Volley.VolleyMultipartRequest;
import com.example.bracketsol.sparrow.webrtcc.CallActivity;
import com.example.bracketsol.sparrow.webrtcc.ConnectActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.bracketsol.sparrow.MyApp.getContext;
import static java.lang.Boolean.FALSE;

public class ChatActivityMain extends AppCompatActivity implements BSImagePicker.OnMultiImageSelectedListener, EmoticonsGridAdapter.KeyClickListener, BSVideoPicker.OnVideoSelectedListener {

    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "ChatActivityMain";
    //For emoticons
    private static final int NO_OF_EMOTICONS = 54;
    //RTC
    private static final int REMOVE_FAVORITE_INDEX = 0;
    private static final int CONNECTION_REQUEST = 1;
    private static final String[] PERMISSIONS_START_CALL = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};//WRITE_EXTERNAL_STORAGE, CAPTURE_VIDEO_OUTPUT
    private static final int PERMISSIONS_REQUEST_START_CALL = 101;
    private static boolean commandLineRun = false;
    //Audio file
    public MediaRecorder myAudioRecorder;
    ImageButton add_filebtn, cam_imgbtn;
    LinearLayout linearLayout;
    Animation bottomUp;
    Toolbar toolbar;
    int previousHeightDiffrence = 0;
    //RETROFIT
    ApiInterface apiInterface;
    //GETTING USERNAME,PROFILEURL,SENDERID
    String getuname;
    int getprofileurl, getsenderid, getreceiverid;
    //TOOLBAR
    TextView title_tv;
    BSImagePicker bsImagePicker;
    BSVideoPicker bsVideoPicker;
    ImageView img;
    String filename;
    String mime;
    String encImage;
    ProgressBar simpleProgressBar;
    LinearLayout imgLayout;
    String message;
    ImageButton vidImgbtn, imageButtonAudioCall;
    ConnectActivity connectActivity;
    LinearLayout imgvidLayout;
    Button adimg, advid;
    Uri mUri = null;
    ImageButton sendmsgBtn;
    ImageView iv;
    AudioRecordView audioRecordView;
    AudioRecordView audioRecordViewclass;
    String encodedImage = null;
    private String mUsername;
    private SharedPreferences mSharedPreferences;
    private ImageButton backbtn, sound_ib, vidcall;
    private LinearLayoutManager mLinearLayoutManager;
    //private EditText mMessageEditText;
    //bottom sheet gallery
    private ImageView ivImage1, ivImage2, ivImage3, ivImage4, ivImage5, ivImage6;
    private VideoView vid;
    private ListView chatList;
    private View popUpView;
    private ArrayList<Spanned> chats;
    private ChatListAdapter mAdapter;
    private LinearLayout emoticonsCover;
    private PopupWindow popupWindow;
    private int keyboardHeight;
    private EditText content;
    private RelativeLayout parentLayout;
    private boolean isKeyBoardVisible;
    private Bitmap[] emoticons;
    private EditText editTextMessage;
    Cursor cursor;
    //FOR SHOWING IN RECYCLERVIEW CHAT
    private MessageAdapter messageAdapter;
    //SOCKETIO
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("msg", "on new message");
                    Log.i(TAG, "run: ");
                    JSONObject data = (JSONObject) args[0];
                    //JSONObject fileObject = (JSONObject) args[0];
                    Log.i("msg", "Data on message recieve: " + data);
                    // Log.i("msg", "Data on message recieve: " + fileObject);
                    String content, sender_id, token;
                    int receiver_id;
                    String mimetype, recfilename, recincImage;

                    try {
                        mimetype = (data.getString("mimetype") != null) ? data.getString("mimetype") : "";
                        content = (data.getString("content") != null) ? data.getString("content") : "";
                        sender_id = data.getString("sender_id");
                        receiver_id = data.getInt("receiver_id");

//                        //socket thingsgi
//                         //fileObject = (data.getJSONObject("files") !=null ?  data.getJSONObject("files") : (JSONObject) JSONObject.NULL);
//                        recfilename = (fileObject.getString("name")!= null) ? fileObject.getString("name") : "";
//                        recincImage = (fileObject.getString("data")!= null) ? fileObject.getString("data") : "";

                        if (mimetype.equals("")) {
                            Log.i("mime", "empty");
                            try {
                                Log.i("getmsg", "mimetype.equals(\"\")");
                                Log.i("getmsg", "content:" + content);
                                Log.i("getmsg", "sender_id:" + sender_id);
                                Log.i("getmsg", "receiver_id:" + receiver_id);
                                MessageFormat format = new MessageFormat(receiver_id, getuname, message);
                                Log.i(TAG, "run:4 ");
                                messageAdapter.add(format);
                                Log.i(TAG, "run:5 ");
                                Log.i(TAG, "run: " + getuname + message + getsenderid);
                            } catch (Exception e) {
                                Log.i("getmsg", "mimetype.equals(\"\")");
                                Log.i("exc", "" + e.getMessage());
                            }

                        } else if (content.equals("")) {
                            Log.i("mime", "not");
                            try {
                                token = data.getString("token");
                                Log.i("getmsg", "content.equals(\"\")");
                                Log.i("getmsg", "sender_id:" + sender_id);
                                Log.i("getmsg", "receiver_id:" + receiver_id);
                                Log.i("getmsg", "mimetype:" + mimetype);
                                Log.i("getmsg", "token:" + token);

                                MessageFormat format = new MessageFormat(receiver_id, getuname);
                                Log.i(TAG, "run:4 ");
                                messageAdapter.add(format);
                                Log.i(TAG, "run:5 ");
                                Log.i(TAG, "run: " + getuname + message + getsenderid);
                            } catch (Exception e) {
                                Log.i("getmsg", "content.equals(\"\")");
                                Log.i("exc", "" + e.getMessage());
                            }
                        } else {
                            Log.i("mime", "not");
                            try {
                                token = data.getString("token");
                                Log.i("getmsg", "else");
                                Log.i("getmsg", "sender_id:" + sender_id);
                                Log.i("getmsg", "receiver_id:" + receiver_id);
                                Log.i("getmsg", "content:" + content);
                                Log.i("getmsg", "mimetype:" + mimetype);
                                Log.i("getmsg", "token:" + token);

                                MessageFormat format = new MessageFormat(receiver_id, getuname, message, mimetype);
                                Log.i(TAG, "run:4 ");
                                messageAdapter.add(format);
                                Log.i(TAG, "run:5 ");
                                Log.i(TAG, "run: " + getuname + message + getsenderid);
                            } catch (Exception e) {
                                Log.i("getmsg", "else");
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("getmsg", "main catch", e);
                        Log.i("getmsg", "main catch");
                        e.printStackTrace();
                    }


                    //for image
                    //JSONObject fileObject = (JSONObject) args[0];
//                    String mime2, filename, encImage;
//                    try {
//                        message = data.getString("content");
//                        int reciever_id = data.getInt("receiver_id");
//                        int token = data.getInt("token");
//                        int type = data.getInt("mimetype");
//                        Log.i("mimetype", "" + type);
//                        Log.i("mytoken", "" + token);
//                        Log.i("msg", "data" + data);
                    //for image
//                        fileObject = data.getJSONObject("files");
//                        if (fileObject.toString().equals("{}")) {
//                            Log.i("chkk", "empty");
//                            MessageFormat format = new MessageFormat(reciever_id, getuname, message);
//                            Log.i(TAG, "run:4 ");
//                            messageAdapter.add(format);
//                            Log.i(TAG, "run:5 ");
//                            Log.i(TAG, "run: " + getuname + message + getsenderid);
//                        } else {
//                            Log.i("chkk", "fill");
//                            mime2 = fileObject.getString("mimetype");
//                            filename = fileObject.getString("name");
//                            Log.i("msg", "filename" + filename);
//                            encImage = fileObject.getString("data");
//                            Log.i("msg", "enc" + encImage);
//                            MessageFormat format = new MessageFormat(reciever_id, getuname, message, encImage, mime2, filename);
//                            Log.i(TAG, "run:4 ");
//                            messageAdapter.add(format);
//                            Log.i(TAG, "run:5 ");
//
//                            Log.i(TAG, "run: " + getuname + message + getsenderid);
//
//                        }


//                    } catch (Exception e) {
//                        Log.i("exc", "" + e.getMessage());
//                        return;
//                    }
                }
            });
        }
    };
    private View imageViewSend, imageViewAudio;
    private String outputFile;
    private SharedPreferences sharedPref;
    private String keyprefResolution;
    private String keyprefFps;
    private String keyprefVideoBitrateType;
    private String keyprefVideoBitrateValue;
    private String keyprefAudioBitrateType;
    private String keyprefAudioBitrateValue;
    private String keyprefRoomServerUrl;
    private String keyprefRoom;
    private String keyprefRoomList;
    private ArrayList<String> roomList;
    private ArrayAdapter<String> adapter;
    private Boolean hasConnection = false;
    private ListView messageListView;
    private Thread thread2;
    private boolean startTyping = false;
    private int time = 2;
    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: typing stopped " + startTyping);
            if (time == 0) {
                setTitle("SocketIO");
                Log.i(TAG, "handleMessage: typing stopped time is " + time);
                startTyping = false;
                time = 2;
            }

        }
    };
    Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i(TAG, "run: " + args[0]);
                    try {
                        Boolean typingOrNot = data.getBoolean("typing");
                        String userName = data.getString("username") + " is Typing......";
                        String id = data.getString("uniqueId");

                        if (id.equals(getreceiverid)) {
                            typingOrNot = false;
                        } else {
                            setTitle(userName);
                        }

                        if (typingOrNot) {

                            if (!startTyping) {
                                startTyping = true;
                                thread2 = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                while (time > 0) {
                                                    synchronized (this) {
                                                        try {
                                                            wait(1000);
                                                            Log.i(TAG, "run: typing " + time);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        time--;
                                                    }
                                                    handler2.sendEmptyMessage(0);
                                                }

                                            }
                                        }
                                );
                                thread2.start();
                            } else {
                                time = 2;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Socket mSocket;
    private Intent startCallIntent;
    //rtc
    Emitter.Listener onNewCall = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("rtc", "on new call");
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String received = data.getString("type");
                        Log.i("rtc", "rtc " + received);

                        switch (received) {
                            case "offer":
                                Log.i("rtc", "" + data.getString("calle"));
                                Log.i("rtc", "" + Prefs.getUserFullNameFromPref(getContext()));
                                Log.i("rtc", "" + data.getString("caller"));
                                Log.i("calltype", "" + data.getString("calltype"));
                                if (data.getString("calle").equals(Prefs.getUserFullNameFromPref(getContext())) && data.getString("calltype").equals("video")) {
                                    //pop up show
                                    Log.i("rtc", "" + "insisder");
                                    // custom dialog
                                    final Dialog dialog = new Dialog(ChatActivityMain.this, R.style.DialogTheme);
                                    dialog.setContentView(R.layout.custom_dialog_rtc_video);
                                    dialog.setTitle("Title...");

                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialog.findViewById(R.id.video_text_name);
                                    text.setText("" + "Incoming call " + data.getString("caller"));
                                    ImageView image = (ImageView) dialog.findViewById(R.id.image);
                                    ImageButton dialogButton = dialog.findViewById(R.id.vid_accept);
                                    ImageButton dialogButtonreject = dialog.findViewById(R.id.vid_reject);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.i("rtc", "rtc alert dialog");
                                            connectToRoom(getuname, false, false, false, 0);
                                            try {
                                                JSONObject jsonObject = (JSONObject) args[0];
                                                jsonObject.put("type", "answer");
                                                jsonObject.put("caller", getuname);
                                                jsonObject.put("calle", Prefs.getUserFullNameFromPref(getContext()));
                                                mSocket.emit("rtc-manager", jsonObject);
                                                dialog.cancel();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    dialog.show();
                                    dialogButtonreject.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.i("rtc", "rtc alert dialog");
                                            JSONObject jsonObject = (JSONObject) args[0];
                                            mSocket.emit("rtc-manager", jsonObject);

                                            try {
                                                jsonObject.put("type", "leave");
                                                jsonObject.put("caller", getuname);
                                                jsonObject.put("calle", Prefs.getUserFullNameFromPref(getContext()));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            mSocket.emit("rtc-manager", jsonObject);
                                            dialog.cancel();
                                        }

                                    });

                                    dialog.show();
                                }


                                break;
                            case "answer":
                                if (data.getString("caller").equals(Prefs.getUserFullNameFromPref(getContext()))) {
                                    Log.i("rtc", "he answered");
                                }
                                break;
                            case "candidate":
                                break;
                            case "leave":

                                break;
                            case "cancel":
                                break;
                            default:
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    Emitter.Listener onNewAudioCall = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("rtc", "on new call");
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String received = data.getString("type");
                        Log.i("rtc", "rtc " + received);

                        switch (received) {
                            case "offer":
                                Log.i("rtc", "" + data.getString("calle"));
                                Log.i("rtc", "" + Prefs.getUserFullNameFromPref(getContext()));
                                Log.i("rtc", "" + data.getString("caller"));
                                Log.i("calltype", "" + data.getString("calltype"));

                                if (data.getString("calle").equals(Prefs.getUserFullNameFromPref(getContext())) && data.getString("calltype").equals("audio")) {
                                    //pop up show
                                    Log.i("rtc", "" + "insisder");
                                    // custom dialog
                                    final Dialog dialog = new Dialog(ChatActivityMain.this, R.style.DialogTheme);
                                    dialog.setContentView(R.layout.custom_dialog_rtc_audio);
                                    dialog.setTitle("Title...");

                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialog.findViewById(R.id.text_name);
                                    text.setText("" + data.getString("caller"));
                                    ImageView image = (ImageView) dialog.findViewById(R.id.image);

                                    ImageButton dialogButton = dialog.findViewById(R.id.accept);
                                    ImageButton dialogButtonreject = dialog.findViewById(R.id.reject);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.i("rtc", "rtc alert dialog");
                                            connectToRoomAudio(getuname, false, false, false, 0);
                                            try {
                                                JSONObject jsonObject = (JSONObject) args[0];
                                                jsonObject.put("type", "answer");
                                                jsonObject.put("caller", getuname);
                                                jsonObject.put("calle", Prefs.getUserFullNameFromPref(getContext()));
                                                mSocket.emit("rtc-manager", jsonObject);
                                                dialog.cancel();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    dialog.show();
                                    dialogButtonreject.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.i("rtc", "rtc alert dialog");
                                            JSONObject jsonObject = (JSONObject) args[0];
                                            mSocket.emit("rtc-manager", jsonObject);

                                            try {
                                                jsonObject.put("type", "leave");
                                                jsonObject.put("caller", getuname);
                                                jsonObject.put("calle", Prefs.getUserFullNameFromPref(getContext()));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            mSocket.emit("rtc-manager", jsonObject);
                                            dialog.cancel();
                                        }
                                    });
                                    dialog.show();
                                }
                                break;
                            case "answer":
                                if (data.getString("caller").equals(Prefs.getUserFullNameFromPref(getContext()))) {
                                    Log.i("rtc", "he answered");
                                }
                                break;
                            case "candidate":
                                break;
                            case "leave":

                                break;
                            case "cancel":
                                break;
                            default:
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    {
        try {
            mSocket = IO.socket("https://social-funda.herokuapp.com/");
        } catch (URISyntaxException e) {
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
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

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get setting keys.
        PreferenceManager.setDefaultValues(this, org.appspot.apprtc.R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        keyprefResolution = getString(org.appspot.apprtc.R.string.pref_resolution_key);
        keyprefFps = getString(org.appspot.apprtc.R.string.pref_fps_key);
        keyprefVideoBitrateType = getString(org.appspot.apprtc.R.string.pref_maxvideobitrate_key);
        keyprefVideoBitrateValue = getString(org.appspot.apprtc.R.string.pref_maxvideobitratevalue_key);
        keyprefAudioBitrateType = getString(org.appspot.apprtc.R.string.pref_startaudiobitrate_key);
        keyprefAudioBitrateValue = getString(org.appspot.apprtc.R.string.pref_startaudiobitratevalue_key);
        keyprefRoomServerUrl = getString(org.appspot.apprtc.R.string.pref_room_server_url_key);
        keyprefRoom = getString(org.appspot.apprtc.R.string.pref_room_key);
        keyprefRoomList = getString(org.appspot.apprtc.R.string.pref_room_list_key);

        setContentView(R.layout.activity_chat_main);
        imgLayout = findViewById(R.id.img_layout);
        linearLayout = (LinearLayout) findViewById(R.id.add_file_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;
        bsImagePicker = new BSImagePicker();
        bsVideoPicker = new BSVideoPicker();


        getPreviousUserData();
        init();
        vidCall();
        AudioCall();
        //connectActivity = new ConnectActivity();

        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }
        if (hasConnection) {

        } else {
            mSocket.connect();
            mSocket.on("private chat message", onNewMessage);
            mSocket.on("on typing", onTyping);
            mSocket.on("rtc-manager", onNewCall);
            mSocket.on("rtc-manager", onNewAudioCall);


            //GETTING USERNAME,PROFILEURL,SENDERID,RECEIVERID
            getuname = getIntent().getStringExtra("username");
            getprofileurl = getIntent().getIntExtra("profileurl", -1);
            getsenderid = getIntent().getIntExtra("senderid", -1);
            getreceiverid = getIntent().getIntExtra("receiverid", -1);

            JSONObject userId = new JSONObject();
            try {
                userId.put("room1", getsenderid);
                userId.put("room2", getreceiverid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit("join private chat", userId);
            //mSocket.emit("online", userId);

        }

        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;


        Log.i(TAG, "onCreate: " + getuname + " " + "Connected");

        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;

        messageListView = findViewById(R.id.chat_list);

        List<MessageFormat> messageFormatListt = new ArrayList<>();
        messageAdapter = new MessageAdapter(ChatActivityMain.this, R.layout.item_message, messageFormatListt);
        messageListView.setAdapter(messageAdapter);
        onTypeButtonEnable();
        getAllMessages();
        listeners();
        //for emoticons
        chatList = (ListView) findViewById(R.id.chat_list);
        parentLayout = (RelativeLayout) findViewById(R.id.list_parent);
        emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
        popUpView = getLayoutInflater().inflate(R.layout.emoticons_popup, null);
        // Setting adapter for chat list
        chats = new ArrayList<Spanned>();
        mAdapter = new ChatListAdapter(getApplicationContext(), chats);
        chatList.setAdapter(mAdapter);
        chatList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
                return false;
            }
        });
        // Defining default height of keyboard which is equal to 230 dip
        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);

        // Showing and Dismissing pop up on clicking emoticons button
        ImageView emoticonsButton = (ImageView) findViewById(R.id.emoticons_button);
        emoticonsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!popupWindow.isShowing()) {
                    popupWindow.setHeight((int) (keyboardHeight));
                    if (isKeyBoardVisible) {
                        emoticonsCover.setVisibility(LinearLayout.GONE);
                    } else {
                        emoticonsCover.setVisibility(LinearLayout.VISIBLE);
                    }
                    popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);

                } else {
                    popupWindow.dismiss();
                }

            }
        });

        readEmoticons();
        enablePopUpView();
        checkKeyboardHeight(parentLayout);
        enableFooterView();
        // If an implicit VIEW intent is launching the app, go directly to that URL.
        final Intent intent = getIntent();
        if ("android.intent.action.VIEW".equals(intent.getAction()) && !commandLineRun) {
            boolean loopback = intent.getBooleanExtra(CallActivity.EXTRA_LOOPBACK, false);
            int runTimeMs = intent.getIntExtra(CallActivity.EXTRA_RUNTIME, 0);
            boolean useValuesFromIntent =
                    intent.getBooleanExtra(CallActivity.EXTRA_USE_VALUES_FROM_INTENT, false);
            String room = sharedPref.getString(keyprefRoom, "");
            connectToRoom(room, true, loopback, useValuesFromIntent, runTimeMs);
        }
    }

    public void AudioCall() {
        imageButtonAudioCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToRoomAudio(Prefs.getUserFullNameFromPref(getContext()), false, false, false, 0);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("type", "offer");
                    jsonObject.put("calle", getuname);
                    jsonObject.put("caller", Prefs.getUserFullNameFromPref(getContext()));
                    jsonObject.put("calltype", "audio");
                    mSocket.emit("rtc-manager", jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void vidCall() {
        vidImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToRoom(Prefs.getUserFullNameFromPref(getContext()), false, false, false, 0);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("type", "offer");
                    jsonObject.put("calle", getuname);
                    jsonObject.put("caller", Prefs.getUserFullNameFromPref(getContext()));
                    jsonObject.put("calltype", "video");
                    mSocket.emit("rtc-manager", jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void init() {
        imgvidLayout = findViewById(R.id.add_imge_vid_layout);

        //webrtc
        sendmsgBtn = findViewById(R.id.ssendButton);
        vidImgbtn = findViewById(R.id.vid_img_btn);
        imageButtonAudioCall = findViewById(R.id.audio_img_btn);
        simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        //mSendButton = findViewById(R.id.ssendButton);

        imageViewSend = findViewById(R.id.imageViewSend);
        imageViewAudio = findViewById(R.id.imageViewAudio);
        audioRecordViewclass = new AudioRecordView(ChatActivityMain.this);
        audioRecordViewclass.setupRecording();
        editTextMessage = (EditText) findViewById(R.id.messageEditText);
        add_filebtn = findViewById(R.id.add_filebtn);
        cam_imgbtn = findViewById(R.id.cam_imgbtn);
        bottomUp = (Animation) AnimationUtils.loadAnimation(this,
                R.anim.right_enter);
        backbtn = findViewById(R.id.back_btn);
        vidcall = findViewById(R.id.vidcall_ib);
        //sound_ib = findViewById(R.id.soun_record_ib);

        //bottom sheet gallery code
        ivImage1 = findViewById(R.id.iv_image1);
        vid = findViewById(R.id.video_view);
        ivImage3 = findViewById(R.id.iv_image3);
        ivImage4 = findViewById(R.id.iv_image4);
        ivImage5 = findViewById(R.id.iv_image5);
        ivImage6 = findViewById(R.id.iv_image6);

        //GET ALL MESSAGES INIT
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //TOOLBAR title set name
        title_tv = findViewById(R.id.title_home);
        getuname = getIntent().getStringExtra("username");
        title_tv.setText(getuname);
        editTextMessage = findViewById(R.id.editTextMessage);
//        //
//        myAudioRecorder = new MediaRecorder();
//        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        myAudioRecorder.setOutputFile(outputFile);
        ClickListeners();


    }

    private void ClickListeners() {
        sendmsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextMessage.getText().equals("")) {
                    if (encodedImage != null) {
                        Toast.makeText(ChatActivityMain.this, "image send", Toast.LENGTH_SHORT).show();
                        sendMessage("Image");
                    } else {
                        sendMessage("message");
                    }
                } else {
                    Toast.makeText(ChatActivityMain.this, "Please write something", Toast.LENGTH_SHORT).show();
                    if (!encodedImage.isEmpty()) {
                        Toast.makeText(ChatActivityMain.this, "Empty", Toast.LENGTH_SHORT).show();
                        sendMessage("Image");
                    }
                }
                encodedImage = null;
            }
        });
    }

    private void getPreviousUserData() {

    }

    private void getAllMessages() {
        //GETTING USERNAME,PROFILEURL,SENDERID,RECEIVERID
        getuname = getIntent().getStringExtra("username");
        getprofileurl = getIntent().getIntExtra("profileurl", -1);
        getsenderid = getIntent().getIntExtra("senderid", -1);
        getreceiverid = getIntent().getIntExtra("receiverid", -1);

        //Toast.makeText(this, "Sid : " + getsenderid + getuname + getprofileurl + "Rid : " + getreceiverid, Toast.LENGTH_SHORT).show();

        int id = -1;

        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {

            id = getreceiverid;

        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {

            id = getsenderid;

        }

        Log.e("msg", "id: " + id);

        Prefs.getUserIDFromPref(getContext());
        Call<ResponseBody> call = apiInterface.getSpecificMessage(Prefs.getUserIDFromPref(getContext()), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String resString = response.body().string();
                    Log.e("TAG", "res: " + response.body().string());
                    JSONObject resJson = new JSONObject(resString);
                    Log.e("TAG", "ok");


                    JSONArray array = resJson.getJSONArray("messages");
                    List<MessageFormat> messageFormatList = new ArrayList<>();
                    List<MessageFormat> messageFormatLis = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        Log.e("TAG", "sender_id" + product.getInt("sender_id"));
                        Log.e("TAG", "receiver_id" + product.getInt("receiver_id"));
                        Log.e("TAG", "content" + product.getString("content"));
                        //Log.e("TAG", "url" + product.getString("url"));
                        //Log.e("TAG", "is_read" + product.getString("is_read"));
                        Log.e("TAG", "created_at" + product.getString("created_at"));


                        //getting sendername

                        int sender_id = product.getInt("sender_id");
                        int receiver_id = product.getInt("receiver_id");
                        //String sender_name = product.getString("sender_name");
                        //String receiver_name = product.getString("receiver_name");
                        String content = product.getString("content");
                        String url = product.getString("url");
                        String created_at = product.getString("created_at");

                        int getuserid = Prefs.getUserIDFromPref(ChatActivityMain.this);
                        Log.i("userid", "" + getuserid);
                        Log.i("url", "" + url);
                        String sender_name = "azeem";
                        String receiver_name = "huzaifa";
                        MessageFormat messageFormat;
                        if (url.equals("N-A")) {
                            messageFormat = new MessageFormat(receiver_id,
                                    (getuserid == sender_id) ? receiver_name : sender_name,
                                    content);
                            simpleProgressBar.setVisibility(View.GONE);
                            messageFormatList.add(messageFormat);

                        } else {
                            messageFormat = new MessageFormat(receiver_id,
                                    (getuserid == sender_id) ? receiver_name : sender_name,
                                    content, "https://s3.amazonaws.com/social-funda-bucket/" + url);
                            simpleProgressBar.setVisibility(View.GONE);
                            messageFormatList.add(messageFormat);
                        }
                    }
                    Log.e("TAG", "ok" + array);

                    //chatList.smoothScrollToPosition(0);
                    //chatList.scrollTo(0, messageAdapter.getCount() - 1);


                    messageAdapter = new MessageAdapter(ChatActivityMain.this, R.layout.item_message, messageFormatList);
                    chatList.setAdapter(messageAdapter);


                    // Toast.makeText(ChatActivityMain.this, "" + resJson.getString("message"), Toast.LENGTH_LONG).show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("TAG", "Exception onresponse" + e.getMessage());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "checkval onresponse" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    public void onTypeButtonEnable() {
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                JSONObject onTyping = new JSONObject();
                try {
                    onTyping.put("typing", true);
                    onTyping.put("username", getuname);
                    onTyping.put("uniqueId", getsenderid);
                    mSocket.emit("on typing", onTyping);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    private void listeners() {
        cam_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //linearLayout.removeAllViews();
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(ChatActivityMain.this, "camera butotn", Toast.LENGTH_SHORT).show();
            }
        });

        editTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
            }
        });

        add_filebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgvidLayout.setVisibility(View.VISIBLE);
                adimg = findViewById(R.id.img_select);
                advid = findViewById(R.id.vid_select);

                adimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                                .setMaximumDisplayingImages(Integer.MAX_VALUE)
                                .isMultiSelect()
                                .setMinimumMultiSelectCount(1)
                                .setMaximumMultiSelectCount(10)
                                .build();
                        pickerDialog.show(getSupportFragmentManager(), "picker");
                    }
                });
                advid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BSVideoPicker pickerDialog = new BSVideoPicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                                .setMaximumDisplayingImages(Integer.MAX_VALUE)
                                .isMultiSelect()
                                .setMinimumMultiSelectCount(1)
                                .setMaximumMultiSelectCount(1)
                                .build();
                        pickerDialog.show(getSupportFragmentManager(), "picker");
                    }
                });


            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        sound_ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void causeCrash() {
        throw new NullPointerException("Fake null pointer exception");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void keyClickedIndex(final String index) {

        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                StringTokenizer st = new StringTokenizer(index, ".");
                Drawable d = new BitmapDrawable(getResources(), emoticons[Integer.parseInt(st.nextToken()) - 1]);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };

        Spanned cs = Html.fromHtml("<img src ='" + index + "'/>", imageGetter, null);

        int cursorPosition = content.getSelectionStart();
        content.getText().insert(cursorPosition, cs);
    }

    //for emojicon
    private void changeKeyboardHeight(int height) {

        if (height > 100) {
            keyboardHeight = height;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, keyboardHeight);
            emoticonsCover.setLayoutParams(params);
        }
    }

    /**
     * Reading all emoticons in local cache
     */
    private void readEmoticons() {

        emoticons = new Bitmap[NO_OF_EMOTICONS];
        for (short i = 0; i < NO_OF_EMOTICONS; i++) {
            emoticons[i] = getImage((i + 1) + ".png");
        }
    }

    /**
     * Enabling all content in footer i.e. post window
     */
    private void enableFooterView() {

        content = (EditText) findViewById(R.id.messageEditText);
        content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();

                }
            }
        });
//        final ImageButton postButton = (ImageButton) findViewById(R.id.ssendButton);
//        //sendMessage(postButton);
//        postButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendMessage();
//            }
//        });
    }

    /**
     * For loading smileys from assets
     */
    private Bitmap getImage(String path) {
        AssetManager mngr = getAssets();
        InputStream in = null;
        try {
            in = mngr.open("emoticons/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap temp = BitmapFactory.decodeStream(in, null, null);
        return temp;
    }

    /**
     * Defining all components of emoticons keyboard
     */
    private void enablePopUpView() {

        ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
        pager.setOffscreenPageLimit(3);

        ArrayList<String> paths = new ArrayList<String>();

        for (short i = 1; i <= NO_OF_EMOTICONS; i++) {
            paths.add(i + ".png");
        }

        EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(ChatActivityMain.this, paths, this);
        pager.setAdapter(adapter);

        // Creating a pop window for emoticons keyboard
        popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) keyboardHeight, false);

        ImageButton backSpace = popUpView.findViewById(R.id.back);
        backSpace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                content.dispatchKeyEvent(event);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                emoticonsCover.setVisibility(LinearLayout.GONE);
            }
        });
    }

    private void checkKeyboardHeight(final View parentLayout) {

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parentLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = parentLayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);

                        if (previousHeightDiffrence - heightDifference > 50) {
                            popupWindow.dismiss();
                        }

                        previousHeightDiffrence = heightDifference;
                        if (heightDifference > 100) {

                            isKeyBoardVisible = true;
                            changeKeyboardHeight(heightDifference);

                        } else {

                            isKeyBoardVisible = false;

                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    public void sendOnlyMessage() {
        Log.i(TAG, "sendMessage: ");
        String message = editTextMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "sendMessage:2 ");
            return;
        }
        editTextMessage.setText("");
        int id = -1;
        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {
            id = getreceiverid;
        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {
            id = getsenderid;

        }

        Log.e("msg", "id: " + id);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sender_id", Prefs.getUserIDFromPref(getContext()));
            jsonObject.put("receiver_id", id);
            jsonObject.put("content", message);
            jsonObject.put("mimetype", "");
            //jsonObject.put("files", []);
            Log.i("sendmessage", "json object send: " + jsonObject);
            mSocket.emit("private chat", jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "sendMessage: 1" + mSocket.emit("chat message", jsonObject));
    }


    private void sendWithVideo() {


    }

    private void sendWithVideoApi() {

        Log.e("TAG","Send Video!");

        File fileUp = new File(String.valueOf(mUri));

        MultipartBody.Part filePart;

        Log.e("TAG","postPath: " + mUri);
        Log.e("TAG","fileUp: " + fileUp.getName());
        Log.e("TAG","Mime Type: " + getMimeType(fileUp.getName()));

        MultipartBody.Part filePart1 = MultipartBody.Part.createFormData("fileUpload", fileUp.getName(), RequestBody.create(MediaType.parse(getMimeType(fileUp.getName())), fileUp));

        filePart = filePart1;

        Call<ResponseBody> call = apiInterface.sendMessagevideo(filePart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String responseStr = response.body().string();
                    Log.e("TAG","Response: " + responseStr);
                    JSONObject jsonObject = new JSONObject(responseStr);
                    String message = jsonObject.getString("message");
                    Log.e("TAG","Message: " + message);
                    Toast.makeText(ChatActivityMain.this,"Message: " + message,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG","Error Response: " + t.getMessage());
            }
        });
    }

    private void sendWithImage() {
        Log.i("imageBTN", "sendWithImage: ");
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String token = "" + Prefs.getUserIDFromPref(MyApp.getContext()) + ts;
        int id = -1;
        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {
            id = getreceiverid;
        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {
            id = getsenderid;
        }
        String message = editTextMessage.getText().toString().trim();
        JSONObject fileObject = new JSONObject();
        if (TextUtils.isEmpty(message)) {
            Log.i("image", "sendWithImage:2 ");
            Log.e("imageBTN", "id: " + id);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("sender_id", Prefs.getUserIDFromPref(getContext()));
                jsonObject.put("receiver_id", id);
                jsonObject.put("token", token);
                jsonObject.put("mimetype", mime);
                jsonObject.put("content", message);
                //image data
                fileObject.put("mimetype", mime);
                fileObject.put("name", filename);
                fileObject.put("data", encImage);

                jsonObject.put("files", fileObject);

                Log.i("imageBTN", "json object send: " + jsonObject);
                mSocket.emit("private chat", jsonObject);
                mSocket.emit("chat message", jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "sendMessage: 1" + mSocket.emit("chat message", jsonObject));
            return;
        } else {
            Log.i("image", "sendWithImage:2 ");
            Log.e("imageBTN", "id: " + id);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("sender_id", Prefs.getUserIDFromPref(getContext()));
                jsonObject.put("receiver_id", id);
                jsonObject.put("content", message);
                jsonObject.put("token", token);
                jsonObject.put("mimetype", mime);

                //image data
                fileObject.put("mimetype", mime);
                fileObject.put("name", filename);
                fileObject.put("data", encImage);
                Log.i("imageBTN", "json object send: " + jsonObject);
                mSocket.emit("private chat", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        //TODO send message over api

    }


    @Override
    public void onMultiImageSelected(final List<Uri> messageFormatList, String tag) {
        for (int i = 0; i < messageFormatList.size(); i++) {
            if (i >= 6) return;

            switch (i) {
                case 0:
                    iv = ivImage1;
                    break;
                case 1:
                    iv = ivImage2;
                    break;
                case 2:
                    iv = ivImage3;
                    break;
                case 3:
                    iv = ivImage4;
                    break;
                case 4:
                    iv = ivImage5;
                    break;
                case 5:
                default:
                    iv = ivImage6;
            }

            Log.i("list", "index: " + messageFormatList.get(i).getPath());
            imgLayout.setVisibility(View.VISIBLE);
            imageViewSend.setVisibility(View.VISIBLE);
            imageViewSend.animate().scaleX(1f).scaleY(1f).setDuration(100).setInterpolator(new LinearInterpolator()).start();
            //imageViewAudio.setVisibility(View.GONE);

            File file = new File(messageFormatList.get(i).getPath());
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            Glide.with(this).load(messageFormatList.get(i)).into(iv);
            File getmimefile = new File(String.valueOf(messageFormatList.get(i)));
            String filenamee = getmimefile.getName();
            mime = getMimeType(filenamee);


            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(messageFormatList.get(i));
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                encodedImage = encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final String finalEncodedImage = encodedImage;
            Log.i("file", "encodedImage: " + encodedImage + "finalEncodedImage: " + finalEncodedImage + "filename: " + filename + "file: " + file);
        }
    }

    public void refresh() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] b = baos.toByteArray();
        encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }


    public void sendMessage(String check) {
        imageViewSend.setVisibility(View.GONE);
        Log.i("msg", "send btn ok" + check);
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String token = "" + Prefs.getUserIDFromPref(MyApp.getContext()) + ts;
        linearLayout.setVisibility(View.GONE);
        message = editTextMessage.getText().toString().trim();
        int id = -1;
        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {
            id = getreceiverid;
        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {
            id = getsenderid;
        }
        Log.e("msg", "id: " + id);
        JSONObject fileObject = new JSONObject();
        if (check.equals("message")) {
            Log.i("msg", "message: " + check);
            sendOnlyMessage();
            sendOnlyMessageApi();
        } else if (check.equals("video")) {
            Log.i("msg", "video :" + check);
            sendWithVideo();
            sendWithVideoApi();
        } else if (check.equals("Image")) {
            Log.i("msg", "Image :" + check);
            sendWithImage();
            sendImageApi();
//            sendMessageFile();

            if (imgLayout.getVisibility() == View.VISIBLE) {
                imgLayout.setVisibility(View.GONE);
            }
        }

//            jsonObject.put("files", fileObject);
//
//            jsonObject.put("sender_id", Prefs.getUserIDFromPref(getContext()));
//            jsonObject.put("receiver_id", id);
//            jsonObject.put("content", message);
//            jsonObject.put("token", token);
//            jsonObject.put("mimetype", mime);
//            Log.i(TAG, "json object send: " + jsonObject);
//            mSocket.emit("private chat", jsonObject);
//            mSocket.emit("chat message", jsonObject);

        editTextMessage.setText("");

    }

    private void sendMessageFile() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String token = "" + Prefs.getUserIDFromPref(MyApp.getContext()) + ts;
        int id = -1;
        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {
            id = getreceiverid;
        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {
            id = getsenderid;
        }


//        Call<ResponseBody> callSendfiles = apiInterface.sendMessageWithFile(message, Prefs.getUserIDFromPref(getContext()), id, token, filename);
        Toast.makeText(ChatActivityMain.this, "" + message + Prefs.getUserIDFromPref(getContext()) + id + token + mUri, Toast.LENGTH_SHORT).show();
//        callSendfiles.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String resString = response.body().string();
//                    JSONObject resJson = new JSONObject(resString);
//                    if (resJson.getString("error").equals("0")) {
//                        Log.e("sendapi", "message send");
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("sendapi", "checkval onresponse" + e.getMessage());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("sendapi", "checkval onresponse" + e.getMessage());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

    }

    private void sendOnlyMessageApi() {
        int id = -1;
        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {
            id = getreceiverid;
        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {
            id = getsenderid;
        }
        Call<ResponseBody> callSend = apiInterface.sendMessage(message, Prefs.getUserIDFromPref(getContext()), id);

        callSend.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    if (resJson.getString("error").equals("0")) {
                        Log.e("sendapi", "message send");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("sendapi", "checkval onresponse" + e.getMessage());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("sendapi", "checkval onresponse" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @SuppressWarnings("StringSplitter")
    public void connectToRoom(String roomId, boolean commandLineRun, boolean loopback,
                              boolean useValuesFromIntent, int runTimeMs) {
        ChatActivityMain.commandLineRun = commandLineRun;

        // roomId is random for loopback.
        if (loopback) {
            roomId = Integer.toString((new Random()).nextInt(100000000));
        }

        String roomUrl = sharedPref.getString(
                keyprefRoomServerUrl, getString(org.appspot.apprtc.R.string.pref_room_server_url_default));

        // Video call enabled flag.
        boolean videoCallEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_videocall_key,
                CallActivity.EXTRA_VIDEO_CALL, org.appspot.apprtc.R.string.pref_videocall_default, useValuesFromIntent);

        // Use screencapture option.
        boolean useScreencapture = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_screencapture_key,
                CallActivity.EXTRA_SCREENCAPTURE, org.appspot.apprtc.R.string.pref_screencapture_default, useValuesFromIntent);

        // Use Camera2 option.
        boolean useCamera2 = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_camera2_key, CallActivity.EXTRA_CAMERA2,
                org.appspot.apprtc.R.string.pref_camera2_default, useValuesFromIntent);

        // Get default codecs.
        String videoCodec = sharedPrefGetString(org.appspot.apprtc.R.string.pref_videocodec_key,
                CallActivity.EXTRA_VIDEOCODEC, org.appspot.apprtc.R.string.pref_videocodec_default, useValuesFromIntent);
        String audioCodec = sharedPrefGetString(org.appspot.apprtc.R.string.pref_audiocodec_key,
                CallActivity.EXTRA_AUDIOCODEC, org.appspot.apprtc.R.string.pref_audiocodec_default, useValuesFromIntent);

        // Check HW codec flag.
        boolean hwCodec = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_hwcodec_key,
                CallActivity.EXTRA_HWCODEC_ENABLED, org.appspot.apprtc.R.string.pref_hwcodec_default, useValuesFromIntent);

        // Check Capture to texture.
        boolean captureToTexture = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_capturetotexture_key,
                CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, org.appspot.apprtc.R.string.pref_capturetotexture_default,
                useValuesFromIntent);

        // Check FlexFEC.
        boolean flexfecEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_flexfec_key,
                CallActivity.EXTRA_FLEXFEC_ENABLED, org.appspot.apprtc.R.string.pref_flexfec_default, useValuesFromIntent);

        // Check Disable Audio Processing flag.
        boolean noAudioProcessing = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_noaudioprocessing_key,
                CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, org.appspot.apprtc.R.string.pref_noaudioprocessing_default,
                useValuesFromIntent);

        boolean aecDump = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_aecdump_key,
                CallActivity.EXTRA_AECDUMP_ENABLED, org.appspot.apprtc.R.string.pref_aecdump_default, useValuesFromIntent);

        boolean saveInputAudioToFile =
                sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_enable_save_input_audio_to_file_key,
                        CallActivity.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED,
                        org.appspot.apprtc.R.string.pref_enable_save_input_audio_to_file_default, useValuesFromIntent);

        // Check OpenSL ES enabled flag.
        boolean useOpenSLES = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_opensles_key,
                CallActivity.EXTRA_OPENSLES_ENABLED, org.appspot.apprtc.R.string.pref_opensles_default, useValuesFromIntent);

        // Check Disable built-in AEC flag.
        boolean disableBuiltInAEC = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_disable_built_in_aec_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, org.appspot.apprtc.R.string.pref_disable_built_in_aec_default,
                useValuesFromIntent);

        // Check Disable built-in AGC flag.
        boolean disableBuiltInAGC = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_disable_built_in_agc_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, org.appspot.apprtc.R.string.pref_disable_built_in_agc_default,
                useValuesFromIntent);

        // Check Disable built-in NS flag.
        boolean disableBuiltInNS = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_disable_built_in_ns_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_NS, org.appspot.apprtc.R.string.pref_disable_built_in_ns_default,
                useValuesFromIntent);

        // Check Disable gain control
        boolean disableWebRtcAGCAndHPF = sharedPrefGetBoolean(
                org.appspot.apprtc.R.string.pref_disable_webrtc_agc_and_hpf_key, CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF,
                org.appspot.apprtc.R.string.pref_disable_webrtc_agc_and_hpf_key, useValuesFromIntent);

        // Get video resolution from settings.
        int videoWidth = 0;
        int videoHeight = 0;
        if (useValuesFromIntent) {
            videoWidth = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_WIDTH, 0);
            videoHeight = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_HEIGHT, 0);
        }
        if (videoWidth == 0 && videoHeight == 0) {
            String resolution =
                    sharedPref.getString(keyprefResolution, getString(org.appspot.apprtc.R.string.pref_resolution_default));
            String[] dimensions = resolution.split("[ x]+");
            if (dimensions.length == 2) {
                try {
                    videoWidth = Integer.parseInt(dimensions[0]);
                    videoHeight = Integer.parseInt(dimensions[1]);
                } catch (NumberFormatException e) {
                    videoWidth = 0;
                    videoHeight = 0;
                    Log.e(TAG, "Wrong video resolution setting: " + resolution);
                }
            }
        }

        // Get camera fps from settings.
        int cameraFps = 0;
        if (useValuesFromIntent) {
            cameraFps = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_FPS, 0);
        }
        if (cameraFps == 0) {
            String fps = sharedPref.getString(keyprefFps, getString(org.appspot.apprtc.R.string.pref_fps_default));
            String[] fpsValues = fps.split("[ x]+");
            if (fpsValues.length == 2) {
                try {
                    cameraFps = Integer.parseInt(fpsValues[0]);
                } catch (NumberFormatException e) {
                    cameraFps = 0;
                    Log.e(TAG, "Wrong camera fps setting: " + fps);
                }
            }
        }

        // Check capture quality slider flag.
        boolean captureQualitySlider = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_capturequalityslider_key,
                CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                org.appspot.apprtc.R.string.pref_capturequalityslider_default, useValuesFromIntent);

        // Get video and audio start bitrate.
        int videoStartBitrate = 0;
        if (useValuesFromIntent) {
            videoStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_BITRATE, 0);
        }
        if (videoStartBitrate == 0) {
            String bitrateTypeDefault = getString(org.appspot.apprtc.R.string.pref_maxvideobitrate_default);
            String bitrateType = sharedPref.getString(keyprefVideoBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefVideoBitrateValue, getString(org.appspot.apprtc.R.string.pref_maxvideobitratevalue_default));
                videoStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        int audioStartBitrate = 0;
        if (useValuesFromIntent) {
            audioStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_AUDIO_BITRATE, 0);
        }
        if (audioStartBitrate == 0) {
            String bitrateTypeDefault = getString(org.appspot.apprtc.R.string.pref_startaudiobitrate_default);
            String bitrateType = sharedPref.getString(keyprefAudioBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefAudioBitrateValue, getString(org.appspot.apprtc.R.string.pref_startaudiobitratevalue_default));
                audioStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        // Check statistics display option.
        boolean displayHud = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_displayhud_key,
                CallActivity.EXTRA_DISPLAY_HUD, org.appspot.apprtc.R.string.pref_displayhud_default, useValuesFromIntent);

        boolean tracing = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_tracing_key, CallActivity.EXTRA_TRACING,
                org.appspot.apprtc.R.string.pref_tracing_default, useValuesFromIntent);

        // Check Enable RtcEventLog.
        boolean rtcEventLogEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_enable_rtceventlog_key,
                CallActivity.EXTRA_ENABLE_RTCEVENTLOG, org.appspot.apprtc.R.string.pref_enable_rtceventlog_default,
                useValuesFromIntent);

        boolean useLegacyAudioDevice = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_use_legacy_audio_device_key,
                CallActivity.EXTRA_USE_LEGACY_AUDIO_DEVICE, org.appspot.apprtc.R.string.pref_use_legacy_audio_device_default,
                useValuesFromIntent);

        // Get datachannel options
        boolean dataChannelEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_enable_datachannel_key,
                CallActivity.EXTRA_DATA_CHANNEL_ENABLED, org.appspot.apprtc.R.string.pref_enable_datachannel_default,
                useValuesFromIntent);
        boolean ordered = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_ordered_key, CallActivity.EXTRA_ORDERED,
                org.appspot.apprtc.R.string.pref_ordered_default, useValuesFromIntent);
        boolean negotiated = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_negotiated_key,
                CallActivity.EXTRA_NEGOTIATED, org.appspot.apprtc.R.string.pref_negotiated_default, useValuesFromIntent);
        int maxRetrMs = sharedPrefGetInteger(org.appspot.apprtc.R.string.pref_max_retransmit_time_ms_key,
                CallActivity.EXTRA_MAX_RETRANSMITS_MS, org.appspot.apprtc.R.string.pref_max_retransmit_time_ms_default,
                useValuesFromIntent);
        int maxRetr =
                sharedPrefGetInteger(org.appspot.apprtc.R.string.pref_max_retransmits_key, CallActivity.EXTRA_MAX_RETRANSMITS,
                        org.appspot.apprtc.R.string.pref_max_retransmits_default, useValuesFromIntent);
        int id = sharedPrefGetInteger(org.appspot.apprtc.R.string.pref_data_id_key, CallActivity.EXTRA_ID,
                org.appspot.apprtc.R.string.pref_data_id_default, useValuesFromIntent);
        String protocol = sharedPrefGetString(org.appspot.apprtc.R.string.pref_data_protocol_key,
                CallActivity.EXTRA_PROTOCOL, org.appspot.apprtc.R.string.pref_data_protocol_default, useValuesFromIntent);

        // Start AppRTCMobile activity.
        Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
        if (validateUrl(roomUrl)) {
            Uri uri = Uri.parse(roomUrl);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setData(uri);
            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
            intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
            intent.putExtra(CallActivity.EXTRA_SCREENCAPTURE, useScreencapture);
            intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, captureQualitySlider);
            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
            intent.putExtra(CallActivity.EXTRA_FLEXFEC_ENABLED, flexfecEnabled);
            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing);
            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
            intent.putExtra(CallActivity.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED, saveInputAudioToFile);
            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
            intent.putExtra(CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF);
            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
            intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
            intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
            intent.putExtra(CallActivity.EXTRA_ENABLE_RTCEVENTLOG, rtcEventLogEnabled);
            intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
            intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);
            intent.putExtra(CallActivity.EXTRA_USE_LEGACY_AUDIO_DEVICE, useLegacyAudioDevice);

            intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);

            if (dataChannelEnabled) {
                intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
                intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
                intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
                intent.putExtra(CallActivity.EXTRA_ID, id);
            }

            if (useValuesFromIntent) {
                if (getIntent().hasExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA)) {
                    String videoFileAsCamera =
                            getIntent().getStringExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA);
                    intent.putExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
                    String saveRemoteVideoToFile =
                            getIntent().getStringExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE, saveRemoteVideoToFile);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
                    int videoOutWidth =
                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, 0);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, videoOutWidth);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
                    int videoOutHeight =
                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, 0);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, videoOutHeight);
                }
            }

            startCallActivity(intent);
        }
    }

    @SuppressWarnings("StringSplitter")
    public void connectToRoomAudio(String roomId, boolean commandLineRun, boolean loopback,
                                   boolean useValuesFromIntent, int runTimeMs) {
        ChatActivityMain.commandLineRun = commandLineRun;

        // roomId is random for loopback.
        if (loopback) {
            roomId = Integer.toString((new Random()).nextInt(100000000));
        }

        String roomUrl = sharedPref.getString(
                keyprefRoomServerUrl, getString(org.appspot.apprtc.R.string.pref_room_server_url_default));

        // Video call enabled flag.
        boolean videoCallEnabled = FALSE;

        // Use screencapture option.
        boolean useScreencapture = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_screencapture_key,
                CallActivity.EXTRA_SCREENCAPTURE, org.appspot.apprtc.R.string.pref_screencapture_default, useValuesFromIntent);

        // Use Camera2 option.
        boolean useCamera2 = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_camera2_key, CallActivity.EXTRA_CAMERA2,
                org.appspot.apprtc.R.string.pref_camera2_default, useValuesFromIntent);

        // Get default codecs.
        String videoCodec = sharedPrefGetString(org.appspot.apprtc.R.string.pref_videocodec_key,
                CallActivity.EXTRA_VIDEOCODEC, org.appspot.apprtc.R.string.pref_videocodec_default, useValuesFromIntent);
        String audioCodec = sharedPrefGetString(org.appspot.apprtc.R.string.pref_audiocodec_key,
                CallActivity.EXTRA_AUDIOCODEC, org.appspot.apprtc.R.string.pref_audiocodec_default, useValuesFromIntent);

        // Check HW codec flag.
        boolean hwCodec = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_hwcodec_key,
                CallActivity.EXTRA_HWCODEC_ENABLED, org.appspot.apprtc.R.string.pref_hwcodec_default, useValuesFromIntent);

        // Check Capture to texture.
        boolean captureToTexture = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_capturetotexture_key,
                CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, org.appspot.apprtc.R.string.pref_capturetotexture_default,
                useValuesFromIntent);

        // Check FlexFEC.
        boolean flexfecEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_flexfec_key,
                CallActivity.EXTRA_FLEXFEC_ENABLED, org.appspot.apprtc.R.string.pref_flexfec_default, useValuesFromIntent);

        // Check Disable Audio Processing flag.
        boolean noAudioProcessing = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_noaudioprocessing_key,
                CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, org.appspot.apprtc.R.string.pref_noaudioprocessing_default,
                useValuesFromIntent);

        boolean aecDump = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_aecdump_key,
                CallActivity.EXTRA_AECDUMP_ENABLED, org.appspot.apprtc.R.string.pref_aecdump_default, useValuesFromIntent);

        boolean saveInputAudioToFile =
                sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_enable_save_input_audio_to_file_key,
                        CallActivity.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED,
                        org.appspot.apprtc.R.string.pref_enable_save_input_audio_to_file_default, useValuesFromIntent);

        // Check OpenSL ES enabled flag.
        boolean useOpenSLES = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_opensles_key,
                CallActivity.EXTRA_OPENSLES_ENABLED, org.appspot.apprtc.R.string.pref_opensles_default, useValuesFromIntent);

        // Check Disable built-in AEC flag.
        boolean disableBuiltInAEC = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_disable_built_in_aec_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, org.appspot.apprtc.R.string.pref_disable_built_in_aec_default,
                useValuesFromIntent);

        // Check Disable built-in AGC flag.
        boolean disableBuiltInAGC = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_disable_built_in_agc_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, org.appspot.apprtc.R.string.pref_disable_built_in_agc_default,
                useValuesFromIntent);

        // Check Disable built-in NS flag.
        boolean disableBuiltInNS = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_disable_built_in_ns_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_NS, org.appspot.apprtc.R.string.pref_disable_built_in_ns_default,
                useValuesFromIntent);

        // Check Disable gain control
        boolean disableWebRtcAGCAndHPF = sharedPrefGetBoolean(
                org.appspot.apprtc.R.string.pref_disable_webrtc_agc_and_hpf_key, CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF,
                org.appspot.apprtc.R.string.pref_disable_webrtc_agc_and_hpf_key, useValuesFromIntent);

        // Get video resolution from settings.
        int videoWidth = 0;
        int videoHeight = 0;
        if (useValuesFromIntent) {
            videoWidth = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_WIDTH, 0);
            videoHeight = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_HEIGHT, 0);
        }
        if (videoWidth == 0 && videoHeight == 0) {
            String resolution =
                    sharedPref.getString(keyprefResolution, getString(org.appspot.apprtc.R.string.pref_resolution_default));
            String[] dimensions = resolution.split("[ x]+");
            if (dimensions.length == 2) {
                try {
                    videoWidth = Integer.parseInt(dimensions[0]);
                    videoHeight = Integer.parseInt(dimensions[1]);
                } catch (NumberFormatException e) {
                    videoWidth = 0;
                    videoHeight = 0;
                    Log.e(TAG, "Wrong video resolution setting: " + resolution);
                }
            }
        }

        // Get camera fps from settings.
        int cameraFps = 0;
        if (useValuesFromIntent) {
            cameraFps = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_FPS, 0);
        }
        if (cameraFps == 0) {
            String fps = sharedPref.getString(keyprefFps, getString(org.appspot.apprtc.R.string.pref_fps_default));
            String[] fpsValues = fps.split("[ x]+");
            if (fpsValues.length == 2) {
                try {
                    cameraFps = Integer.parseInt(fpsValues[0]);
                } catch (NumberFormatException e) {
                    cameraFps = 0;
                    Log.e(TAG, "Wrong camera fps setting: " + fps);
                }
            }
        }

        // Check capture quality slider flag.
        boolean captureQualitySlider = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_capturequalityslider_key,
                CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                org.appspot.apprtc.R.string.pref_capturequalityslider_default, useValuesFromIntent);

        // Get video and audio start bitrate.
        int videoStartBitrate = 0;
        if (useValuesFromIntent) {
            videoStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_BITRATE, 0);
        }
        if (videoStartBitrate == 0) {
            String bitrateTypeDefault = getString(org.appspot.apprtc.R.string.pref_maxvideobitrate_default);
            String bitrateType = sharedPref.getString(keyprefVideoBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefVideoBitrateValue, getString(org.appspot.apprtc.R.string.pref_maxvideobitratevalue_default));
                videoStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        int audioStartBitrate = 0;
        if (useValuesFromIntent) {
            audioStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_AUDIO_BITRATE, 0);
        }
        if (audioStartBitrate == 0) {
            String bitrateTypeDefault = getString(org.appspot.apprtc.R.string.pref_startaudiobitrate_default);
            String bitrateType = sharedPref.getString(keyprefAudioBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefAudioBitrateValue, getString(org.appspot.apprtc.R.string.pref_startaudiobitratevalue_default));
                audioStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        // Check statistics display option.
        boolean displayHud = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_displayhud_key,
                CallActivity.EXTRA_DISPLAY_HUD, org.appspot.apprtc.R.string.pref_displayhud_default, useValuesFromIntent);

        boolean tracing = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_tracing_key, CallActivity.EXTRA_TRACING,
                org.appspot.apprtc.R.string.pref_tracing_default, useValuesFromIntent);

        // Check Enable RtcEventLog.
        boolean rtcEventLogEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_enable_rtceventlog_key,
                CallActivity.EXTRA_ENABLE_RTCEVENTLOG, org.appspot.apprtc.R.string.pref_enable_rtceventlog_default,
                useValuesFromIntent);

        boolean useLegacyAudioDevice = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_use_legacy_audio_device_key,
                CallActivity.EXTRA_USE_LEGACY_AUDIO_DEVICE, org.appspot.apprtc.R.string.pref_use_legacy_audio_device_default,
                useValuesFromIntent);

        // Get datachannel options
        boolean dataChannelEnabled = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_enable_datachannel_key,
                CallActivity.EXTRA_DATA_CHANNEL_ENABLED, org.appspot.apprtc.R.string.pref_enable_datachannel_default,
                useValuesFromIntent);
        boolean ordered = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_ordered_key, CallActivity.EXTRA_ORDERED,
                org.appspot.apprtc.R.string.pref_ordered_default, useValuesFromIntent);
        boolean negotiated = sharedPrefGetBoolean(org.appspot.apprtc.R.string.pref_negotiated_key,
                CallActivity.EXTRA_NEGOTIATED, org.appspot.apprtc.R.string.pref_negotiated_default, useValuesFromIntent);
        int maxRetrMs = sharedPrefGetInteger(org.appspot.apprtc.R.string.pref_max_retransmit_time_ms_key,
                CallActivity.EXTRA_MAX_RETRANSMITS_MS, org.appspot.apprtc.R.string.pref_max_retransmit_time_ms_default,
                useValuesFromIntent);
        int maxRetr =
                sharedPrefGetInteger(org.appspot.apprtc.R.string.pref_max_retransmits_key, CallActivity.EXTRA_MAX_RETRANSMITS,
                        org.appspot.apprtc.R.string.pref_max_retransmits_default, useValuesFromIntent);
        int id = sharedPrefGetInteger(org.appspot.apprtc.R.string.pref_data_id_key, CallActivity.EXTRA_ID,
                org.appspot.apprtc.R.string.pref_data_id_default, useValuesFromIntent);
        String protocol = sharedPrefGetString(org.appspot.apprtc.R.string.pref_data_protocol_key,
                CallActivity.EXTRA_PROTOCOL, org.appspot.apprtc.R.string.pref_data_protocol_default, useValuesFromIntent);

        // Start AppRTCMobile activity.
        Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
        if (validateUrl(roomUrl)) {
            Uri uri = Uri.parse(roomUrl);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setData(uri);
            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
            intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
            intent.putExtra(CallActivity.EXTRA_SCREENCAPTURE, useScreencapture);
            intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, captureQualitySlider);
            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
            intent.putExtra(CallActivity.EXTRA_FLEXFEC_ENABLED, flexfecEnabled);
            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing);
            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
            intent.putExtra(CallActivity.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED, saveInputAudioToFile);
            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
            intent.putExtra(CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF);
            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
            intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
            intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
            intent.putExtra(CallActivity.EXTRA_ENABLE_RTCEVENTLOG, rtcEventLogEnabled);
            intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
            intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);
            intent.putExtra(CallActivity.EXTRA_USE_LEGACY_AUDIO_DEVICE, useLegacyAudioDevice);

            intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);

            if (dataChannelEnabled) {
                intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
                intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
                intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
                intent.putExtra(CallActivity.EXTRA_ID, id);
            }

            if (useValuesFromIntent) {
                if (getIntent().hasExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA)) {
                    String videoFileAsCamera =
                            getIntent().getStringExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA);
                    intent.putExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
                    String saveRemoteVideoToFile =
                            getIntent().getStringExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE, saveRemoteVideoToFile);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
                    int videoOutWidth =
                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, 0);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, videoOutWidth);
                }

                if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
                    int videoOutHeight =
                            getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, 0);
                    intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, videoOutHeight);
                }
            }

            startCallActivity(intent);
        }
    }

    private boolean validateUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true;
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle(getText(org.appspot.apprtc.R.string.invalid_url_title))
                .setMessage(getString(org.appspot.apprtc.R.string.invalid_url_text, url))
                .setCancelable(false)
                .setNeutralButton(org.appspot.apprtc.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .create()
                .show();
        return false;
    }

    private String sharedPrefGetString(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultValue = getString(defaultId);
        if (useFromIntent) {
            String value = getIntent().getStringExtra(intentName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getString(attributeName, defaultValue);
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private boolean sharedPrefGetBoolean(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.parseBoolean(getString(defaultId));
        if (useFromIntent) {
            return getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private int sharedPrefGetInteger(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultString = getString(defaultId);
        int defaultValue = Integer.parseInt(defaultString);
        if (useFromIntent) {
            return getIntent().getIntExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            String value = sharedPref.getString(attributeName, defaultString);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Wrong setting for: " + attributeName + ":" + value);
                return defaultValue;
            }
        }
    }

    private void startCallActivity(Intent intent) {
        if (!hasPermissions(this, PERMISSIONS_START_CALL)) {
            startCallIntent = intent;
            ActivityCompat.requestPermissions(this, PERMISSIONS_START_CALL, PERMISSIONS_REQUEST_START_CALL);
            return;
        }
        startActivityForResult(intent, CONNECTION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_START_CALL: {
                if (hasPermissions(this, PERMISSIONS_START_CALL)) {
                    // permission was granted, yay!
                    if (startCallIntent != null)
                        startActivityForResult(startCallIntent, CONNECTION_REQUEST);
                } else {
                    Toast.makeText(this, "Required permissions denied.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String room = sharedPref.getString(keyprefRoom, "");
        roomList = new ArrayList<>();
        String roomListJson = sharedPref.getString(keyprefRoomList, null);
        if (roomListJson != null) {
            try {
                JSONArray jsonArray = new JSONArray(roomListJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    roomList.add(jsonArray.get(i).toString());
                }
            } catch (JSONException e) {
                Log.e(TAG, "Failed to load room list: " + e.toString());
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomList);
    }

    @Override
    public void OnVideoSelected(List<Uri> messageFormatList, String tag) {
        for (int i = 0; i < messageFormatList.size(); i++) {
            if (i >= 6) return;
            VideoView iv;
            switch (i) {
                case 0:
                    iv = vid;
                    break;
                default:
                    iv = vid;
            }
            imageViewSend.setVisibility(View.VISIBLE);
            imageViewSend.animate().scaleX(1f).scaleY(1f).setDuration(100).setInterpolator(new LinearInterpolator()).start();

            if(messageFormatList.size()>0){
                // Get the Image from data
                Uri selectedImage = messageFormatList.get(i);
                Log.i("TAG","message url" + messageFormatList.get(i));
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                //imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                //Glide.with(CreatePost.this).load(BitmapFactory.decodeFile(mediaPath)).into(iv_image);
                cursor.close();
                mUri= Uri.parse(mediaPath);
            }

            mUri = messageFormatList.get(i);
            Log.i("list", "videoindex: " + messageFormatList.get(i));
            Log.i("list", "videoindex: " + mUri);
            imgLayout.setVisibility(View.VISIBLE);
            Toast.makeText(ChatActivityMain.this, "" + mUri, Toast.LENGTH_SHORT).show();


            sendMessage("video");

            File file = new File(String.valueOf(messageFormatList.get(i)));
            filename = file.getName();
            mime = getMimeType(filename);

        }

    }

    //volley

    public void sendImageApi() {
        int id = -1;
        if (Prefs.getUserIDFromPref(getContext()) == getsenderid) {
            id = getreceiverid;
        } else if (Prefs.getUserIDFromPref(getContext()) == getreceiverid) {
            id = getsenderid;
        }
        //our custom volley request
        int finalId = id;
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ApiClient.Base_URL + "api/messages/chat/",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        /*progress_logo.clearAnimation();
                        progress_logo.setVisibility(View.GONE);

                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        Log.i("volley", "Register Response: " + response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            jsonObject.getString("message");
                            jsonObject.getString("error");
                            Log.i("volley", "message" + jsonObject.getString("message"));
                            Log.i("volley", "error" + jsonObject.getString("error"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Error Message: " + error.getMessage());
            }
        }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             */


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.i("volley", "" + Prefs.getUserToken(ChatActivityMain.this));
                params.put("authorization", "Bearer " + Prefs.getUserToken(ChatActivityMain.this));

                return params;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Log.i("volley", "params");
                Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                params.put("content", editTextMessage.getText().toString());
                params.put("sender_id", String.valueOf(Prefs.getUserIDFromPref(MyApp.getContext())));
                params.put("receiver_id", String.valueOf(finalId));
                params.put("token", "234234");
                params.put("mimetype", mime);


                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Log.i("volley", "image");
                Log.e("TAG", "Image 1: param ");
                Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                params.put("msgData", new DataPart(imagename + ".png", imageInByte));

                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


}
