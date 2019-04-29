package com.example.bracketsol.sparrow;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bracketsol.sparrow.Model.ModelDiscussion;

import java.util.ArrayList;

public class HomeTry extends Fragment {

    private static View view;
    FragmentManager fragmentManager;
    RecyclerView discussion_recyclerView;
    ArrayList<ModelDiscussion> modelDiscussionArrayList;
    RecyclerView.Adapter adapter;
    LinearLayoutManager manager;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    GridLayoutManager gridLayoutManager;
    HomeTry context;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.support.v4.app.Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_account:
//                    fragmentManager
//                            .beginTransaction()
//                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                            .replace(R.id.frame_container, new NotificationFragment());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();


                    return true;
                case R.id.navigation_social:
//                    fragmentTransaction
//                            //.beginTransaction()
//                            //.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                            .replace(R.id.frame_container, new DiscussionFragment());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();


                    return true;
                case R.id.navigation_discussion:

                    return true;
                case R.id.navigation_notification:
//                    fragment = new NotificationFragment();
//                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };



    @Override
    public void onAttach(Context context) {
        context=(FragmentActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //fragmentManager = getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        view = (View) inflater.inflate(R.layout.discussion_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
