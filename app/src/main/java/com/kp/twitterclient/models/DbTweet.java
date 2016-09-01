package com.kp.twitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TWEETS")
public class DbTweet extends Model {

    @Column(name = "REMOTE_ID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;

    @Column(name = "TWEET_TYPE")
    public int tweetType;

    @Column(name = "TWEEI_ID")
    public long tweetId;

    @Column(name = "TWEET_BODY")
    public String tweetBody;

    @Column(name = "CREATED_AT")
    public String createdAt;

    @Column(name = "FORMATTED_CREATED_AT")
    public String formattedCreatedAt;

    @Column(name = "RELATIVE_CREATION_TIME")
    public String relativeCreationTime;

    @Column(name = "FAVORITED")
    public char favorited;

    @Column(name = "RETWEETED")
    public char retweeted;

    @Column(name = "RETWEET_COUNT")
    public int retweetCount;

    @Column(name = "MEDIA_TYPE")
    public String mediaType;

    @Column(name = "MEDIA_URL")
    public String mediaUrl;

    @Column(name = "MEDIA_URL_DISK")
    public String mediaUrlDisk;

    @Column(name = "MEDIA_URL_THUMB")
    public String mediaUrlThumb;

    @Column(name = "MEDIA_URL_THUMB_DISK")
    public String mediaUrlThumbDisk;

    @Column(name = "MEDIA_URL_SMALL")
    public String mediaUrlSmall;

    @Column(name = "MEDIA_URL_SMALL_DISK")
    public String mediaUrlSmallDisk;

    @Column(name = "MEDIA_URL_MEDIUM")
    public String mediaUrlMedium;

    @Column(name = "MEDIA_URL_MEDIUM_DISK")
    public String mediaUrlMediumDisk;

    @Column(name = "MEDIA_URL_LARGE")
    public String mediaUrlLarge;

    @Column(name = "MEDIA_URL_LARGE_DISK")
    public String mediaUrlLargeDisk;

    @Column(name = "USER", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public DbUser user;

    public DbTweet() {
        super();
    }

    public DbTweet(long remoteId, int tweetType, long tweetId, String tweetBody, String createdAt,
                   String formattedCreatedAt, String relativeCreationTime, char favorited,
                   char retweeted, int retweetCount, String mediaType, String mediaUrl,
                   String mediaUrlDisk, String mediaUrlThumb, String mediaUrlThumbDisk,
                   String mediaUrlSmall, String mediaUrlSmallDisk, String mediaUrlMedium,
                   String mediaUrlMediumDisk, String mediaUrlLarge, String mediaUrlLargeDisk, DbUser user) {
        this.remoteId = remoteId;
        this.tweetType = tweetType;
        this.tweetId = tweetId;
        this.tweetBody = tweetBody;
        this.createdAt = createdAt;
        this.formattedCreatedAt = formattedCreatedAt;
        this.relativeCreationTime = relativeCreationTime;
        this.favorited = favorited;
        this.retweeted = retweeted;
        this.retweetCount = retweetCount;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.mediaUrlDisk = mediaUrlDisk;
        this.mediaUrlThumb = mediaUrlThumb;
        this.mediaUrlThumbDisk = mediaUrlThumbDisk;
        this.mediaUrlSmall = mediaUrlSmall;
        this.mediaUrlSmallDisk = mediaUrlSmallDisk;
        this.mediaUrlMedium = mediaUrlMedium;
        this.mediaUrlMediumDisk = mediaUrlMediumDisk;
        this.mediaUrlLarge = mediaUrlLarge;
        this.mediaUrlLargeDisk = mediaUrlLargeDisk;
        this.user = user;
    }
}
