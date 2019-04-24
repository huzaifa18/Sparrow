package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Model.TryDiscussionModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

public class TryDiscussionAdapter extends RecyclerView.Adapter<TryDiscussionAdapter.ViewHolder> {

    protected TryDiscussionAdapter.ItemListener mListener;
    ArrayList<TryDiscussionModel> mValues;
    Context mContext;

    public TryDiscussionAdapter(Context context, ArrayList<TryDiscussionModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public TryDiscussionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_discussion_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TryDiscussionAdapter.ViewHolder holder, final int position) {
        holder.setData(mValues.get(position));
        TryDiscussionModel data = mValues.get(position);
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
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(TryDiscussionModel item);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        CardView cardView;
        TryDiscussionModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            //img = v.findViewById(R.id.img_discuss);
            cardView = (CardView) v.findViewById(R.id.card_view_discuss);
        }

        public void setData(TryDiscussionModel item) {
            this.item = item;


            img.setImageResource(item.getImgid());


        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
    }


