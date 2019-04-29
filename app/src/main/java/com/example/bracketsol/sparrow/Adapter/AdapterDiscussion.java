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
import com.example.bracketsol.sparrow.Model.ModelDiscussionTry;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDiscussion extends RecyclerView.Adapter<AdapterDiscussion.ViewHolder> {

    protected AdapterDiscussion.ItemListener mListener;
    ArrayList<ModelDiscussionTry> mValues;
    Context mContext;
    private int currentViewType;

    public AdapterDiscussion(Context context, ArrayList<ModelDiscussionTry> values) {
        mValues = values;
        mContext = context;
    }

    @Override
    public AdapterDiscussion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.row_discussion_try, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AdapterDiscussion.ViewHolder holder, final int position) {

        holder.setData(mValues.get(position));
        ModelDiscussionTry data = mValues.get(position);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, position + " is clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(ModelDiscussionTry item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView pro_img1,pro_img2,pro_img3,pro_img4,pro_img5,pro_img6;
        ImageView img1,img2,img3,img4,img5,img6;
        TextView name1,name2,name3,name4,name5,name6;


        CardView cardView;
        ModelDiscussionTry item;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            pro_img1 = v.findViewById(R.id.pro_img_dis1);
            pro_img2 = v.findViewById(R.id.pro_img_dis2);
            pro_img3 = v.findViewById(R.id.pro_img_dis3);
            pro_img4 = v.findViewById(R.id.pro_img_dis4);
            pro_img5 = v.findViewById(R.id.pro_img_dis5);
            pro_img6 = v.findViewById(R.id.pro_img_dis6);

            img1 = v.findViewById(R.id.pro_img_status_dis1);
            img2 = v.findViewById(R.id.pro_img_status_dis2);
            img3 = v.findViewById(R.id.pro_img_status_dis3);
            img4 = v.findViewById(R.id.pro_img_status_dis4);
            img5 = v.findViewById(R.id.pro_img_status_dis5);
            img6 = v.findViewById(R.id.pro_img_status_dis6);

            name1 = v.findViewById(R.id.pro_name_dis1);
            name2 = v.findViewById(R.id.pro_name_dis2);
            name3 = v.findViewById(R.id.pro_name_dis3);
            name4 = v.findViewById(R.id.pro_name_dis4);
            name5 = v.findViewById(R.id.pro_name_dis5);
            name6 = v.findViewById(R.id.pro_name_dis6);

        }

        public void setData(ModelDiscussionTry item) {
            this.item = item;


            pro_img1.setImageResource(item.getPro_image1());
            pro_img2.setImageResource(item.getPro_image2());
            pro_img3.setImageResource(item.getPro_image3());
            pro_img4.setImageResource(item.getPro_image4());
            pro_img5.setImageResource(item.getPro_image5());
            pro_img6.setImageResource(item.getPro_image6());

            img1.setImageResource(item.getPost_image1());
            img2.setImageResource(item.getPost_image2());
            img3.setImageResource(item.getPost_image3());
            img4.setImageResource(item.getPost_image4());
            img5.setImageResource(item.getPost_image5());
            img6.setImageResource(item.getPost_image6());

            name1.setText(item.getName1());
            name2.setText(item.getName2());
            name3.setText(item.getName3());
            name4.setText(item.getName4());
            name5.setText(item.getName5());
            name6.setText(item.getName6());


        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}
