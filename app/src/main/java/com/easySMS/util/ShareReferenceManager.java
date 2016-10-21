package com.easySMS.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.easySMS.EasySMS;
import com.easySMS.model.Member;
import com.google.gson.Gson;


public class ShareReferenceManager {
    static String CONTENT = "easySMS_data_your";

    public static void saveData(Member member) {
        SharedPreferences.Editor editor = EasySMS.getContext().getSharedPreferences(Constants.YOUR, Context.MODE_PRIVATE).edit();
        String your = new Gson().toJson(member, Member.class);
        editor.putString(CONTENT, your).commit();
    }

    public static void deleteData() {
        SharedPreferences.Editor editor = EasySMS.getContext().getSharedPreferences(Constants.YOUR, Context.MODE_PRIVATE).edit();

        editor.putString(CONTENT, "").commit();
    }

    public static Member getData() {
        SharedPreferences sharedPreferences = EasySMS.getContext().getSharedPreferences(Constants.YOUR, Context.MODE_PRIVATE);
        String your = sharedPreferences.getString(CONTENT, "none");
        if (!your.isEmpty()) {
            return new Gson().fromJson(your, Member.class);
        }
        return null;
    }

}
