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
    int getPostId;
    LinearLayoutManager manager;
    SwipeRefreshLayout swipe_comments;

    ApiInterface apiInterface;
    Call<ResponseBody> addComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getPostId = getIntent().getIntExtra("post_id",-1);
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
        getAllComment();

        refreshListener();

    }

    private void refreshListener() {

        swipe_comments.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_comments.setRefreshing(true);
                commentArraylist.clear();
                getAllComment();
            }
        });

    }

    private void getAllComment() {
        addComment = apiInterface.getPreviousComment(getPostId);
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

    private void listener() {
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coment_edittext.equals("")){

                }else{
                    Log.e("Com","Content: " + coment_edittext.getText().toString());
                    swipe_comments.setRefreshing(true);
                    sendComment(coment_edittext.getText().toString());
                }
            }
        });
    }

    private void sendComment(String toString) {
        addComment = apiInterface.addComment(getPostId ,coment_edittext.getText().toString());
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
                    getAllComment();
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
