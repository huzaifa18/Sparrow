package com.example.bracketsol.sparrow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DisFragment extends Fragment {

    View view;
    FragmentManager fragmentManager;
    private ArrayList<Object> objects ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.dis_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        fragmentManager = getActivity().getSupportFragmentManager();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_View);
        objects = new ArrayList<>();
        MainAdapter adapter = new MainAdapter(getContext(), getObject());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static ArrayList<SingleVertical> getVerticalData() {
        ArrayList<SingleVertical> singleVerticals = new ArrayList<>();

        singleVerticals.add(new SingleVertical("Nawaz sharif",R.drawable.demo,R.drawable.ic_man3));
        singleVerticals.add(new SingleVertical("Shabaz sharif",R.drawable.demo,R.drawable.ic_ma));
        singleVerticals.add(new SingleVertical("Hamza shahbaz",R.drawable.demo,R.drawable.ic_man));
        return singleVerticals;
    }

    public static ArrayList<SingleHorizontalTwo> getHorizontalDataTwo() {
        ArrayList<SingleHorizontalTwo> singleHorizontals = new ArrayList<>();
        singleHorizontals.add(new SingleHorizontalTwo(R.drawable.demo, R.drawable.ic_ma,R.drawable.ic_like,R.drawable.ic_comment,"Hakim faraz","Hi there, i'm going to lahore for a big deal, so i can't make a promise with you, here's my status. Meet you soon. Till then take care"));
        return singleHorizontals;
    }

    public static ArrayList<SingleHorizontal> getHorizontalData() {
        ArrayList<SingleHorizontal> singleHorizontals = new ArrayList<>();
        singleHorizontals.add(new SingleHorizontal(R.drawable.demo, R.drawable.ic_seo, "Muhammad Ali"));
        singleHorizontals.add(new SingleHorizontal(R.drawable.demo, R.drawable.ic_girl, "Tayyaba"));
        return singleHorizontals;
    }

    private ArrayList<Object> getObject() {
        objects.add(getVerticalData().get(0));
        objects.add(getHorizontalData().get(0));
        objects.add(getHorizontalDataTwo().get(0));
        objects.add(getVerticalData().get(0));
        objects.add(getHorizontalData().get(0));
        objects.add(getHorizontalDataTwo().get(0));
        objects.add(getVerticalData().get(0));
        objects.add(getHorizontalData().get(0));
        objects.add(getHorizontalDataTwo().get(0));

        return objects;
    }


}


