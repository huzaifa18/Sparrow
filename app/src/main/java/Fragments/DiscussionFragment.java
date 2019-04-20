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
import com.example.bracketsol.sparrow.Model.Adapter.NotificationAdapter;
import com.example.bracketsol.sparrow.Model.ModelDiscussion;
import com.example.bracketsol.sparrow.Model.NotificationModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

public class DiscussionFragment extends Fragment {


    private static View view;
    FragmentManager fragmentManager;
    RecyclerView discussion_recyclerView;
    ArrayList<ModelDiscussion> modelDiscussionArrayList;
    RecyclerView.Adapter adapter;
    LinearLayoutManager manager;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    Context context;

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
        modelDiscussionArrayList = new ArrayList<ModelDiscussion>();
        adapter = new AdapterDiscussion(getContext(),modelDiscussionArrayList);

        discussion_recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);

        discussion_recyclerView.setLayoutManager(gridLayoutManager);
//
//        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
//        discussion_recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man3));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_team));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man3));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man3));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_target));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_team));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man3));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_team));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.capture2));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_target));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man3));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_target));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_man));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_team));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_seo));
        modelDiscussionArrayList.add(new ModelDiscussion(R.drawable.ic_target));

        adapter.notifyDataSetChanged();

    }


}
