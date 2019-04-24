package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Model.ModelDiscussion;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

public class AdapterDiscussion extends RecyclerView.Adapter<AdapterDiscussion.ViewHolder> {

    protected AdapterDiscussion.ItemListener mListener;
    ArrayList<ModelDiscussion> mValues;
    Context mContext;

    public AdapterDiscussion(Context context, ArrayList<ModelDiscussion> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public AdapterDiscussion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.row_discussion_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDiscussion.ViewHolder holder, final int position) {


        holder.setData(mValues.get(position));
        ModelDiscussion data = mValues.get(position);

        final int pos = position * 2;
//        Glide.with(mContext)
//                .load(data.getImg_url())
//                .into(Vholder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(ModelDiscussion item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pro_img, status_img;
        TextView pro_name;
        CardView cardView;
        ModelDiscussion item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            pro_img = v.findViewById(R.id.pro_img_dis);
            status_img = v.findViewById(R.id.pro_img_status_dis);
            pro_name = v.findViewById(R.id.pro_name_dis);
            cardView = (CardView) v.findViewById(R.id.card_view_discuss);
        }

        public void setData(ModelDiscussion item) {
            this.item = item;


            status_img.setImageResource(item.getStatus_img());
            pro_img.setImageResource(item.getPro_image());
            pro_name.setText(item.getPro_name());


        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}
