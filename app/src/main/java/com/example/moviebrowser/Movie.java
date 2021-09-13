package com.example.moviebrowser;

import android.os.Parcel;
import android.os.Parcelable;

/*
This class implements a movie object which contains the movie details necessary for the app.
It always implements the Parcelable interface in order to be sent to a different activity which displays further information.
*/

public class Movie implements Parcelable {
    private String mName,mLang,mDetails,mImg,mRelease,mRating;

    public Movie(String mName, String mLang, String mDetails, String mImg, String mRelease, String mRating) {
        this.mName = mName;
        this.mLang = mLang;
        this.mDetails = mDetails;
        this.mImg = mImg;
        this.mRelease = mRelease;
        this.mRating = mRating;
    }



    protected Movie(Parcel in) {
        mName = in.readString();
        mDetails = in.readString();
        mImg = in.readString();
        mRelease = in.readString();
        mRating = in.readString();
        mLang = in.readString();

    }

    public String getmLang() {
        return mLang;
    }

    public void setmLang(String mLang) {
        this.mLang = mLang;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


    public String getmDetails() {
        return mDetails;
    }


    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public String getmRelease() {
        return mRelease;
    }

    public void setmRelease(String mRelease) {
        this.mRelease = mRelease;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mDetails);
        parcel.writeString(mImg);
        parcel.writeString(mRelease);
        parcel.writeString(mRating);
        parcel.writeString(mLang);
    }
}
