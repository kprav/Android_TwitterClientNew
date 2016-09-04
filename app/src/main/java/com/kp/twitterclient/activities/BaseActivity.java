package com.kp.twitterclient.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.activeandroid.ActiveAndroid;
import com.astuetz.PagerSlidingTabStrip;
import com.kp.twitterclient.R;
import com.kp.twitterclient.adapters.TweetsPagerAdapter;
import com.kp.twitterclient.applications.TwitterApplication;
import com.kp.twitterclient.applications.TwitterClient;
import com.kp.twitterclient.fragments.TweetsListFragment;
import com.kp.twitterclient.helpers.NetworkAvailabilityCheck;
import com.kp.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BaseActivity extends AppCompatActivity {

    private TwitterClient twitterClient;
    private User loggedInUser;
    private ImageView scrollToTop;
    private ViewPager vpPager;
    private TweetsPagerAdapter viewPagerAdapter;
    private PagerSlidingTabStrip tabsStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActiveAndroid.setLoggingEnabled(true);
        twitterClient = TwitterApplication.getRestClient();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            setupScrollToTopButton(getSupportActionBar());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        getLoggedInUserInfo();
    }

    private void setupScrollToTopButton(ActionBar actionBar) {
        scrollToTop = new ImageView(this);
        scrollToTop.setClickable(true);
        scrollToTop.setEnabled(true);
        scrollToTop.setBackgroundResource(R.drawable.ic_twitter_white);
        RelativeLayout relative = new RelativeLayout(this);
        relative.addView(scrollToTop);
        actionBar.setCustomView(relative);
    }

    private void getLoggedInUserInfo() {
        if (isNetworkAvailable()) {
            twitterClient.getLoggedInUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("USER", response.toString());
                    loggedInUser = User.fromJSON(response);
                    loadFragments();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("ERROR", errorResponse.toString());
                }
            });
        } else {
            loadFragments();
        }
    }

    private void loadFragments() {
        // Get the viewpager and setup a PageChangeListener
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // Get the view pager adapter for the pager
        viewPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager(), loggedInUser);
        vpPager.setAdapter(viewPagerAdapter);
        // Find the sliding tabstrips
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the view pager
        tabsStrip.setViewPager(vpPager);
        setupPageChangeListener();
    }

    private void setupPageChangeListener() {
        tabsStrip.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                scrollToTop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TweetsListFragment) viewPagerAdapter.getRegisteredFragment(position)).scrollToTop();
                    }
                });
            }
        });
    }

    private boolean isNetworkAvailable() {
        return NetworkAvailabilityCheck.isNetworkAvailable(this);
    }

}
