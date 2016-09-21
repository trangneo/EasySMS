package com.example.poiuyt.easysms.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberModel implements Parcelable {

    String username;
    String password;
    String email;
    String mRecipientUid;
    String created;
    String connection;
    String yourName;
    String yourEmail;
    String yourId;
    String yourCreatedAt;

    public MemberModel() {
    }

    public MemberModel(Parcel parcelIn) {
        username = parcelIn.readString();
        email = parcelIn.readString();
        created = parcelIn.readString();
        mRecipientUid = parcelIn.readString();
        yourName = parcelIn.readString();
        yourId = parcelIn.readString();
        yourEmail = parcelIn.readString();
        yourCreatedAt = parcelIn.readString();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getmRecipientUid() {
        return mRecipientUid;
    }

    public void setmRecipientUid(String mRecipientUid) {
        this.mRecipientUid = mRecipientUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }

    public String getYourEmail() {
        return yourEmail;
    }

    public void setYourEmail(String yourEmail) {
        this.yourEmail = yourEmail;
    }

    public String getYourId() {
        return yourId;
    }

    public void setYourId(String yourId) {
        this.yourId = yourId;
    }

    public String getYourCreatedAt() {
        return yourCreatedAt;
    }

    public void setYourCreatedAt(String yourCreatedAt) {
        this.yourCreatedAt = yourCreatedAt;
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
        parcel.writeString(mRecipientUid);
        parcel.writeString(yourName);
        parcel.writeString(yourId);
        parcel.writeString(yourEmail);
        parcel.writeString(yourCreatedAt);
    }

    public long createdAtYours() {
        return Long.parseLong(getYourCreatedAt());
    }

    public long createdAtSender() {
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