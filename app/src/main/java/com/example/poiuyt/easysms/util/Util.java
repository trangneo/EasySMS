package com.example.poiuyt.easysms.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.poiuyt.easysms.R;
import com.example.poiuyt.easysms.data.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by poiuyt on 9/9/16.
 */

public  class Util {
    static Toast toast;
    public static void showErrorMessage(Context context, String errorMessage) {
        new AlertDialog.Builder(context)
                .setMessage(errorMessage)
                .setTitle(context.getString(R.string.title_alert))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    public static void showToast(Context context, String message) {
        if(toast !=null){
            toast.cancel();
        }
        toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static boolean validateEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(password);
        return !matcher.find() && password.length() > 5;
    }
    public static boolean isEmptyString(String string) {
        return string == null || string.trim().equals("") || string.trim().length() <= 0;
    }
    public static void saveMyProfile( Context context, User user) {
//        SharedPreferences pref = context.getSharedPreferences(Constants.SAVE_DATA, Context.MODE_PRIVATE);
//        String profile = new Gson().toJson(user, User.class);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(PROFILE, profile);
//        editor.apply();
    }
    
}
