package com.example.bracketsol.sparrow.SocialLife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Activities.HomeActivity;
import com.example.bracketsol.sparrow.Model.StatusPostingModel;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Retrofit.ApiClient;
import com.example.bracketsol.sparrow.Retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialLifeFragment extends Fragment {

    private static View view;
    ApiInterface apiInterface;
    LinearLayoutManager mLayoutManager;
    Call<ResponseBody> socialCall, alertCall;
    ProgressBar simpleProgressBar;
    LinearLayout alert_layout, social_layout;
    FloatingActionButton floatingActionButton, fabalert;
    LinearLayout getSocial_layout, getAlert_layout;
    boolean isfirsttime_social = true;
    boolean isfirsttime_alert = true;
    private ArrayList<StatusPostingModel> socialArrayList;
    private RecyclerView recyclerView_alert;
    private AdapterAlertNotificationSocial alertadapter;
    private ArrayList<ModelAlertSocial> alertArrayList;
    private RecyclerView recyclerView_social;
    private SocialPostAdapter socialadapter;
    SwipeRefreshLayout swipe_container;

    Boolean has_next = true;
    int total_pages = 0;

    int page = 1;
    int post_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = (View) inflater.inflate(R.layout.social_liffe_fragment, container, false);
        init();
        Listeners();
        onScrollListener();
        return view;
    }


    private void init() {

        //((HomeActivity)getActivity()).hideToolbar();
        alert_layout = view.findViewById(R.id.alert_layout);
        social_layout = view.findViewById(R.id.social_layout);
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        swipe_container = view.findViewById(R.id.swipe_container);
        recyclerView_social = (RecyclerView) view.findViewById(R.id.social_recycler);
        recyclerView_alert = (RecyclerView) view.findViewById(R.id.alert_recycler);
        alertArrayList = new ArrayList<>();
        socialArrayList = new ArrayList<>();
        socialadapter = new SocialPostAdapter(getContext(), socialArrayList);

        recyclerView_social.setAdapter(socialadapter);
        LinearLayoutManager ll_manager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, true);
        ll_manager.setReverseLayout(true);
        ll_manager.setStackFromEnd(true);
        recyclerView_social.setLayoutManager(ll_manager);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        recyclerView_social.addOnScrollListener(new SocialCustomScrollListener());
        recyclerView_alert.addOnScrollListener(new AlertCustomScrollListener());
        floatingActionButton = view.findViewById(R.id.fab);
        fabalert = view.findViewById(R.id.fab_alert);


        fetchData();
        prepareAlertData();

    }

    private void swipeListener() {

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                socialArrayList.clear();
                fetchData();
            }
        });

    }
    private void fetchData() {
        page++;
        //progressBar.setVisibility(View.VISIBLE);
        swipe_container.setRefreshing(true);
        prepareSocialData();

    }

    private void Listeners() {

        alert_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        social_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.7f
                );
                social_layout.setLayoutParams(param);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fabalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alert_layout.getVisibility()==View.VISIBLE){
                    social_layout.setVisibility(View.VISIBLE);
                    alert_layout.setVisibility(View.GONE);
                    Animation down = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                    social_layout.setAnimation(down);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1.99f
                    );
                    social_layout.setLayoutParams(param);
                }else if(social_layout.getVisibility()==View.VISIBLE){
                    alert_layout.setVisibility(View.VISIBLE);
                    social_layout.setVisibility(View.GONE);
                    Animation up = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                    alert_layout.setAnimation(up);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1.99f
                    );
                    alert_layout.setLayoutParams(param);
                }

            }
        });

        recyclerView_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //alert_View.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void prepareSocialData() {
        socialCall = apiInterface.getSocialLife(page);
        socialCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    swipe_container.setRefreshing(false);
                    String resString = response.body().string();
                    JSONObject resJson = new JSONObject(resString);
                    Log.e("TAG", "resString: " + resString);
                    JSONArray array = resJson.getJSONArray("announcements");
                    Log.e("TAG", "ok");

                    total_pages = resJson.getInt("total_pages");


                    has_next  = resJson.getBoolean("has_next");

                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        Log.e("TAG", "announcement_id" + product.getInt("announcement_id"));
                        Log.e("TAG", "sender_id" + product.getInt("sender_id"));
                        Log.e("TAG", "sender_name" + product.getString("sender_name"));
                        Log.e("TAG", "profile_pic" + product.getString("profile_pic"));
                        Log.e("TAG", "statement" + product.getString("statement"));
                        Log.e("TAG", "attachment_url" + product.getString("attachment_url"));
                        Log.e("TAG", "attachment_url" + product.getString("attachment_url"));
                        Log.e("TAG", "is_active" + product.getInt("is_active"));
                        Log.e("TAG", "type" + product.getString("type"));
                        Log.e("TAG", "start_date" + product.getString("start_date"));
                        Log.e("TAG", "end_date" + product.getString("end_date"));
                        Log.e("TAG", "total_likes" + product.getInt("total_likes"));
                        Log.e("TAG", "total_comments" + product.getInt("total_comments"));
                        Log.e("TAG", "total_views" + product.getInt("total_views"));
                        Log.e("TAG", "created_at" + product.getString("created_at"));


                        int announcement_id = product.getInt("announcement_id");
                        int sender_id = product.getInt("sender_id");
                        String sender_name = product.getString("sender_name");
                        String profile_pic = product.getString("profile_pic");
                        String statement = product.getString("statement");
                        String url = product.getString("attachment_url");
                        int is_active = product.getInt("is_active");
                        String type = product.getString("type");
                        String attachment_type = product.getString("attachment_type");
                        String attachment_url = product.getString("attachment_url");
                        int attachment_id = product.getInt("attachment_id");
                        String start_data = product.getString("start_date");
                        String end_date = product.getString("end_date");
                        int total_likes = product.getInt("total_likes");
                        int total_comments = product.getInt("total_comments");
                        int total_views= product.getInt("total_views");
                        String created_at = product.getString("created_at");

                        //simpleProgressBar.setVisibility(View.GONE);
                        Log.i("url", "https://s3.amazonaws.com/social-funda-bucket/" + url);
                        StatusPostingModel statusPostingModel = new StatusPostingModel(sender_name, "https://social-funda-bucket.s3.amazonaws.com/" + profile_pic, statement, "https://social-funda-bucket.s3.amazonaws.com/" + attachment_url, total_likes,
                                total_comments, total_views,post_id,attachment_type);

