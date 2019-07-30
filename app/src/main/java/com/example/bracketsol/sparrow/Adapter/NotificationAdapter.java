package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Model.NotificationModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    protected ItemListener mListener;
    ArrayList<NotificationModel> mValues;
    Context mContext;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notifications_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(mValues.get(position));
        NotificationModel data = mValues.get(position);
//        Glide.with(mContext)
//                .load(data.getImg_url())
//                .into(Vholder.imageView);

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
        void onItemClick(NotificationModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView post_noti,time_noti;
        ImageButton more_noti;
        CircleImageView profile_noti;
        RelativeLayout linearLayout;
        NotificationModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            post_noti = (TextView) v.findViewById(R.id.name_txtview_noti);
            time_noti = (TextView) v.findViewById(R.id.time_noti);

            more_noti = v.findViewById(R.id.more_imgbtn_noti);
            profile_noti = v.findViewById(R.id.noti_image);
            linearLayout = v.findViewById(R.id.rv_main);
        }

        public void setData(NotificationModel item) {
            this.item = item;

            post_noti.setText(item.getContent());
            time_noti.setText(item.getCreated_at());
            Glide.with(mContext).load(item.getPicture_url()).into(profile_noti);
            Glide.with(mContext).load(R.drawable.story1).into(profile_noti);
            //more_noti.setBackgroundResource(item.getMore_noti());

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}