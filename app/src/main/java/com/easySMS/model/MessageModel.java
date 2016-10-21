package com.easySMS.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageModel implements Parcelable {

    public String recipient;
    public String sender;
    public String message;
    public MessageModel() {
    }

    public MessageModel(Parcel parcelIn) {
        recipient = parcelIn.readString();
        sender= parcelIn.readString();
        message = parcelIn.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sender);
        parcel.writeString(recipient);
        parcel.writeString(message);
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