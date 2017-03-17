package com.example.nishant.myapplication;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Nishant on 04-03-2017.
 */

public class prefs {

    private SharedPreferences mPreferences;


    public prefs(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public prefs(Context context, String name, int mode) {
        mPreferences = context.getSharedPreferences(name, mode);
    }




    public void putString (String key, String value) {
        SharedPreferences.Editor prefEdit = mPreferences.edit();
        prefEdit.putString(key, value);
        prefEdit.apply();
    }


    public String getString(String key, String defValue) throws ClassCastException {
        return mPreferences.getString(key, defValue);
    }


}
