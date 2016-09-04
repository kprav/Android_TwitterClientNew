package com.kp.twitterclient.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kp.twitterclient.R;
import com.kp.twitterclient.helpers.NetworkAvailabilityCheck;
import com.kp.twitterclient.models.Tweet;
import com.kp.twitterclient.models.TweetType;
import com.kp.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineFragment extends TweetsListFragment {

    private boolean status;
    private boolean networkFlag;

    public HomeTimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        super.user = (User) bundle.getParcelable("loggedInUser");
        populateTimeline(true);
        return view;
    }

    @Override
    public boolean populateTimeline(boolean reset) {
        if (!isNetworkAvailable()) {
            NetworkAvailabilityCheck.showNetworkUnavailableToast(getActivity());
            return false;
        }
        twitterClient.getHomeTimeline(reset, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Tweet> tweets = Tweet.fromJSONArray(TweetType.HOME_TIMELINE, response);
                addAll(tweets);
                status = true;
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", errorResponse.toString());
                status = false;
            }
        });
        swipeContainer.setRefreshing(false);
        return status;
    }

}