//                        simpleProgressBar.setVisibility(View.GONE);
                        socialArrayList.add(statusPostingModel);
                    }
                    recyclerView_social.setAdapter(socialadapter);
                    recyclerView_social.scrollToPosition(socialArrayList.size());
                    socialadapter.notifyDataSetChanged();
                    mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
                   // mLayoutManager.setReverseLayout(true);
                    //mLayoutManager.setStackFromEnd(true);
                    recyclerView_alert.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_social.setLayoutManager(mLayoutManager);


                    recyclerView_social.setItemAnimator(new DefaultItemAnimator());


                } catch (IOException e) {
                    e.printStackTrace();
                    swipe_container.setRefreshing(false);
                    Log.e("TAG", "checkval " + e.getMessage());


                } catch (JSONException e) {
                    e.printStackTrace();
                    swipe_container.setRefreshing(false);
                    Log.e("TAG", "checkval onresponse" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                swipe_container.setRefreshing(false);
            }
        });
    }


    private void prepareAlertData() {
        alertadapter = new AdapterAlertNotificationSocial(getContext(), alertArrayList);

        recyclerView_alert.setAdapter(alertadapter);
        mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, true);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView_alert.setLayoutManager(mLayoutManager);

        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
        alertArrayList.add(new ModelAlertSocial(R.drawable.ic_girl, R.drawable.ic_more_horiz_black_24dp, "Yesterday 19:25", "We are <b><i>so</i></b> glad to see you"));
    }

