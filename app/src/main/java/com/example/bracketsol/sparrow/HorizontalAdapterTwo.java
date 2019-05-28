package com.example.bracketsol.sparrow;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HorizontalAdapterTwo extends RecyclerView.Adapter<HorizontalAdapterTwo.MyViewHolder> {

    protected Itemlistenerinterface itemlistener;
    ArrayList<SingleHorizontalTwo> data = new ArrayList<>();
    Context mContext;
    private boolean islike = false;

    public HorizontalAdapterTwo(Context mContext, ArrayList<SingleHorizontalTwo> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_single_row_two, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.pro_name.setText(data.get(position).getName());
        holder.imagestatus.setImageResource(data.get(position).getStatus_image());
        holder.pro_img.setImageResource(data.get(position).getPro_img());

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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface Itemlistenerinterface {
        void onItemClick(SingleHorizontalTwo statusPostingModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pro_name,desc;
        ImageView imagestatus;
        CircleImageView pro_img;
        CardView cardView;
        ImageButton like, coment;

        public MyViewHolder(View itemView) {
            super(itemView);
            pro_name = (TextView) itemView.findViewById(R.id.pro_name_dis1_two);
            imagestatus = (ImageView) itemView.findViewById(R.id.pro_img_status_dis1_two);
            pro_img = (CircleImageView) itemView.findViewById(R.id.pro_img_dis1_two);
            cardView = itemView.findViewById(R.id.card_horizontal_two);
            like = itemView.findViewById(R.id.like_imgbtndis);
            coment = itemView.findViewById(R.id.coment_imgbtndis);
            desc = (TextView) itemView.findViewById(R.id.desc);
        }


    }
}
