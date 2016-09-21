package com.example.poiuyt.easysms.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by poiuyt on 9/8/16.
 */

public class User implements Parcelable {
    public String password;
    public String email;
    public String username;
    public String created;

    public User(String username, String email, String password, String created) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.created = created;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
