package com.androidapp.play2change;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enables toolbar (slide in)
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // enables twitter
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                //.twitterAuthConfig(new TwitterAuthConfig())//pass Twitter API Key and Secret
                .twitterAuthConfig(new TwitterAuthConfig("3nVuSoBZnx6U4vzUxf5w", "Bcs59EFbbsdF6Sl9Ng71smgStWEGwXXKSjYvPVt7qys"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        // creates instance of navbar
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Remove tint on icons
        navigationView.setItemIconTintList(null);
        // for selecting items
        navigationView.setNavigationItemSelectedListener(this);

        // activaties the drawer with a toggle for open and close
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //shows the home screen by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserTimelineFragment()).commit();
            //toolbar.setTitle("Home");
            navigationView.setCheckedItem(R.id.nav_home);
        }
        UserTimelineFragment userTimelineFragment;
        userTimelineFragment = (UserTimelineFragment) getSupportFragmentManager().findFragmentByTag("timeline");

        if (userTimelineFragment != null && userTimelineFragment.isVisible()) {
            toolbar.setTitle("Home");
        }

    }

    // a toggle for opening different fragments based on what button is clicked in navbar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Toolbar title = findViewById(R.id.toolbar);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                // creates a new class that will call on the fragment to appear
                //title.setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserTimelineFragment()).commit();
                break;
            case R.id.nav_events:
                title.setTitle("Events");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                break;
            case R.id.nav_team:
                title.setTitle("About Us");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TeamFragment()).commit();
                break;
        }
        // hides drawer
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // what to do when back button is pressed
    @Override
    public void onBackPressed() {
        // if drawer is open, close it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        // else close the activity
        else {
            super.onBackPressed();
        }
    }
}
