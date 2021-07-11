package com.example.animation.Models;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DialogData implements Parcelable {
    @io.objectbox.annotation.Id
    long id;
    public DialogData(String movieImage, String movieName) {
        this.movieImage = movieImage;
        this.movieName = movieName;
    }

    private String movieImage;
    private String movieName;

    protected DialogData(Parcel in) {
        id = in.readLong();
        movieImage = in.readString();
        movieName = in.readString();
    }

    public static final Creator<DialogData> CREATOR = new Creator<DialogData>() {
        @Override
        public DialogData createFromParcel(Parcel in) {
            return new DialogData(in);
        }

        @Override
        public DialogData[] newArray(int size) {
            return new DialogData[size];
        }
    };

    public String getMovieImage() {
        return movieImage;
    }

    public String getMovieName() {
        return movieName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(movieImage);
        dest.writeString(movieName);
    }
}
