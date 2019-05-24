package com.example.bracketsol.sparrow.SocialLife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bracketsol.sparrow.Model.NotificationModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAlertNotificationSocial extends   RecyclerView.Adapter<AdapterAlertNotificationSocial.MyViewHolder>{

    private ItemListener itemListener;
    private ArrayList<ModelAlertSocial> list;
    Context ctx;

    public AdapterAlertNotificationSocial(Context context,ArrayList<ModelAlertSocial> list) {
        this.list = list;
        this.ctx = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.alert_bar, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ModelAlertSocial data = list.get(i);
//        Glide.with(mContext)
//                .load(data.getImg_url())
//                .into(Vholder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface ItemListener{
        void onItemClick(ModelAlertSocial item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView post_alert,time_alert;
        ImageButton more_alert;
        CircleImageView profile_alert;
        LinearLayout linearLayout;
        ModelAlertSocial item;

        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            post_alert= (TextView) v.findViewById(R.id.alert_name_txtview);
            time_alert= (TextView) v.findViewById(R.id.alert_time);
            more_alert= v.findViewById(R.id.alert_more_imgbtn);
            profile_alert= v.findViewById(R.id.alert_image);
            linearLayout = (LinearLayout) v.findViewById(R.id.alertlinear_layoutnoti_row);
        }

        public void setData(ModelAlertSocial item) {
            this.item = item;

            post_alert.setText(item.getPost_alert());
            time_alert.setText(item.getTime_alert());

            profile_alert.setImageResource(item.getImg_user_alert());
            more_alert.setBackgroundResource(item.getMore_alert());

        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(item);
            }
        }
    }
}
