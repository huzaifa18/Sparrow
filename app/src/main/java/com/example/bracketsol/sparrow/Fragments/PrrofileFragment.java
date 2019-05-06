package com.example.bracketsol.sparrow.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bracketsol.sparrow.Activities.showPicture;
import com.example.bracketsol.sparrow.Adapter.ProfileAdapter;
import com.example.bracketsol.sparrow.Model.ModelProfile;
import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.RoundRectCornerImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PrrofileFragment extends Fragment {

    private static View view;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    TextView toolbarTextView_next;
    RecyclerView recyclerView;
    ProfileAdapter profileAdapter;
    ArrayList<ModelProfile> arrayList;
    LinearLayoutManager manager;
    RoundRectCornerImageView pro_img;
    Context context;
    ImageView pop_image;
    Dialog settingsDialog;
    boolean zoomOut = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.profile_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.ppl_you_know_list);
        arrayList = new ArrayList<>();

        profileAdapter = new ProfileAdapter(arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(profileAdapter);
        pro_img = view.findViewById(R.id.pro_image);

        settingsDialog = new Dialog(getContext());

        Listeners();

        prepareMovieData();

    }

    private void Listeners() {

        pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getContext(), showPicture.class);
//                intent.putExtra("resourseInt", pro_img.getImage);
//                startActivity(intent);


                //okay method
                pro_img.buildDrawingCache();
                Bitmap bmap = pro_img.getDrawingCache();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                Intent intent = new Intent(getContext(), showPicture.class);
                intent.putExtra("picture", image);
                startActivity(intent);


//                if (zoomOut) {
//                    Toast.makeText(getContext(), "NORMAL SIZE!", Toast.LENGTH_LONG).show();
//
//                    float scale = getContext().getResources().getDisplayMetrics().density;
//                    pro_img.setLayoutParams(new LinearLayout.LayoutParams((int) (350 * scale + 0.5f), (int) (300 * scale + 0.5f)));
//                    pro_img.setAdjustViewBounds(true);
//                    zoomOut = false;
//                } else {
//                    Toast.makeText(getContext(), "FULLSCREEN!", Toast.LENGTH_LONG).show();
//                    float scaleHeigth = getContext().getResources().getDisplayMetrics().heightPixels;
//                    float scaleWidth = getContext().getResources().getDisplayMetrics().widthPixels;
//                    pro_img.setLayoutParams(new LinearLayout.LayoutParams((int) scaleWidth, (int) scaleHeigth));
//                    pro_img.setScaleType(ImageView.ScaleType.FIT_XY);
//
//                    zoomOut = true;
//                }
            }

        });
    }

    private void loadPhoto(ImageView imageView) {

        ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.image_layout,
                (ViewGroup) view.findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });


        imageDialog.create();
        imageDialog.show();
    }

    private void prepareMovieData() {
        ModelProfile account = new ModelProfile(R.drawable.ic_seo, "kamal Rafiq");
        arrayList.add(account);
        account = new ModelProfile(R.drawable.ic_seo, "sajid Rahim");
        arrayList.add(account);
        account = new ModelProfile(R.drawable.ic_seo, "waseem Azeem");
        arrayList.add(account);
        profileAdapter.notifyDataSetChanged();
    }

}
