package com.kp.twitterclient.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Entity implements Parcelable {
    private String mediaType;
    private String mediaUrl;
    private String mediaUrlThumb;
    private String mediaUrlSmall;
    private String mediaUrlMedium;
    private String mediaUrlLarge;
    private String mediaUrlDisk;
    private String mediaUrlThumbDisk;
    private String mediaUrlSmallDisk;
    private String mediaUrlMediumDisk;
    private String mediaUrlLargeDisk;

    public Entity() {

    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrlThumb() {
        return mediaUrlThumb;
    }

    public void setMediaUrlThumb(String mediaUrlThumb) {
        this.mediaUrlThumb = mediaUrlThumb;
    }

    public String getMediaUrlSmall() {
        return mediaUrlSmall;
    }

    public void setMediaUrlSmall(String mediaUrlSmall) {
        this.mediaUrlSmall = mediaUrlSmall;
    }

    public String getMediaUrlMedium() {
        return mediaUrlMedium;
    }

    public void setMediaUrlMedium(String mediaUrlMedium) {
        this.mediaUrlMedium = mediaUrlMedium;
    }

    public String getMediaUrlLarge() {
        return mediaUrlLarge;
    }

    public void setMediaUrlLarge(String mediaUrlLarge) {
        this.mediaUrlLarge = mediaUrlLarge;
    }

    public String getMediaUrlDisk() {
        return mediaUrlDisk;
    }

    public void setMediaUrlDisk(String mediaUrlDisk) {
        this.mediaUrlDisk = mediaUrlDisk;
    }

    public String getMediaUrlThumbDisk() {
        return mediaUrlThumbDisk;
    }

    public void setMediaUrlThumbDisk(String mediaUrlThumbDisk) {
        this.mediaUrlThumbDisk = mediaUrlThumbDisk;
    }

    public String getMediaUrlSmallDisk() {
        return mediaUrlSmallDisk;
    }

    public void setMediaUrlSmallDisk(String mediaUrlSmallDisk) {
        this.mediaUrlSmallDisk = mediaUrlSmallDisk;
    }

    public String getMediaUrlMediumDisk() {
        return mediaUrlMediumDisk;
    }

    public void setMediaUrlMediumDisk(String mediaUrlMediumDisk) {
        this.mediaUrlMediumDisk = mediaUrlMediumDisk;
    }

    public String getMediaUrlLargeDisk() {
        return mediaUrlLargeDisk;
    }

    public void setMediaUrlLargeDisk(String mediaUrlLargeDisk) {
        this.mediaUrlLargeDisk = mediaUrlLargeDisk;
    }

    // Get the Entity Information
    public static Entity fromJSON(JSONObject jsonObject) {
        Entity entity = null;
        if (jsonObject != null) {
            try {
                if (jsonObject.optJSONArray("media") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("media");
                    if (jsonArray.optJSONObject(0) != null) {
                        JSONObject mediaJSON = jsonArray.getJSONObject(0);
                        if (mediaJSON.optString("media_url") != null) {
                            entity = new Entity();
                            entity.mediaType = "photo"; // currently twitter returns only photos
                            entity.mediaUrl = mediaJSON.getString("media_url");
                            entity.mediaUrlThumb = entity.mediaUrl + ":thumb";
                            entity.mediaUrlSmall = entity.mediaUrl + ":small";
                            entity.mediaUrlMedium = entity.mediaUrl + ":medium";
                            entity.mediaUrlLarge = entity.mediaUrl + ":large";
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mediaType);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.mediaUrlThumb);
        dest.writeString(this.mediaUrlSmall);
        dest.writeString(this.mediaUrlMedium);
        dest.writeString(this.mediaUrlLarge);
        dest.writeString(this.mediaUrlDisk);
        dest.writeString(this.mediaUrlThumbDisk);
        dest.writeString(this.mediaUrlSmallDisk);
        dest.writeString(this.mediaUrlMediumDisk);
        dest.writeString(this.mediaUrlLargeDisk);
    }

    protected Entity(Parcel in) {
        this.mediaType = in.readString();
        this.mediaUrl = in.readString();
        this.mediaUrlThumb = in.readString();
        this.mediaUrlSmall = in.readString();
        this.mediaUrlMedium = in.readString();
        this.mediaUrlLarge = in.readString();
        this.mediaUrlDisk = in.readString();
        this.mediaUrlThumbDisk = in.readString();
        this.mediaUrlSmallDisk = in.readString();
        this.mediaUrlMediumDisk = in.readString();
        this.mediaUrlLargeDisk = in.readString();
    }

    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
}
