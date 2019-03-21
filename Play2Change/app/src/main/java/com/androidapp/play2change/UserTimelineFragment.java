package com.androidapp.play2change;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class UserTimelineFragment extends Fragment {
    private Context context;
    private RecyclerView timelineRV;
    private TweetTimelineRecyclerViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
/*
    public UserTimelineFragment() {
    }

    public static UserTimelineFragment newInstance() {

        Bundle args = new Bundle();

        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // calls on the timeline fragment to use xml items
        return inflater.inflate(R.layout.timeline, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // when created, calls the recycler view (list) and loads the timeline
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
        loadUserTimeline();
    }

    // initializes the recycler
    private void setUpRecyclerView(@NonNull View view) {
        timelineRV = view.findViewById(R.id.user_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);//it should be Vertical only
        timelineRV.setLayoutManager(linearLayoutManager);
    }

    // loading the timeline
    private void loadUserTimeline() {
        //build timeline
        UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("Play2ChangeWMT")//screen name of the user to show tweets for
                .includeReplies(true)//Whether to include replies. Defaults to false.
                .includeRetweets(true)//Whether to include re-tweets. Defaults to true.
                .maxItemsPerRequest(50)//Max number of items to return per request
                .build();

        //now build adapter for recycler view
        adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                .setTimeline(userTimeline)//set the created timeline
                //set tweet view style
                .setViewStyle(R.style.tw__TweetLightStyle)
                .build();

        //set the created adapter to recycler view
        timelineRV.setAdapter(adapter);
    }
}
