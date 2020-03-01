package com.example.barakah.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.barakah.R;
import com.example.barakah.ui.fragment.RegisterFragment;

public class BarakahUtils {


    public static void setCurrentFragment(FragmentActivity activity, int pContainerId, Fragment pFragment, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (pFragment instanceof RegisterFragment
        ) {
            fragmentTransaction.replace(pContainerId, pFragment, tag);
        } else {
            fragmentTransaction.add(pContainerId, pFragment, tag);
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    public static Dialog customProgressDialog(Context context) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }


    public static String getPref(String key, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static boolean getPrefBoolean(String key, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    public static void putPref(String key, String value, Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putPrefBoolean(String key, boolean value, Context context) {
        if (context == null)
            return;

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void deletesharedKey(Context context, String key) {
        if (context == null)
            return;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.commit();

    }

    public static void deletesharedData(Context context) {
        if (context == null)
            return;
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

    }

    public static void toastMessgae(Context context, String message, int time) {
        Toast.makeText(context, message, time).show();

    }

}

