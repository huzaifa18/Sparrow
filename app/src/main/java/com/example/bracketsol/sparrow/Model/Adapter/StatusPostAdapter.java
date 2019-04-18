package com.example.bracketsol.sparrow.Model.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.layout.custom_dialog);
                alertDialog.show();

            }
        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return statusarraylistAdapter.size();
    }

    public interface Itemlistenerinterface {
        void onItemClick(StatusPostingModel statusPostingModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView user_img;
        TextView name, location;
        ImageButton more, like, coment, share, save;
        SquareImageView imgpost;
        CardView cardView;

        StatusPostingModel item;


        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            user_img = itemView.findViewById(R.id.post_uimg);
            name = itemView.findViewById(R.id.username_post);
            location = itemView.findViewById(R.id.location);
            more = itemView.findViewById(R.id.more_imgbtn);
            imgpost = itemView.findViewById(R.id.posted_img);

            like = itemView.findViewById(R.id.like_imgbtn);
            coment = itemView.findViewById(R.id.coment_imgbtn);
            share = itemView.findViewById(R.id.share_imgbtn);
            save = itemView.findViewById(R.id.save_imgbtn);
            cardView = itemView.findViewById(R.id.cardview);

        }

        public void setData(StatusPostingModel statusPostingModel) {
            this.item = statusPostingModel;

            user_img.setImageResource(statusPostingModel.getUser_img());
            name.setText(statusPostingModel.getName_txt());
            location.setText(statusPostingModel.getLoc());
            more.setImageResource(statusPostingModel.getMore_imgbtn());
            like.setImageResource(statusPostingModel.getLike_imgbtn());
            coment.setImageResource(statusPostingModel.getComent_imgbtn());
            share.setImageResource(statusPostingModel.getSend_imgbtn());
            save.setImageResource(statusPostingModel.getFav_imgbtn());
        }

        @Override
        public void onClick(View view) {
            if (itemlistener != null) {
                itemlistener.onItemClick(item);
            }

        }
    }
//
//    public class ListGroup {
//        public Dialog CreateGroupDialog(Context context, ArrayList<FindFriendModel> arrayList) {
//            final Dialog dialog = new Dialog(context);
//            dialog.setContentView(R.layout.custom_dialog);
//
//            LayoutInflater li = LayoutInflater.from(context);
//            View view = li.inflate(R.layout.custom_dialog, null);
//            Toolbar toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
//            // The following line has no effect...
//            toolbar.setTitle("List Group");
//
//            dialog.setCancelable(false);
//            ListView listView = (ListView) dialog.findViewById(R.id.group_listview);
//            CustomAdapter customAdapter = new CustomAdapter(context, R.layout.list_object, arrayList);
//            listView.setAdapter(customAdapter);
//            return dialog;
//
//
//        }

}

