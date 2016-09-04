package com.kp.twitterclient.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.kp.twitterclient.applications.TwitterClient;
import com.kp.twitterclient.helpers.DateUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tweet implements Parcelable {
    private long tweetId;
    private String tweetBody;
    private String createdAt;
    private String formattedCreatedAt;
    private String relativeCreationTime;
    private boolean isFavorited;
    private boolean isRetweeted;
    private int retweetCount;
    private TweetType tweetType;
    private User user;
    private Entity entity;

    public Tweet() {

    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetBody() {
        return tweetBody;
    }

    public void setTweetBody(String tweetBody) {
        this.tweetBody = tweetBody;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFormattedCreatedAt() {
        return formattedCreatedAt;
    }

    public void setFormattedCreatedAt(String formattedCreatedAt) {
        this.formattedCreatedAt = formattedCreatedAt;
    }

    public String getRelativeCreationTime() {
        return relativeCreationTime;
    }

    public void setRelativeCreationTime(String relativeCreationTime) {
        this.relativeCreationTime = relativeCreationTime;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public TweetType getTweetType() {
        return tweetType;
    }

    public void setTweetType(TweetType tweetType) {
        this.tweetType = tweetType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    // Get the Tweet Information
    private static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            if (jsonObject.optInt("id") != 0)
                tweet.tweetId = jsonObject.getLong("id");
            else
                tweet.tweetId = 0;
            if (jsonObject.optString("text") != null)
                tweet.tweetBody = jsonObject.getString("text");
            else
                tweet.tweetBody = null;
            if (jsonObject.optString("created_at") != null) {
                tweet.createdAt = jsonObject.getString("created_at");
                tweet.formattedCreatedAt = DateUtilities.getFormattedTime(jsonObject.getString("created_at"));
                tweet.relativeCreationTime = DateUtilities.getRelativeTime(jsonObject.getString("created_at"));
            }
            else {
                tweet.createdAt = null;
                tweet.formattedCreatedAt = null;
                tweet.relativeCreationTime = null;
            }
            if (jsonObject.optBoolean("favorited"))
                tweet.isFavorited = jsonObject.getBoolean("favorited");
            else
                tweet.isFavorited = false;
            if (jsonObject.optBoolean("retweeted"))
                tweet.isRetweeted = jsonObject.getBoolean("retweeted");
            else
                tweet.isRetweeted = false;
            if (jsonObject.optInt("retweet_count") != 0)
                tweet.retweetCount = jsonObject.getInt("retweet_count");
            else
                tweet.retweetCount = 0;
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.entity = Entity.fromJSON(jsonObject.getJSONObject("entities"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    // Pass in an array of JSON items and return a list of tweets
    public static ArrayList<Tweet> fromJSONArray(TweetType tweetType, JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        if (jsonArray !=  null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    if (jsonArray.optJSONObject(i) != null) {
                        JSONObject tweetsJson = jsonArray.getJSONObject(i);
                        Tweet tweet = Tweet.fromJSON(tweetsJson);
                        if (tweet != null) {
                            tweet.tweetType = tweetType;
                            tweets.add(tweet);
                        }
                        if (tweetType.equals(TweetType.HOME_TIMELINE))
                            TwitterClient.MAX_TWEET_ID_HOME = tweet.getTweetId();
                        // TODO: MENTIONS and USER TIMELINES
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return tweets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.tweetId);
        dest.writeString(this.tweetBody);
        dest.writeString(this.createdAt);
        dest.writeString(this.formattedCreatedAt);
        dest.writeString(this.relativeCreationTime);
        dest.writeByte(this.isFavorited ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRetweeted ? (byte) 1 : (byte) 0);
        dest.writeInt(this.retweetCount);
        dest.writeInt(this.tweetType == null ? -1 : this.tweetType.ordinal());
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.entity, flags);
    }

    protected Tweet(Parcel in) {
        this.tweetId = in.readLong();
        this.tweetBody = in.readString();
        this.createdAt = in.readString();
        this.formattedCreatedAt = in.readString();
        this.relativeCreationTime = in.readString();
        this.isFavorited = in.readByte() != 0;
        this.isRetweeted = in.readByte() != 0;
        this.retweetCount = in.readInt();
        int tmpTweetType = in.readInt();
        this.tweetType = tmpTweetType == -1 ? null : TweetType.values()[tmpTweetType];
        this.user = in.readParcelable(User.class.getClassLoader());
        this.entity = in.readParcelable(Entity.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
