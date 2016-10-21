package com.easySMS.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberModel implements Parcelable {

    String username;
    String password;
    String email;
    String created;
    String connection;
    String uId;

    public MemberModel() {
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MemberModel(Parcel parcelIn) {
        username = parcelIn.readString();
        email = parcelIn.readString();
        created = parcelIn.readString();
        uId = parcelIn.readString();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(created);
        parcel.writeString(uId);
    }

    public long createdAtYours() {
        return Long.parseLong(getCreated());
    }

    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel parcel) {
            return new MemberModel(parcel);
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };
}