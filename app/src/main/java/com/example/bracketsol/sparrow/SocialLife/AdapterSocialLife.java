package com.example.bracketsol.sparrow.SocialLife;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
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

public class AdapterSocialLife extends RecyclerView.Adapter<AdapterSocialLife.MyViewHolder> {

    protected Itemlistenerinterface  itemlistener;
    ArrayList<ModelSocial> list;
    Context conl;
    private boolean islike = false;
    private boolean issave = false;
    ApiInterface apiInterface;
    Call<ResponseBody> hitLike,hitDisLike;


    public AdapterSocialLife(Context context, ArrayList<ModelSocial> list) {
        this.list = list;
        this.conl = context;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public AdapterSocialLife.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_social_life, viewGroup, false);

        return new AdapterSocialLife.MyViewHolder(itemView);
    }

    public void add(ModelSocial response) {
        list.add(response);
        notifyItemInserted(list.size() - 1);
    }

    private void remove(ModelSocial postItems) {
        int position = list.indexOf(postItems);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        add(new ModelSocial());
    }

    public void removeLoading() {
        int position = list.size() - 1;
        ModelSocial item = getItem(position);
        if (item != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    ModelSocial getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        ModelSocial item = list.get(i);

        myViewHolder.sender_name.setText(item.getSender_name());
        myViewHolder.total_likes.setText(String.valueOf(item.getLikes()));
        myViewHolder.total_comments.setText(String.valueOf(item.getComments()));
        myViewHolder.total_views.setText(String.valueOf(item.getTotal_views()));
        myViewHolder.content.setText(item.getStatement());

        myViewHolder.content.setText(item.getStatement());
        myViewHolder.sender_name.setText(item.getSender_name());
        myViewHolder.total_likes.setText("" + item.getLikes());
        myViewHolder.total_comments.setText("" + item.getComments());
        myViewHolder.total_views.setText("" + item.getTotal_views());

        if (islike) {

            myViewHolder.like.setImageResource(R.drawable.ic_like);
            myViewHolder.iv_like.setVisibility(View.VISIBLE);
            myViewHolder.iv_like.setImageResource(R.drawable.ic_like);
            myViewHolder.islike = true;

        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(conl, ""+list.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        myViewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(conl, CommentActivity.class);
                intent.putExtra("post_id",list.get(i).getSender_id());
                intent.putExtra("api","announcement");
                conl.startActivity(intent);
            }
        });

        myViewHolder.ll_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(conl, "like" + list.get(i).getSender_id(), Toast.LENGTH_SHORT).show();
                hitLikeBtn(myViewHolder,i);
            }
        });


        Glide.with(conl)
                .load(item.getProfile_pic())
                .into(myViewHolder.sender_pic);
        Glide.with(conl)
                .load(item.getUrl())
                .into(myViewHolder.attachment);

        myViewHolder.attachment.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(conl, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Log.i("TEST", "onDoubleTap");
                    Toast.makeText(conl, "like double tap", Toast.LENGTH_SHORT).show();
                    hitLikeBtn(myViewHolder,i);
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Itemlistenerinterface {
        void onItemClick(ModelSocial modelSocial);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView sender_pic;
        TextView sender_name, content, total_likes, total_comments, total_views;
        ImageButton more, like, coment, save;
        SquareImageView attachment;
        CardView cardView;
        ImageView iv_like;
        boolean islike = false;
        LinearLayout ll_like,ll_comment;
        VideoView vv_posted;

        ModelSocial item;


        public MyViewHolder(View view) {
            super(view);

            vv_posted = itemView.findViewById(R.id.vv_social_posted);
            ll_like = itemView.findViewById(R.id.ll_social_like);
            ll_comment = itemView.findViewById(R.id.ll_social_comment);
            iv_like = itemView.findViewById(R.id.iv_social_like);
            sender_pic = itemView.findViewById(R.id.post_social_uimg);
            sender_name = itemView.findViewById(R.id.username_social_post);
            total_likes = itemView.findViewById(R.id.like_social_tv);
            total_comments = itemView.findViewById(R.id.coment_social_tv);
            total_views = itemView.findViewById(R.id.views_social_tv);
            attachment = itemView.findViewById(R.id.posted_social_img);
            content = itemView.findViewById(R.id.content_social);

            more = itemView.findViewById(R.id.more_social_imgbtn);
            like = itemView.findViewById(R.id.like_social_imgbtn);
            coment = itemView.findViewById(R.id.coment_social_imgbtn);
            save = itemView.findViewById(R.id.save_imgbtn);

            cardView = itemView.findViewById(R.id.cardview);

        }

        public void setTotalLikes(String likes) {
            total_likes.setText(likes);

        }

        @Override
        public void onClick(View view) {
            if(itemlistener!=null){
            itemlistener.onItemClick(item);
            }

        }
    }

    public void hitLikeBtn(MyViewHolder holder, int position){
        if (holder.islike) {
            holder.like.setImageResource(R.drawable.ic_dislike);
            holder.iv_like.setVisibility(View.VISIBLE);
            holder.iv_like.setImageResource(R.drawable.ic_dislike);
            Animation grow = AnimationUtils.loadAnimation(conl,R.anim.grow_anim);
            holder.iv_like.setAnimation(grow);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.iv_like.setVisibility(View.GONE); }
            }, 500);
            holder.islike = false;
            //hitDisLikeApi(holder,position,holder.islike);
        } else {
            holder.like.setImageResource(R.drawable.ic_like);
            holder.iv_like.setVisibility(View.VISIBLE);
            holder.iv_like.setImageResource(R.drawable.ic_like);
            Animation grow = AnimationUtils.loadAnimation(conl,R.anim.grow_anim);
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

    public void hitLikeApi(MyViewHolder Apiholder, int position, final boolean like) {
        Log.i("TAG", "Announcement ID" + list.get(position).getAnnouncement_id());
        hitLike = apiInterface.hitLikeAnnouncement(list.get(position).getAnnouncement_id());
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

                        int TotalLike = list.get(position).getLikes();
                        //int updatelikes = ++TotalLike;
                        Log.i("TAG", "" + TotalLike);
                        Apiholder.setTotalLikes("" + TotalLike);

                        Apiholder.like.setImageResource(R.drawable.ic_like);
                        //islike = false;
                    } else {
                        int TotalLike = list.get(position).getLikes();
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
