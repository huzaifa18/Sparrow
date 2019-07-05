package com.example.bracketsol.sparrow.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Prefs {

    public static void addingUserUDID(Context context, final String userUDID) {
        SharedPreferences userUDIDPref = context.getSharedPreferences("betaar_udid", 0);
        SharedPreferences.Editor editor = userUDIDPref.edit();
        editor.putString("user_udid", userUDID);
        editor.commit();

    }

    public static String gettUserUDID(Context context) {

        SharedPreferences userUDIDPref = context.getSharedPreferences("betaar_udid", 0);
        String UDID = userUDIDPref.getString("user_udid", "-1");
        return UDID;
    }

    public static void addPrefsForLogin(Context context, final int user_id,
                                        final String username,
                                        final String fullname,
                                        final String email, final String phone,final String profession, String auth) {

        SharedPreferences userLoginPref = context.getSharedPreferences("betaar_user", 0);
        SharedPreferences.Editor editor = userLoginPref.edit();
        editor.putInt("_id", user_id);
        editor.putString("username", username);
        editor.putString("full_name", fullname);
        editor.putString("email", email);
        editor.putString("phone_no", phone);
        editor.putString("profession", profession);
        editor.putString("token", auth);
        editor.commit();
    }

    public static void addPrefsForUserId(Context context, final int user_id) {

        SharedPreferences userLoginPref = context.getSharedPreferences("betaar_user", 0);
        SharedPreferences.Editor editor = userLoginPref.edit();
        editor.putInt("_id", user_id);
        editor.commit();


    }

    //getting user id
    public static int getUserIDFromPref(Context context) {
        SharedPreferences userLoginPref = context.getSharedPreferences("betaar_user", 0);
        int userId = userLoginPref.getInt("_id", 0);
        return userId;
    }

    //getting user id
    public static String getUserToken(Context context) {
        SharedPreferences userLoginPref = context.getSharedPreferences("betaar_user", 0);
        String userId = userLoginPref.getString("token", "-1");
        return userId;
    }

    //gettitng user role
    public static String getUserRoleFromPref(Context context) {
        SharedPreferences userRolePref = context.getSharedPreferences("betaar_user", 0);
        String userId = userRolePref.getString("role", "-1");
        return userId;
    }

    //gettitng full name
    public static String getUserFullNameFromPref(Context context) {
        SharedPreferences preUserFullName = context.getSharedPreferences("betaar_user", 0);
        String fullName = preUserFullName.getString("name", "-1");
        return fullName;
    }

    //gettitng user name
    public static String getUserNameFromPref(Context context) {
        SharedPreferences preUserName = context.getSharedPreferences("betaar_user", 0);
        String username = preUserName.getString("username", "-1");
        return username;
    }

    //gettitng user name
    public static String getEmailFromPref(Context context) {
        SharedPreferences preUserEmail = context.getSharedPreferences("betaar_user", 0);
        String email = preUserEmail.getString("email", "-1");
        return email;
    }

    //gettitng password
    public static String getPasswordFromPref(Context context) {
        SharedPreferences prepassword = context.getSharedPreferences("betaar_user", 0);
        String password = prepassword.getString("password", "-1");
        return password;
    }


    public static ArrayList<String> getAllUserValueFromPref(Context context) {

        ArrayList<String> arrayList = new ArrayList<>();
        SharedPreferences userLoginPref = context.getSharedPreferences("betaar_user", 0);
        String userId = userLoginPref.getString("_id", "");
        String name = userLoginPref.getString("username", "");
        String email = userLoginPref.getString("email", "");
        String phone = userLoginPref.getString("phone", "");
        String password = userLoginPref.getString("password", "");
        arrayList.add(userId);
        arrayList.add(name);
        arrayList.add(email);
        arrayList.add(phone);
        arrayList.add(password);

        return arrayList;
    }

    public static void clearPrefData(Context context) {
        SharedPreferences userLoginPref = context.getSharedPreferences("betaar_user", 0);
        SharedPreferences.Editor editor = userLoginPref.edit();
        editor.clear();
        editor.commit();
    }
}
