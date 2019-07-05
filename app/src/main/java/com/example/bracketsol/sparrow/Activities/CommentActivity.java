package com.example.bracketsol.sparrow.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Adapter.CommentAdapter;
import com.example.bracketsol.sparrow.Model.CommentModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    CommentAdapter commentAdapter;
    ArrayList<CommentModel> commentArraylist;
    ImageView send_comment;
    RecyclerView coment_recyclerview;
    EditText coment_edittext;
    int getPostId,announcement_id;
    LinearLayoutManager manager;
    SwipeRefreshLayout swipe_comments;

    ApiInterface apiInterface;
    Call<ResponseBody> addComment;

    String api_to_call = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getPostId = getIntent().getIntExtra("post_id",-1);
        api_to_call = getIntent().getStringExtra("api");

        init();
        listener();
    }

    private void init() {

        swipe_comments = findViewById(R.id.swipe_comments);
        send_comment = findViewById(R.id.coment_send);
        coment_recyclerview= findViewById(R.id.rv_comment);
        coment_edittext= findViewById(R.id.commenttext);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        commentArraylist = new ArrayList<CommentModel>();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        coment_recyclerview.setLayoutManager(manager);

        /*commentArraylist.add(new CommentModel(12,2,17,"Ammar","wow nice picture, comment added","21:34","21:02","hello"));
        commentAdapter.notifyDataSetChanged();*/
        swipe_comments.setRefreshing(true);

        if (api_to_call.equals("post")) {

            getAllPostComments();

        } else {

            announcement_id = getPostId;
            getAllAnnouncementComments();

        }

        refreshListener();


    }

    private void refreshListener() {

        swipe_comments.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_comments.setRefreshing(true);
                commentArraylist.clear();

                if (api_to_call.equals("post")) {

                    getAllPostComments();

                } else {

                    getAllAnnouncementComments();

                }
            }
        });

    }

    private void getAllPostComments() {
        addComment = apiInterface.getPreviousPostComment(getPostId);
        Log.e("Com","Post Id:" + getPostId);
        addComment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject resJson = null;
                try {
                    swipe_comments.setRefreshing(false);

                    String resString = response.body().string();
                    Log.e("Com","Response: " + resString);

                    resJson = new JSONObject(resString);
                    JSONArray commentsArray =  resJson.getJSONArray("comments");

                    for(int i=0; i<commentsArray.length(); i++){

                        JSONObject jobj = commentsArray.getJSONObject(i);

                        int id = jobj.getInt("_id");
                        int user_id = jobj.getInt("user_id");
                        String user_name = jobj.getString("username");
                        String content = jobj.getString("content");
                        String updated_at = jobj.getString("updated_at");
                        String created_at = jobj.getString("created_at");
                        String picture_url = jobj.getString("picture_url");

                        commentArraylist.add(new CommentModel(id,user_id,getPostId,user_name,content,updated_at,created_at,"https://s3.amazonaws.com/social-funda-bucket/"+picture_url));

                    }

                    Log.e("Com","Comment Size: " + commentArraylist.size());
                    commentAdapter = new CommentAdapter(CommentActivity.this, commentArraylist);
                    coment_recyclerview.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();

                    String getmessage = resJson.getString("error");
                    String getmes = resJson.getString("message");
                    Log.i("TAG", "" + getmessage + getmes);
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipe_comments.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
                    swipe_comments.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe_comments.setRefreshing(false);
                Toast.makeText(CommentActivity.this, "Failed To Retrieve Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAllAnnouncementComments() {

        addComment = apiInterface.getPreviousAnnouncementComment(announcement_id);
        Log.e("Com","Post Id:" + announcement_id);
        addComment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject resJson = null;
                try {
                    swipe_comments.setRefreshing(false);

                    String resString = response.body().string();
                    Log.e("Com","Response: " + resString);

                    resJson = new JSONObject(resString);
                    JSONArray commentsArray =  resJson.getJSONArray("comments");

                    for(int i=0; i<commentsArray.length(); i++){

                        JSONObject jobj = commentsArray.getJSONObject(i);

                        int id = jobj.getInt("_id");
                        int user_id = jobj.getInt("user_id");
                        //String user_name = jobj.getString("username");
                        String content = jobj.getString("content");
                        String updated_at = jobj.getString("updated_at");
                        String created_at = jobj.getString("created_at");
                        //String picture_url = jobj.getString("picture_url");

                        commentArraylist.add(new CommentModel(id,user_id,announcement_id,"User Name",content,updated_at,created_at,"https://s3.amazonaws.com/social-funda-bucket/"+
                                "data:image/jpeg;base64,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"));

                    }

                    Log.e("Com","Comment Size: " + commentArraylist.size());
                    commentAdapter = new CommentAdapter(CommentActivity.this, commentArraylist);
                    coment_recyclerview.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();

                    String getmessage = resJson.getString("error");
                    String getmes = resJson.getString("message");
                    Log.i("TAG", "" + getmessage + getmes);
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipe_comments.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
                    swipe_comments.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe_comments.setRefreshing(false);
                Toast.makeText(CommentActivity.this, "Failed To Retrieve Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void listener() {
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coment_edittext.equals("")){

                }else{
                    Log.e("Com","Content: " + coment_edittext.getText().toString());
                    swipe_comments.setRefreshing(true);
                    commentArraylist.clear();

                    if (api_to_call.equals("post")) {

                        sendPostComment(coment_edittext.getText().toString());

                    } else {

                        sendAnnouncementComment(coment_edittext.getText().toString());

                    }
                }
            }
        });
    }

    private void sendPostComment(String toString) {
        addComment = apiInterface.addAnnouncementComment(getPostId ,coment_edittext.getText().toString());
        coment_edittext.setText("");
        addComment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject resJson = null;
                try {
                    String resString = response.body().string();
                    resJson = new JSONObject(resString);
                    String getmessage = resJson.getString("error");
                    String getmes = resJson.getString("message");
                    Log.i("TAG", "" + getmessage + getmes);
                    if (api_to_call.equals("post")) {

                        getAllPostComments();

                    } else {

                        getAllAnnouncementComments();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe_comments.setRefreshing(false);
                Toast.makeText(CommentActivity.this, "Failed To Retrieve Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendAnnouncementComment(String toString) {
        addComment = apiInterface.addAnnouncementComment(announcement_id ,coment_edittext.getText().toString());
        coment_edittext.setText("");
        addComment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject resJson = null;
                try {
                    String resString = response.body().string();
                    resJson = new JSONObject(resString);
                    String getmessage = resJson.getString("error");
                    String getmes = resJson.getString("message");
                    Log.i("TAG", "" + getmessage + getmes);
                    if (api_to_call.equals("post")) {

                        getAllPostComments();

                    } else {

                        getAllAnnouncementComments();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe_comments.setRefreshing(false);
                Toast.makeText(CommentActivity.this, "Failed To Retrieve Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
