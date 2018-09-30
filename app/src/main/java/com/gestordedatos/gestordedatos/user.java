package com.gestordedatos.gestordedatos;

import android.os.Parcel;
import android.os.Parcelable;

public class user implements Parcelable{
    String username;

    public user(final String username) {
        this.username = username;
    }

    protected user(Parcel in) {
        username = in.readString();
    }

    public static final Creator<user> CREATOR = new Creator<user>() {
        @Override
        public user createFromParcel(Parcel in) {
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(username);
    }
}
