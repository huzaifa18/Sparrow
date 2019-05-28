package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.MessageActivity.ChatActivityMain;

import com.example.bracketsol.sparrow.Model.RecentChatModel;
import com.example.bracketsol.sparrow.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentChatsListingAdapter extends RecyclerView.Adapter<RecentChatsListingAdapter.ViewHolder> {

    protected ItemListener mListener;
    ArrayList<RecentChatModel> mValues;
    Context mContext;

    public RecentChatsListingAdapter(Context context, ArrayList<RecentChatModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_chat_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Vholder.setData(mValues.get(position));
        RecentChatModel data = mValues.get(position);

        Vholder.recent_tv.setText(data.getSubname());

        Vholder.profile.setImageResource(data.getImgid());
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        Vholder.time_tv.setText(formattedDate);


        /*Glide.with(mContext)
                .load(data.getImg_url())
                .into(Vholder.imageView);*/

        /*Vholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(), PlaceDetails.class);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(RecentChatModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, recent_tv,time_tv;
        CircleImageView profile;
        RecentChatModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            name = (TextView) v.findViewById(R.id.name_tv);
            recent_tv = (TextView) v.findViewById(R.id.recent_tv);
            time_tv = (TextView) v.findViewById(R.id.time_tv);

            profile = v.findViewById(R.id.profile_iv);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext, getAdapterPosition() + " is clicked", Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, ChatActivityMain.class));
                }
            });

        }

        public void setData(RecentChatModel item) {
            this.item = item;

            name.setText(item.getName());
            //recent_tv.setText(item.getLast_text());
            time_tv.setText(item.getDate());
            //profile.setImageResource(Integer.parseInt(item.getImage_path()));

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
                Toast.makeText(mContext, getAdapterPosition() + " is clicked", Toast.LENGTH_SHORT).show();

            }
        }
    }
}