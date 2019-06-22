package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Model.CommentModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    protected ItemListener mListener;
    ArrayList<CommentModel> mValues;
    Context mContext;

    public CommentAdapter(Context context, ArrayList<CommentModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(mValues.get(position));
        CommentModel data = mValues.get(position);
        Glide.with(mContext)
                .load(data.getPic())
                .into(holder.profile_noti);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(), PlaceDetails.class);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
//
//    @Override
//    public void onBindViewHolder(final FindFriendAdapter.ViewHolder Vholder, final int position) {
//
//    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(CommentModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView post_noti, time_noti, username;
        ImageButton more_noti;
        CircleImageView profile_noti;
        LinearLayout linearLayout;
        CommentModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            post_noti = (TextView) v.findViewById(R.id.tv_content);
            time_noti = (TextView) v.findViewById(R.id.time_noti);
            username = v.findViewById(R.id.username_txtview_noti);
            more_noti = v.findViewById(R.id.more_imgbtn_noti);
            profile_noti = v.findViewById(R.id.noti_image);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_layoutnoti_row);
        }

        public void setData(CommentModel item) {
            this.item = item;

            post_noti.setText(item.getContent());
            time_noti.setText(item.getCreated_at());
            username.setText(item.getUsername());
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}
