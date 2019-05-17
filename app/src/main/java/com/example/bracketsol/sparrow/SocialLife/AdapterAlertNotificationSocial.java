package com.example.bracketsol.sparrow.SocialLife;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bracketsol.sparrow.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAlertNotificationSocial extends   RecyclerView.Adapter<AdapterAlertNotificationSocial.MyViewHolder>{
    private List<ModelAlertSocial> list;

    public AdapterAlertNotificationSocial(List<ModelAlertSocial> list) {
        this.list = list;
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
        ModelAlertSocial mAccount = list.get(i);
        myViewHolder.title.setText(mAccount.getNotification());
        myViewHolder.imageView.setImageResource(mAccount.getImage_path());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        CircleImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.alert_name_txtview);
            imageView=(CircleImageView) view.findViewById(R.id.alert_image);
        }
    }
}
