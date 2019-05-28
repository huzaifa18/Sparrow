package com.example.bracketsol.sparrow.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.google.gson.Gson;


public class SaveSharedPreference {

    public static final String SHARED_PREF_NAME = "mysharedpref12";
    public static SaveSharedPreference mInstance;
    public static Context mctx;
    public static String LOGGED_IN_PREF = "logged_in_status";
    public static String USER_PREF = "user_data";

    public SaveSharedPreference(Context context) {
        mctx = context;
    }


    public static synchronized SaveSharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SaveSharedPreference(context);
        }
        return mInstance;
    }

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getLoginStatus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(LOGGED_IN_PREF, false);

    }

    public boolean addUsertoPref(UserData user) {
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);

        editor.putString(USER_PREF, json);
        editor.apply();
        return true;
    }

    public UserData getUser() {
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(USER_PREF, "");
        UserData obj = gson.fromJson(json, UserData.class);
        Toast.makeText(mctx, "" + obj, Toast.LENGTH_SHORT).show();
        return obj;
    }

    /**
     * Set the Login Status
     *
     * @param context
     * @param loggedIn
     */
    public void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     *
     * @param context
     * @return boolean: login status
     */
    public boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}
