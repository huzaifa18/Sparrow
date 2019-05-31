package com.example.bracketsol.sparrow.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.R;
import com.yuyakaido.android.squareimageview.SquareImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusPostAdapter extends RecyclerView.Adapter<StatusPostAdapter.ViewHolder> {

    protected Itemlistenerinterface itemlistener;
    ArrayList<StatusPostingModel> statusarraylistAdapter;
    Context mContext;
    Dialog dialog;
    private boolean islike = false;
    private boolean issave = false;


    public StatusPostAdapter(Context mContext, ArrayList<StatusPostingModel> statusarraylistAdapter) {
        this.statusarraylistAdapter = statusarraylistAdapter;
        this.mContext = mContext;
        dialog = new Dialog(mContext);
    }

    @Override
    public StatusPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.status_posting_row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(statusarraylistAdapter.get(position));
        StatusPostingModel statusPostingModel = statusarraylistAdapter.get(position);

        Glide.with(mContext).load("https://s3.amazonaws.com/social-funda-bucket/" +statusarraylistAdapter.get(position).getSender_pic()).into(holder.sender_pic);
        Glide.with(mContext).load("https://s3.amazonaws.com/social-funda-bucket/" +statusarraylistAdapter.get(position).getAttachment()).into(holder.sender_pic);
        Glide.with(mContext).load("https://s3.amazonaws.com/social-funda-bucket/" +statusarraylistAdapter.get(position).getSender_pic()).into(holder.sender_pic);
        Glide.with(mContext).load("https://s3.amazonaws.com/social-funda-bucket/" +statusarraylistAdapter.get(position).getSender_pic()).into(holder.sender_pic);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "like", Toast.LENGTH_SHORT).show();
                if (islike) {
                    holder.like.setImageResource(R.drawable.ic_dislike);
                    islike = false;
                } else {
                    holder.like.setImageResource(R.drawable.ic_like);
                    islike = true;
                }
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
        TextView sender_name, content,total_likes,total_comments,total_views;
        ImageButton more, like, coment, save;
        SquareImageView attachment;
        CardView cardView;

        StatusPostingModel item;


        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);



            sender_pic= itemView.findViewById(R.id.post_uimg);
            sender_name= itemView.findViewById(R.id.username_post);
            total_likes= itemView.findViewById(R.id.like_tv);
            total_comments= itemView.findViewById(R.id.coment_tv);
            total_views= itemView.findViewById(R.id.views_tv);
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
            total_likes.setText(statusPostingModel.getTotal_likes());
            total_comments.setText(statusPostingModel.getTotal_comments());
            total_likes.setText(statusPostingModel.getTotal_views());
            content.setText(statusPostingModel.getContent());
        }

        @Override
        public void onClick(View view) {
            if (itemlistener != null) {
                itemlistener.onItemClick(item);
            }

        }
    }


}

