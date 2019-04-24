package com.example.bracketsol.sparrow.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Model.FindFriendModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendAdapter extends RecyclerView.Adapter<FindFriendAdapter.ViewHolder> {

    protected ItemListener mListener;
    ArrayList<FindFriendModel> mValues;
    Context mContext;

    public FindFriendAdapter(Context context, ArrayList<FindFriendModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public FindFriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.find_friend_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {
        Vholder.setData(mValues.get(position));
        FindFriendModel data = mValues.get(position);
//        Glide.with(mContext)
//                .load(data.getImg_url())
//                .into(Vholder.imageView);




        Vholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(), PlaceDetails.class);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Vholder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(FindFriendModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView first, second;
        CircleImageView profile;
        Button minus;
        Button plus;
        LinearLayout linearLayout;
        FindFriendModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            first = (TextView) v.findViewById(R.id.name_txtview);
            second = (TextView) v.findViewById(R.id.mutual_txtview);

            profile = v.findViewById(R.id.friend_image);
            plus = v.findViewById(R.id.friendship_btn);
            minus = v.findViewById(R.id.cut_btn);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_layout);
        }

        public void setData(FindFriendModel item) {
            this.item = item;

            first.setText(item.getName());
            second.setText(item.getSubname());
            profile.setImageResource(item.getImgid());
            plus.setBackgroundResource(item.getImgplus());
            minus.setBackgroundResource(item.getImgminus());
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }


    }
    public void removeAt(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());
    }

}