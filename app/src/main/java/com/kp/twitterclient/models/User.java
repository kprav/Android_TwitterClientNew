package com.kp.twitterclient.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable, Comparable<User> {
    private long userId;
    private String userName;
    private String twitterHandle;
    private String profileImageUrl;
    private String profileImageUrlDisk;
    private String bannerImageUrl;
    private String bannerImageUrlDisk;
    private String tagLine;
    private int numFollowers;
    private int numFollowing;
    private int numTweets;

    public User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrlDisk() {
        return profileImageUrlDisk;
    }

    public void setProfileImageUrlDisk(String profileImageUrlDisk) {
        this.profileImageUrlDisk = profileImageUrlDisk;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getBannerImageUrlDisk() {
        return bannerImageUrlDisk;
    }

    public void setBannerImageUrlDisk(String bannerImageUrlDisk) {
        this.bannerImageUrlDisk = bannerImageUrlDisk;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public int getNumTweets() {
        return numTweets;
    }

    public void setNumTweets(int numTweets) {
        this.numTweets = numTweets;
    }

    // Get the User Information
    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();
        try {
            if (jsonObject.getLong("id") != 0)
                user.userId = jsonObject.getLong("id");
            else
                user.userId = 0;
            if (jsonObject.optString("name") != null)
                user.userName = jsonObject.getString("name");
            else
                user.userName = null;
            if (jsonObject.optString("screen_name") != null)
                user.twitterHandle = "@" + jsonObject.getString("screen_name");
            else
                user.twitterHandle = null;
            if (jsonObject.optString("profile_image_url") != null &&
                    !jsonObject.optString("profile_image_url").equals(""))
                user.profileImageUrl = jsonObject.getString("profile_image_url");
            else
                user.profileImageUrl = null;
            if (jsonObject.optString("description") != null)
                user.tagLine = jsonObject.getString("description");
            else
                user.tagLine = null;
            if (jsonObject.optInt("followers_count") != 0)
                user.numFollowers = jsonObject.getInt("followers_count");
            else
                user.numFollowers = 0;
            if (jsonObject.optInt("friends_count") != 0)
                user.numFollowing = jsonObject.getInt("friends_count");
            else
                user.numFollowing = 0;
            if (jsonObject.optInt("statuses_count") != 0)
                user.numTweets = jsonObject.getInt("statuses_count");
            else
                user.numTweets = 0;
            if (jsonObject.optString("profile_banner_url") != null &&
                    !jsonObject.optString("profile_banner_url").equals(""))
                user.bannerImageUrl = jsonObject.getString("profile_banner_url");
            else
                user.bannerImageUrl = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.twitterHandle);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.profileImageUrlDisk);
        dest.writeString(this.bannerImageUrl);
        dest.writeString(this.bannerImageUrlDisk);
        dest.writeString(this.tagLine);
        dest.writeInt(this.numFollowers);
        dest.writeInt(this.numFollowing);
        dest.writeInt(this.numTweets);
    }

    protected User(Parcel in) {
        this.userId = in.readLong();
        this.userName = in.readString();
        this.twitterHandle = in.readString();
        this.profileImageUrl = in.readString();
        this.profileImageUrlDisk = in.readString();
        this.bannerImageUrl = in.readString();
        this.bannerImageUrlDisk = in.readString();
        this.tagLine = in.readString();
        this.numFollowers = in.readInt();
        this.numFollowing = in.readInt();
        this.numTweets = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int compareTo(@NonNull User another) {
        if (this.twitterHandle.equals(another.twitterHandle))
            return 0;
        else
            return 1;
    }
}
