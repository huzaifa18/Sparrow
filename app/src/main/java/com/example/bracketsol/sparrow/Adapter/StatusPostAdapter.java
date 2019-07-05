package com.example.bracketsol.sparrow.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Activities.CommentActivity;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;
import com.yuyakaido.android.squareimageview.SquareImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusPostAdapter extends RecyclerView.Adapter<StatusPostAdapter.ViewHolder> {

    protected Itemlistenerinterface itemlistener;
    ArrayList<StatusPostingModel> statusarraylistAdapter;
    Context mContext;
    Dialog dialog;
    boolean issave = false;
    ApiInterface apiInterface;
    Call<ResponseBody> hitLike,hitDisLike;


    public StatusPostAdapter(Context mContext, ArrayList<StatusPostingModel> statusarraylistAdapter) {
        this.statusarraylistAdapter = statusarraylistAdapter;
        this.mContext = mContext;
        dialog = new Dialog(mContext);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    }

    @Override
    public StatusPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.status_posting_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(statusarraylistAdapter.get(position));
        StatusPostingModel statusPostingModel = statusarraylistAdapter.get(position);

        if (statusPostingModel.getAttachment_type().equals("image")){
            holder.attachment.setVisibility(View.VISIBLE);
            holder.vv_posted.setVisibility(View.GONE);
            Glide.with(mContext).load(statusarraylistAdapter.get(position).getAttachment()).into(holder.attachment);
        } else if (statusPostingModel.getAttachment_type().equals("video")){
            holder.attachment.setVisibility(View.GONE);
            holder.vv_posted.setVisibility(View.VISIBLE);
            Uri uri=Uri.parse(statusPostingModel.getAttachment());
            holder.vv_posted.setVideoURI(uri);
            holder.vv_posted.start();
            holder.vv_posted.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    holder.vv_posted.start();
                }
            });
        }
        if (statusPostingModel.getAttachment().equals("N-A")) {

            holder.attachment.setVisibility(View.GONE);
            holder.vv_posted.setVisibility(View.GONE);

        }

        Glide.with(mContext).load(statusarraylistAdapter.get(position).getSender_pic()).into(holder.sender_pic);

        Log.e("tag", "glide url" + statusarraylistAdapter.get(position).getSender_pic());
        //Glide.with(mContext).load("https://www.gstatic.com/webp/gallery3/3.png").into(holder.sender_pic);

        clickListeners(holder,position);
    }

    private void clickListeners(ViewHolder holder , int position) {

        holder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,CommentActivity.class);
                intent.putExtra("post_id",statusarraylistAdapter.get(position).getPost_id());
                mContext.startActivity(intent);
            }
        });

        holder.ll_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "like" + statusarraylistAdapter.get(position).getPost_id(), Toast.LENGTH_SHORT).show();
                hitLikeBtn(holder,position);
            }
        });


        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (issave) {
                    holder.save.setImageResource(R.drawable.ic_bookmark);
                    issave = false;
                } else {
                    holder.save.setImageResource(R.drawable.ic_bookmarked);
                    issave = true;
                }
            }
        });

        holder.attachment.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.i("TEST", "onDoubleTap");
                    Toast.makeText(mContext, "like double tap", Toast.LENGTH_SHORT).show();
                    hitLikeBtn(holder,position);
                    return super.onDoubleTap(e);
                }
                // implement here other callback methods like onFling, onScroll as necessary
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setMessage("asdf");

                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return statusarraylistAdapter.size();
    }

    public interface Itemlistenerinterface {
        void onItemClick(StatusPostingModel statusPostingModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView sender_pic;
        TextView sender_name, content, total_likes, total_comments, total_views;
        ImageButton more, like, coment, save;
        SquareImageView attachment;
        CardView cardView;
        ImageView iv_like;
        boolean islike = false;
        LinearLayout ll_like,ll_comment;
        VideoView vv_posted;

        StatusPostingModel item;


        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            vv_posted = itemView.findViewById(R.id.vv_posted);
            ll_like = itemView.findViewById(R.id.ll_like);
            ll_comment = itemView.findViewById(R.id.ll_comment);
            iv_like = itemView.findViewById(R.id.iv_like);
            sender_pic = itemView.findViewById(R.id.post_uimg);
            sender_name = itemView.findViewById(R.id.username_post);
            total_likes = itemView.findViewById(R.id.like_tv);
            total_comments = itemView.findViewById(R.id.coment_tv);
            total_views = itemView.findViewById(R.id.views_tv);
            attachment = itemView.findViewById(R.id.posted_img);
            content = itemView.findViewById(R.id.content);

            more = itemView.findViewById(R.id.more_imgbtn);
            like = itemView.findViewById(R.id.like_imgbtn);
            coment = itemView.findViewById(R.id.coment_imgbtn);
            save = itemView.findViewById(R.id.save_imgbtn);

            cardView = itemView.findViewById(R.id.cardview);

        }

        public void setData(StatusPostingModel statusPostingModel) {
            this.item = statusPostingModel;

            sender_name.setText(statusPostingModel.getSender_name());
            total_likes.setText(String.valueOf(statusPostingModel.getTotal_likes()));
            total_comments.setText(String.valueOf(statusPostingModel.getTotal_comments()));
            total_views.setText(String.valueOf(statusPostingModel.getTotal_views()));
            content.setText(statusPostingModel.getContent());
        }

        public void setTotalLikes(String likes) {
            total_likes.setText(likes);

        }

        @Override
        public void onClick(View view) {
            if (itemlistener != null) {
                itemlistener.onItemClick(item);
            }

        }
    }

    public void hitLikeBtn(ViewHolder holder, int position){
        if (holder.islike) {
            holder.like.setImageResource(R.drawable.ic_dislike);
            holder.iv_like.setVisibility(View.VISIBLE);
            holder.iv_like.setImageResource(R.drawable.ic_dislike);
            Animation grow = AnimationUtils.loadAnimation(mContext,R.anim.grow_anim);
            holder.iv_like.setAnimation(grow);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.iv_like.setVisibility(View.GONE); }
            }, 500);
            holder.islike = false;
            hitDisLikeApi(holder,position,holder.islike);
        } else {
            holder.like.setImageResource(R.drawable.ic_like);
            holder.iv_like.setVisibility(View.VISIBLE);
            holder.iv_like.setImageResource(R.drawable.ic_like);
            Animation grow = AnimationUtils.loadAnimation(mContext,R.anim.grow_anim);
            holder.iv_like.setAnimation(grow);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.iv_like.setVisibility(View.GONE); }
            }, 500);
            holder.islike = true;
            hitLikeApi(holder, position, holder.islike);
        }


    }

    public void hitLikeApi(ViewHolder Apiholder, int position, final boolean like) {
        hitLike = apiInterface.hitLike(statusarraylistAdapter.get(position).getPost_id());
        hitLike.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    String getmessage = resJson.getString("error");
                    String getmes = resJson.getString("message");
                    Log.i("TAG", "" + getmessage + getmes);

                    boolean islikes = like;
                    if (islikes) {

                        int TotalLike = statusarraylistAdapter.get(position).getTotal_likes();
                        //int updatelikes = ++TotalLike;
                        Log.i("TAG", "" + TotalLike);
                        Apiholder.setTotalLikes("" + TotalLike);

                        Apiholder.like.setImageResource(R.drawable.ic_like);
                        //islike = false;
                    } else {
                        int TotalLike = statusarraylistAdapter.get(position).getTotal_likes();
                        int updatelikes = --TotalLike;
                        Log.i("TAG", "" + TotalLike);
                        Apiholder.setTotalLikes("" + TotalLike);

                        Apiholder.like.setImageResource(R.drawable.ic_dislike);

                        //islike= true;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
    public void hitDisLikeApi(ViewHolder Apiholder, int position, final boolean like) {
        hitLike = apiInterface.hitDisLike(statusarraylistAdapter.get(position).getPost_id());
        hitLike.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    String getmessage = resJson.getString("error");
                    String getmes = resJson.getString("message");
                    Log.i("TAG", "" + getmessage + getmes);
                    Log.i("TAG", "" + "dislike");

                    boolean islikes = like;
                    if (islikes) {

                        int TotalLike = statusarraylistAdapter.get(position).getTotal_likes();
                        //int updatelikes = ++TotalLike;
                        Log.i("TAG", "" + TotalLike);
                        Apiholder.setTotalLikes("" + TotalLike);

                        Apiholder.like.setImageResource(R.drawable.ic_like);
                        //islike = false;
                    } else {
                        int TotalLike = statusarraylistAdapter.get(position).getTotal_likes();
                        int updatelikes = --TotalLike;
                        Log.i("TAG", "" + TotalLike);
                        Apiholder.setTotalLikes("" + TotalLike);

                        Apiholder.like.setImageResource(R.drawable.ic_dislike);

                        //islike= true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

}