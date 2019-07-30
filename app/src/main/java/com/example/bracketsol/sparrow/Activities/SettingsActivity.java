package com.example.bracketsol.sparrow.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bracketsol.sparrow.R;
import com.example.bracketsol.sparrow.Utils.Prefs;

import net.cachapa.expandablelayout.ExpandableLayout;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout ll_invite, ll_policy, ll_timeline, ll_live, ll_report, ll_help, ll_term_server,
            ll_about, ll_change_password, logout;

    RelativeLayout ll_account,ll_events,ll_multimedia_display,ll_noti;

    CardView cv_noti,cv_ringtone;

    ExpandableLayout el_account,el_events,el_multi,el_noti,el_ringtone;

    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Init();
        Listeners();
    }

    private void Init() {

        ll_account = findViewById(R.id.ll_account);
        ll_invite = findViewById(R.id.ll_invite);
        ll_policy = findViewById(R.id.ll_policy);
        ll_timeline = findViewById(R.id.ll_timeline);
        ll_live = findViewById(R.id.ll_live);
        ll_events = findViewById(R.id.ll_event);
        ll_multimedia_display = findViewById(R.id.ll_multimedia);
        ll_noti = findViewById(R.id.ll_noti);
        ll_report = findViewById(R.id.ll_report_problem);
        ll_help = findViewById(R.id.ll_help);
        ll_term_server = findViewById(R.id.ll_term_server);
        ll_about = findViewById(R.id.ll_about);
        ll_change_password = findViewById(R.id.ll_change_password);
        logout = findViewById(R.id.logout);

        cv_noti = findViewById(R.id.cv_noti);
        cv_ringtone = findViewById(R.id.cv_ringtone);


        el_ringtone = findViewById(R.id.el_ringtone);
        el_noti = findViewById(R.id.el_noti);
        el_events = findViewById(R.id.el_events);
        el_account = findViewById(R.id.el_account);
        el_multi = findViewById(R.id.el_multimedia_and_display);

        iv_back = findViewById(R.id.iv_back);

    }

    private void Listeners() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ll_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(SettingsActivity.this, OthersActivity.class);
                intent.putExtra("user_id", Prefs.getUserIDFromPref(SettingsActivity.this));
                startActivity(intent);*/

                el_account.toggle();

            }
        });

        cv_ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(SettingsActivity.this, OthersActivity.class);
                intent.putExtra("user_id", Prefs.getUserIDFromPref(SettingsActivity.this));
                startActivity(intent);*/

                el_ringtone.toggle();

            }
        });

        ll_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareApp();

            }
        });

        ll_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        ll_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ll_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ll_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(SettingsActivity.this,EventSettings.class));
                el_events.toggle();

            }
        });

        ll_multimedia_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(SettingsActivity.this,MultiMediaAndDisplaySettings.class));
                el_multi.toggle();

            }
        });

        ll_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                el_noti.toggle();
                //startActivity(new Intent(SettingsActivity.this,NotificationSettings.class));

            }
        });

        ll_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ReportDialog();
                startActivity(new Intent(SettingsActivity.this, ReportActivity.class));

            }
        });

        ll_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        ll_term_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SettingsActivity.this, ContactUs.class));

            }
        });

        ll_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SettingsActivity.this, ChangePassword.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("betaar_user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                startActivity(new Intent(SettingsActivity.this, Login.class));
            }
        });

        cv_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                el_noti.toggle();
            }
        });

    }

    public void shareApp() {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //String shareBodyText = "Please Download this App \n https://play.google.com/store/apps/details?id=hubbud.com";
        String shareBodyText = "Please Download Sirius!";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sirius Android App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));

    }

    public void ReportDialog() {
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_report);

        Spinner sp_type_spinner = dialog.findViewById(R.id.sp_type);
        String[] years = {"Suggestion", "Complaint"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(SettingsActivity.this, R.layout.spinner_text, years);
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_type_spinner.setAdapter(langAdapter);

        TextView yes = (TextView) dialog.findViewById(R.id.yesCall);
        TextView no = (TextView) dialog.findViewById(R.id.noCall);
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        sp_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG", "Spinner: " + i);

                if (i == 0) {


                } else {


                }

                //selectedItem = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void HelpDialog() {
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_help);
        TextView yes = (TextView) dialog.findViewById(R.id.yesCall);
        TextView no = (TextView) dialog.findViewById(R.id.noCall);
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}