//    public void startSlideDownAnimation() {
//        alert_layout.startAnimation(slideDownAnimation);
//    }
//    public void startSlideDownAnimationn() {
//        social_layout.startAnimation(slideDownAnimation);
//    }
//    public void startSlideUpAnimation() {
//        alert_layout.startAnimation(slideUpAnimation);
//    }

    public class SocialCustomScrollListener extends RecyclerView.OnScrollListener {
        public SocialCustomScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    Log.i("onscroll", "The RecyclerView is not scrolling");
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    Log.i("onscroll", "Scrolling now");
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    Log.i("onscroll", "Scroll Settling");
                    break;
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dx > 0) {
                Log.i("onscroll", "Scrolled Right");
            } else if (dx < 0) {
                Log.i("onscroll", "Scrolled Left");
            } else {
                Log.i("onscroll", "No Horizontal Scrolled");
            }
            if (dy > 0) {
                Log.i("onscroll", "Scrolled Downwards");
                fabalert.setVisibility(View.VISIBLE);
                if (isfirsttime_social) {
                    isfirsttime_alert= true;
                    //startSlideDownAnimation();
                    Log.i("onscroll", "Scrolled Downwards");
                    //alert_layout.setL
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1.99f
                    );
                    social_layout.setLayoutParams(param);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            0.01f
                    );
                    params.setMargins(0,0,0,0);
                    alert_layout.setLayoutParams(params);
                    alert_layout.setVisibility(View.GONE);

                }
                isfirsttime_social = false;
            } else if (dy < 0) {
                Log.i("onscroll", "Scrolled Upwards");
                fabalert.setVisibility(View.VISIBLE);
                if (isfirsttime_social) {
                    isfirsttime_alert= true;
                    //startSlideUpAnimation();
                    Log.i("onscroll", "Scrolled Downwards");
                    //alert_layout.setL
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1.99f
                    );
                    social_layout.setLayoutParams(param);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            0.01f
                    );
                    params.setMargins(0,0,0,0);
                    alert_layout.setLayoutParams(params);
                    alert_layout.setVisibility(View.GONE);

                }
                isfirsttime_social = false;
            } else {
                Log.i("onscroll", "No Vertical Scrolled");
            }
        }
    }

    public class AlertCustomScrollListener extends RecyclerView.OnScrollListener {
        public AlertCustomScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    Log.i("onscroll", "The RecyclerView is not scrolling");
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    Log.i("onscroll", "Scrolling now");

                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    Log.i("onscroll", "Scroll Settling");
                    break;

            }

        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dx > 0) {
                Log.i("onscroll", "Scrolled Right");
            } else if (dx < 0) {
                Log.i("onscroll", "Scrolled Left");

            } else {
                Log.i("onscroll", "No Horizontal Scrolled");
            }
            if (dy > 0) {
                if (isfirsttime_alert) {
                    isfirsttime_social = true;
                    //startSlideDownAnimationn();
                    Log.i("onscroll", "Scrolled Downwards");
                    fabalert.setVisibility(View.VISIBLE);
                    //alert_layout.setL
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            0.01f
                    );
                    social_layout.setLayoutParams(param);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1.99f
                    );
                    params.setMargins(0,0,0,0);
                    alert_layout.setLayoutParams(params);
                    social_layout.setVisibility(View.GONE);
                }
                isfirsttime_alert = false;

            } else if (dy < 0) {
                Log.i("onscroll", "Scrolled Upwards");
                fabalert.setVisibility(View.VISIBLE);
                if (isfirsttime_alert) {
                    isfirsttime_social = true;
                    //startSlideUpAnimation();
                    Log.i("onscroll", "Scrolled Downwards");
                    //alert_layout.setL
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            0.01f
                    );
                    social_layout.setLayoutParams(param);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1.99f
                    );
                    params.setMargins(0,0,0,0);
                    alert_layout.setLayoutParams(params);
                    social_layout.setVisibility(View.GONE);

                }
                isfirsttime_alert = false;
            } else {
                Log.i("onscroll", "No Vertical Scrolled");
            }
        }
    }
    private void onScrollListener() {
        recyclerView_social.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.e("Page", "onScrolledstate");

                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible <= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    //you have reached to the bottom of your recycler view
                    Log.e("Page", "islastitem");
                    if (has_next) {
                        Log.e("Page", "hasnext");
                        if (page <= total_pages) {
                            Log.e("Page", "page<totalpage");
                            fetchData();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
        socialadapter.notifyDataSetChanged();

    }


}
