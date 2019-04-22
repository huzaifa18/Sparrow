package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bracketsol.sparrow.Model.Adapter.AdapterDiscussion;
import com.example.bracketsol.sparrow.Model.ModelDiscussion;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

public class DiscussionFragment extends Fragment {


    private static View view;
    FragmentManager fragmentManager;
    RecyclerView discussion_recyclerView, two_recyclerview;
    ArrayList<ModelDiscussion> modelDiscussionArrayList, twoarraylist;
    RecyclerView.Adapter adapter,adapter2;
    LinearLayoutManager manager, manager2;
    GridLayoutManager gridLayoutManager;
    Context context;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    public DiscussionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = (View) inflater.inflate(R.layout.discussion_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        fragmentManager = getActivity().getSupportFragmentManager();
        discussion_recyclerView = view.findViewById(R.id.dis_recycler_view);
        //two_recyclerview = view.findViewById(R.id.dis_recycler_view_two);


        modelDiscussionArrayList = new ArrayList<ModelDiscussion>();

        //twoarraylist = new ArrayList<ModelDiscussion>();
        adapter = new AdapterDiscussion(getContext(), modelDiscussionArrayList);
        //adapter2 = new AdapterDiscussion(getContext(), twoarraylist);

        discussion_recyclerView.setAdapter(adapter2);
        //two_recyclerview.setAdapter(adapter);


        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //manager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        //two_recyclerview.setLayoutManager(manager2);
        discussion_recyclerView.setLayoutManager(manager);
//        gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
//
//        discussion_recyclerView.setLayoutManager(gridLayoutManager);
//
//        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
//        discussion_recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo, R.drawable.demo, "hassan"));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo, R.drawable.demo, "ali"));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo, R.drawable.demo, "hassan"));





//        twoarraylist.add(new ModelDiscussion(R.drawable.ic_seo, R.drawable.demo, "ahmed"));
//        twoarraylist.add(new ModelDiscussion(R.drawable.ic_seo, R.drawable.demo, "hassan"));


        adapter.notifyDataSetChanged();

    }


}
