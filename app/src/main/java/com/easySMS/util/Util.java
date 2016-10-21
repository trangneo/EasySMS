package com.easySMS.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.easySMS.R;

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

    /**
     *     check internet connection ----/

     */

    public static  boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
