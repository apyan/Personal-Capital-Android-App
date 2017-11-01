package com.example.pctesting.personalcapitaltest.AppObjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The news extracted as from the XML file
 */

public class ItemArticleXML implements Parcelable {

    // Variables
    public String titleTag;
    public String linkTag;
    public String pubDateTag;
    public String descriptionTag;
    public String contentEncoded;
    public String imageContentTag;

    // Constructor
    public ItemArticleXML() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Storing the data to Parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleTag);
        dest.writeString(linkTag);
        dest.writeString(pubDateTag);
        dest.writeString(descriptionTag);
        dest.writeString(contentEncoded);
        dest.writeString(imageContentTag);
    }

    // For Creator
    private ItemArticleXML(Parcel in){
        this.titleTag = in.readString();
        this.linkTag = in.readString();
        this.pubDateTag = in.readString();
        this.descriptionTag = in.readString();
        this.contentEncoded = in.readString();
        this.imageContentTag = in.readString();
    }

    public static final Parcelable.Creator<ItemArticleXML> CREATOR = new Parcelable.Creator<ItemArticleXML>() {
        @Override
        public ItemArticleXML createFromParcel(Parcel source) {
            return new ItemArticleXML(source);
        }

        @Override
        public ItemArticleXML[] newArray(int size) {
            return new ItemArticleXML[size];
        }
    };
}
