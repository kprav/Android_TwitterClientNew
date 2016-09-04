package com.kp.twitterclient.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kp.twitterclient.R;
import com.kp.twitterclient.helpers.DeviceDimensionsHelper;
import com.kp.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvTwitterHandle;
        TextView tvCreationTime;
        TextView tvTweetBody;
        RelativeLayout rlInlinePhoto;
    }

    private ViewHolder viewHolder;
    private static final String LOG_TAG = "TweetsArrayAdapter";

    public TweetsArrayAdapter(Context context, List<Tweet> tweetList) {
        super(context, android.R.layout.simple_list_item_1, tweetList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);
        boolean newConvertView = false;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvTwitterHandle = (TextView) convertView.findViewById(R.id.tvTwitterHandle);
            viewHolder.tvCreationTime = (TextView) convertView.findViewById(R.id.tvCreationTime);
            viewHolder.tvTweetBody = (TextView) convertView.findViewById(R.id.tvTweetBody);
            viewHolder.rlInlinePhoto = (RelativeLayout) convertView.findViewById(R.id.rlInlinePhoto);
            convertView.setTag(viewHolder);
            newConvertView = true;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            newConvertView = false;
        }

        viewHolder.tvUserName.setText(tweet.getUser().getUserName());
        viewHolder.tvTwitterHandle.setText(tweet.getUser().getTwitterHandle());
        viewHolder.tvCreationTime.setText(tweet.getRelativeCreationTime());
        viewHolder.tvTweetBody.setText(tweet.getTweetBody());
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
        if (tweet.getUser().getBannerImageUrl() != null) {
            Picasso.with(getContext()).load(tweet.getUser().getBannerImageUrl()).fetch();
        }

        if (tweet.getEntity() != null && tweet.getEntity().getMediaUrlLarge() != null) {
            ImageView ivInlinePhoto = null;
            String mediaUrl = tweet.getEntity().getMediaUrlLarge();
            viewHolder.rlInlinePhoto.setVisibility(View.VISIBLE);
            if (newConvertView) {
                viewHolder.rlInlinePhoto.removeAllViews();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inlinePhotoView = inflater.inflate(R.layout.inline_photo, null);
                ivInlinePhoto = (ImageView) inlinePhotoView.findViewById(R.id.ivInlinePhoto);
                viewHolder.rlInlinePhoto.addView(ivInlinePhoto);
            } else {
                ivInlinePhoto = (ImageView) viewHolder.rlInlinePhoto.findViewById(R.id.ivInlinePhoto);
            }
            if (ivInlinePhoto != null)
                Picasso.with(getContext()).load(mediaUrl).resize(DeviceDimensionsHelper.getDisplayWidth(getContext()), 0).into(ivInlinePhoto);
            else
                Log.e(LOG_TAG, "ivInlinePhoto ImageView is NULL");
        } else {
            viewHolder.rlInlinePhoto.removeAllViews();
            viewHolder.rlInlinePhoto.setVisibility(View.INVISIBLE);
            viewHolder.tvTweetBody.setPadding(0, 0, 10, 15);
        }

        // TODO: Set onlick listener for profile image and name to take to user profile

        // TODO: Insert the Tweet to database before returning the convertView

        return convertView;
    }
}
