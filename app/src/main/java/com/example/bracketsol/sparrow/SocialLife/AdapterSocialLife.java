package com.example.bracketsol.sparrow.SocialLife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bracketsol.sparrow.Adapter.StatusPostAdapter;
import com.example.bracketsol.sparrow.Model.ModelDiscussionTry;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterSocialLife extends RecyclerView.Adapter<AdapterSocialLife.MyViewHolder> {

    protected Itemlistenerinterface  itemlistener;
    ArrayList<ModelSocial> list;
    Context conl;
    private boolean islike = false;
    private boolean issave = false;


    public AdapterSocialLife(Context context, ArrayList<ModelSocial> list) {
        this.list = list;
        this.conl = context;
    }

    @NonNull
    @Override
    public AdapterSocialLife.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_social_life, viewGroup, false);

        return new AdapterSocialLife.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.setData(list.get(i));
        ModelSocial data = list.get(i);
        ModelSocial mAccount = list.get(i);

        myViewHolder.desc.setText(mAccount.getStatement());
        myViewHolder.user.setText(mAccount.getSender_name());
        myViewHolder.likes.setText("" + mAccount.getLikes());
        myViewHolder.coments.setText("" + mAccount.getComments());


        myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(conl, ""+list.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        myViewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "like", Toast.LENGTH_SHORT).show();
                if (islike) {
                    myViewHolder.like.setImageResource(R.drawable.ic_dislike);
                    islike = false;
                } else {
                    myViewHolder.like.setImageResource(R.drawable.ic_like);
                    islike = true;
                }
            }
        });


        Glide.with(conl)
                .load(mAccount.getProfile_pic())
                .into(myViewHolder.imageVie1w);
        Glide.with(conl)
                .load(mAccount.getUrl())
                .into(myViewHolder.imageView_post);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Itemlistenerinterface {
        void onItemClick(ModelSocial modelSocial);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView user, desc, likes, coments;
        ImageView imageView_post;
        CircleImageView imageVie1w;
        ImageButton like, coment;
        ModelSocial item;
        LinearLayout layout;


        public MyViewHolder(View view) {
            super(view);
            desc = (TextView) view.findViewById(R.id.desc_tv_social);
            user = (TextView) view.findViewById(R.id.username_social);
            likes = (TextView) view.findViewById(R.id.likess_tv);
            coments = (TextView) view.findViewById(R.id.coments_tv);
            layout = view.findViewById(R.id.layout);
            imageVie1w = (CircleImageView) view.findViewById(R.id.userprofile_social);
            imageView_post = (ImageView) view.findViewById(R.id.image_post_social);
        }
        public void setData(ModelSocial item) {
            this.item = item;



        }

        @Override
        public void onClick(View view) {
            if(itemlistener!=null){
            itemlistener.onItemClick(item);
            }

        }
    }
}
