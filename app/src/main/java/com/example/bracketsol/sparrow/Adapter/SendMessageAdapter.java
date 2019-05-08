package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Model.FindFriendModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SendMessageAdapter extends RecyclerView.Adapter<SendMessageAdapter.ViewHolder> {

    protected SendMessageAdapter.ItemListener mListener;
    ArrayList<FindFriendModel> mValues;
    Context mContext;
    public RelativeLayout viewBackground, viewForeground;

    public SendMessageAdapter(Context context, ArrayList<FindFriendModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public SendMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.find_friend_list_row, parent, false);
        return new SendMessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SendMessageAdapter.ViewHolder Vholder, final int position) {
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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(FindFriendModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username;
        CircleImageView profile;
        LinearLayout linearLayout;
        FindFriendModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            username = (TextView) v.findViewById(R.id.name_txtview);

            profile = v.findViewById(R.id.friend_image);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_layout);

        }

        public void setData(FindFriendModel item) {
            this.item = item;
            username.setText(item.getUsername());
            profile.setImageResource(item.getProfileUrl());

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


    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private SendMessageAdapter mAdapter;

        public SwipeToDeleteCallback(SendMessageAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            mAdapter = adapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.removeAt(position);

        }
    }
}
