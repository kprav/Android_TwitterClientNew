package com.kp.twitterclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kp.twitterclient.R;
import com.kp.twitterclient.adapters.TweetsArrayAdapter;
import com.kp.twitterclient.applications.TwitterApplication;
import com.kp.twitterclient.applications.TwitterClient;
import com.kp.twitterclient.helpers.EndlessScrollListener;
import com.kp.twitterclient.helpers.NetworkAvailabilityCheck;
import com.kp.twitterclient.models.Tweet;
import com.kp.twitterclient.models.User;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweetsList;
    private TweetsArrayAdapter tweetsArrayAdapter;
    private ListView lvTweets;

    protected SwipeRefreshLayout swipeContainer;
    protected User user;
    protected TwitterClient twitterClient;

    public TweetsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetsList = new ArrayList<Tweet>();
        tweetsArrayAdapter = new TweetsArrayAdapter(getActivity(), tweetsList);
        twitterClient = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetsArrayAdapter);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        setupSwipeRefresh();
        setupListViewListeners();
        return view;
    }

    public void scrollToTop() {
        lvTweets.smoothScrollToPosition(0);
    }

    public void add(int position, Tweet tweet) {
        tweetsList.add(position, tweet);
        tweetsArrayAdapter.notifyDataSetChanged();
    }

    protected void addAll(List<Tweet> tweets) {
        tweetsArrayAdapter.addAll(tweets);
        tweetsArrayAdapter.notifyDataSetChanged();
    }

    public void clear() {
        tweetsArrayAdapter.clear();
        if (tweetsList.size() > 0);
            // TODO: Clear database
    }

    public void setupSwipeRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Make sure to call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline(true);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void setupListViewListeners() {
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Show TweetDetail
            }
        });

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            // Triggered only when new data needs to be appended to the list
            // Load more data for paginating and append the new data items to the adapter.
            // Use the page/totalItemsCount value to retrieve paginated data.
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount <= 800) {
                    // Twitter returns about 800 tweets for the home timeline.
                    // We query 50 results with each call.

                    Log.d("totalItemsCount", Integer.toString(totalItemsCount));

                    return (populateTimeline(false));

                    // True ONLY if more data is actually being loaded; false otherwise.
                    // return true;
                }
                return false;
            }
        });
    }

    protected boolean isNetworkAvailable() {
        return NetworkAvailabilityCheck.isNetworkAvailable(getActivity());
    }

    public abstract boolean populateTimeline(boolean reset);

}