package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bracketsol.sparrow.Adapter.NotificationAdapter;
import com.example.bracketsol.sparrow.Model.NotificationModel;
import com.example.bracketsol.sparrow.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private static View view;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    TextView toolbarTextView_next;
    RecyclerView recyclerView;
    ArrayList<NotificationModel> notificationModelArrayList;
    NotificationAdapter notificationAdapter;
    LinearLayoutManager manager;
    Context context;

    public NotificationFragment() {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //toolbar = getActivity().findViewById(R.id.toolbar_custom_noti);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //toolbarTextView_next = getView().findViewById(R.id.next_textview_noti);
//        toolbarTextView_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().finish();
//                Toast.makeText(context, "Back clicked!", Toast.LENGTH_SHORT).show();
//            }
//        });

        view = (View) inflater.inflate(R.layout.notification_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = view.findViewById(R.id.find_recycler_view_noti);
        notificationModelArrayList = new ArrayList<NotificationModel>();
        notificationAdapter = new NotificationAdapter(getContext(), notificationModelArrayList);

        recyclerView.setAdapter(notificationAdapter);
        manager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, true);

        recyclerView.setLayoutManager(manager);

        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_man, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_man3, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_target, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_seo, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_team, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_man, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
        notificationModelArrayList.add(new NotificationModel(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "Faisal Razzaq commented on a post that you are tagged in"));
    }

//
//    @Override
//    public void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        new AsyncCaller().execute();
//    }
//
//    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                     }
//            });
//            //this method will be running on background thread so don't update UI frome here
//            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            //this method will be running on UI thread
//        }
}


