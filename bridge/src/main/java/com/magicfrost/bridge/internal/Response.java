package com.magicfrost.bridge.internal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MagicFrost on 2019-07-08.
 */
public class Response implements Parcelable {

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    protected Response(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
