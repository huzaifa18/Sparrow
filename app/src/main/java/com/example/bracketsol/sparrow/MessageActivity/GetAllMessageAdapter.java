package com.example.bracketsol.sparrow.MessageActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetAllMessageAdapter extends RecyclerView.Adapter<GetAllMessageAdapter.ViewHolder> {

    public RelativeLayout viewBackground, viewForeground;
    protected GetAllMessageAdapter.ItemListener mListener;
    ArrayList<MessageListModel> mValues;
    Context mContext;

    public GetAllMessageAdapter(Context context, ArrayList<MessageListModel> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public GetAllMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_chat_list_row, parent, false);
        return new GetAllMessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GetAllMessageAdapter.ViewHolder Vholder, final int position) {
        Vholder.setData(mValues.get(position));
        final MessageListModel data = mValues.get(position);
//        Glide.with(mContext)
//                .load(data.getImg_url())
//                .into(Vholder.imageView);

        Vholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "" + data.getSender_id() + data.getUsername() + data.getProfileUrl(), Toast.LENGTH_SHORT).show();

                Log.i("TAG","senderid : "+data.getSender_id());
                Log.i("TAG","getUsername : "+data.getUsername() );
                Log.i("TAG","getReceiverid : "+data.getReceiverid());

                Intent intent = new Intent(mContext, ChatActivityMain.class);
                intent.putExtra("username", data.getUsername());
                intent.putExtra("profileurl", data.getProfileUrl());
                intent.putExtra("senderid", data.getSender_id());
                intent.putExtra("receiverid", data.getReceiverid());

                mContext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void removeAt(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mValues.size());
    }

    public interface ItemListener {
        void onItemClick(MessageListModel item);
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private GetAllMessageAdapter mAdapter;

        public SwipeToDeleteCallback(GetAllMessageAdapter adapter) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView profile;
        TextView uname, date, message;
        LinearLayout linearLayout;
        MessageListModel item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            uname = (TextView) v.findViewById(R.id.name_tv);
            message = (TextView) v.findViewById(R.id.recent_tv);
            date = (TextView) v.findViewById(R.id.time_tv);
            profile = v.findViewById(R.id.friend_image);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_layoutt);

        }

        public void setData(MessageListModel item) {
            this.item = item;

            uname.setText(item.getUsername());
            message.setText(item.getMessage());
            date.setText(item.getDate());
            //profile.setImageResource(item.getProfileUrl());

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}
