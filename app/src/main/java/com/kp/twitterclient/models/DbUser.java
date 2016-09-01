package com.kp.twitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "USERS")
public class DbUser extends Model {

    @Column(name = "REMOTE_ID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;

    @Column(name = "USER_ID")
    public long userId;

    @Column(name = "USER_NAME")
    public String userName;

    @Column(name = "TWITTER_HANDLE")
    public String twitterHandle;

    @Column(name = "PROFILE_IMAGE_URL")
    public String profileImageUrl;

    @Column(name = "PROFILE_IMAGE_URL_DISK")
    public String profileImageUrlDisk;

    @Column(name = "BANNER_IMAGE_URL")
    public String bannerImageUrl;

    @Column(name = "BANNER_IMAGE_URL_DISK")
    public String bannerImageUrlDisk;

    @Column(name = "TAG_LINE")
    public String tagLine;

    @Column(name = "NUM_FOLLOWERS")
    public int numFollowers;

    @Column(name = "NUM_FOLLOWING")
    public int numFollowing;

    @Column(name = "NUM_TWEETS")
    public int numTweets;

    public DbUser() {
        super();
    }

    public DbUser(long remoteId, long userId, String userName, String twitterHandle,
                  String profileImageUrl, String profileImageUrlDisk, String bannerImageUrl,
                  String bannerImageUrlDisk, String tagLine, int numFollowers, int numFollowing,
                  int numTweets) {
        this.remoteId = remoteId;
        this.userId = userId;
        this.userName = userName;
        this.twitterHandle = twitterHandle;
        this.profileImageUrl = profileImageUrl;
        this.profileImageUrlDisk = profileImageUrlDisk;
        this.bannerImageUrl = bannerImageUrl;
        this.bannerImageUrlDisk = bannerImageUrlDisk;
        this.tagLine = tagLine;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
        this.numTweets = numTweets;
    }

    public DbUser(long remoteId, User user) {
        this(remoteId,
                user.getUserId(),
                user.getUserName(),
                user.getTwitterHandle(),
                user.getProfileImageUrl(),
                user.getProfileImageUrlDisk(),
                user.getBannerImageUrl(),
                user.getBannerImageUrlDisk(),
                user.getTagLine(),
                user.getNumFollowers(),
                user.getNumFollowing(),
                user.getNumTweets());
    }

    // Return all the tweets belonging to this user from Tweets table
    public List<DbTweet> getTweetsInDb() {
        return getMany(DbTweet.class, "USER");
    }
}
