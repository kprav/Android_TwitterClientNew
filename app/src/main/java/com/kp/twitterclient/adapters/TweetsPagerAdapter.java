package com.kp.twitterclient.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kp.twitterclient.fragments.HomeTimelineFragment;
import com.kp.twitterclient.fragments.TweetsListFragment;
import com.kp.twitterclient.models.User;

// Return the order of the fragments in the view pager
public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
    private String[] tabTitles = {"Name", "Mentions"};
    private User loggedInUser;
    private TweetsListFragment fragment;

    private Bundle setupFragmentArgs() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("loggedInUser", loggedInUser);
        return bundle;
    }

    // Adapter gets the manager that is uses to insert
    // or remove fragments from the activity
    public TweetsPagerAdapter(FragmentManager fm, User loggedInUser) {
        super(fm);
        this.loggedInUser = loggedInUser;
    }

    // Controls the order and creation of fragments within the pager
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            fragment = new HomeTimelineFragment();
        } else if (position == 1) {
            fragment = new HomeTimelineFragment();
        } else {
            return null;
        }
        fragment.setArguments(setupFragmentArgs());
        // FragmentManager fragmentManager = getSupportFragmentManager();
        // fragmentManager.beginTransaction().replace(R.id.fragment_timeline_frame, fragment).commit();
        return fragment;
    }

    // Return the number of fragments to swipe between
    @Override
    public int getCount() {
        return tabTitles.length;
    }

    // Return the tab title
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
