package com.magicfrost.bridge.internal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MagicFrost on 2019-07-08.
 */
public class Request implements Parcelable {

    private String data;

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    public Request(String data) {
        this.data = data;
    }

    protected Request(Parcel in) {
        this.data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "data='" + data + '\'' +
                '}';
    }
}
