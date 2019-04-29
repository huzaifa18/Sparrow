package com.example.bracketsol.sparrow;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    protected Itemlistenerinterface itemlistener;
    ArrayList<SingleHorizontal> data = new ArrayList<>();
    Context mContext;

    public HorizontalAdapter(Context mContext, ArrayList<SingleHorizontal> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_single_row_one, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.pro_name.setText(data.get(position).getName());
        holder.imagestatus.setImageResource(data.get(position).getStatus_image());
        holder.pro_img.setImageResource(data.get(position).getPro_img());

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
        void onItemClick(SingleHorizontal statusPostingModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pro_name;
        ImageView imagestatus;
        CircleImageView pro_img;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            pro_name = (TextView) itemView.findViewById(R.id.pro_name_dis1);
            imagestatus = (ImageView) itemView.findViewById(R.id.pro_img_status_dis1);
            pro_img = (CircleImageView) itemView.findViewById(R.id.pro_img_dis1);
            cardView = itemView.findViewById(R.id.card_horizontal);
        }


    }
}
