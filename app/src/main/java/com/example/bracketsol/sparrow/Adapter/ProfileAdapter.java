package com.example.bracketsol.sparrow.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bracketsol.sparrow.Model.ModelProfile;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.RoundRectCornerImageView;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    Context context;
    private List<ModelProfile> list_Account;

    public ProfileAdapter(List<ModelProfile> list_Account) {
        this.list_Account = list_Account;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final ModelProfile modelProfile = list_Account.get(i);
        myViewHolder.title.setText(modelProfile.getName());
        myViewHolder.imageView.setImageResource(modelProfile.getImage_pathl());

    }

    @Override
    public int getItemCount() {
        return list_Account.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView imageView;
        RoundRectCornerImageView img;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
            img =  view.findViewById(R.id.civ_profile);


        }
    }

}
