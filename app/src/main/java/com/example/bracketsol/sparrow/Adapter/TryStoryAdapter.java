package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Model.StoryModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TryStoryAdapter extends RecyclerView.Adapter<TryStoryAdapter.ViewHolder> {

    protected Itemlistener itemlistener;
    ArrayList<StoryModel> storyAdapterArrayList;

    private boolean isFirstTime = true;
    Context mContext;



    public TryStoryAdapter(Context mContext, ArrayList<StoryModel> storyAdapterArrayList) {
        //Collections.reverse(storyAdapterArrayList);
        this.storyAdapterArrayList = storyAdapterArrayList;
        this.mContext = mContext;
    }

    @Override
    public TryStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.story_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        storyAdapterArrayList.get(storyAdapterArrayList.lastIndexOf(position));
//        Toast.makeText(mContext, ""+storyAdapterArrayList.get(storyAdapterArrayList.lastIndexOf(position)), Toast.LENGTH_SHORT).show();

        switch(position){
            case 10:
                holder.add.setVisibility(View.GONE);
                break;
        }
        holder.add.setVisibility(View.INVISIBLE);


        //holder.add.setVisibility(View.GONE);

        holder.setData(storyAdapterArrayList.get(position));
        StoryModel storyModel = storyAdapterArrayList.get(position);
        holder.rowRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {

        return storyAdapterArrayList.size();
    }

    public interface Itemlistener {
        void onItemClick(StoryModel storyModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView story_profile;
        TextView user_name;
        ImageView add;
        RelativeLayout rowRelativelayout;
        StoryModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            story_profile = itemView.findViewById(R.id.story_image_pro);
            user_name = itemView.findViewById(R.id.story_username);
            rowRelativelayout = itemView.findViewById(R.id.relativeLayout_row);

            //add = itemView.findViewById(R.id.test);
        }

        public void setData(StoryModel item) {
            this.item = item;

            story_profile.setImageResource(item.getStry_img_id());
            user_name.setText(item.getUser_name_story());

        }

        @Override
        public void onClick(View view) {
            if (itemlistener != null) {
                itemlistener.onItemClick(item);
            }

        }

            }
}
