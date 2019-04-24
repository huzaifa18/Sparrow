package com.example.bracketsol.sparrow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bracketsol.sparrow.Model.ListModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {


    private ArrayList<ListModel> listData;

    private LayoutInflater layoutInflater;

    Context context;

    public CustomListAdapter(ArrayList<ListModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view==null){
            view = layoutInflater.inflate(R.layout.row_list,null);
            viewHolder = new ViewHolder();
            viewHolder.unitView = (TextView) view.findViewById(R.id.unit);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.unitView.setText(listData.get(i).getText().toString());
        return null;
    }

    static class ViewHolder {
        TextView unitView;

    }
}
