package com.resterman.kongregateforums.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.ui.fragments.ForumGroupFragment;
import com.resterman.kongregateforums.ui.fragments.MonitoredPostsFragment;
import com.resterman.kongregateforums.ui.fragments.MyPostsFragment;
import com.resterman.kongregateforums.ui.fragments.NavigationDrawerFragment;

public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int id) {
        // update the main content by replacing fragments
        Fragment fragment;
        switch (id) {
            case R.id.user_posts:
                fragment = MyPostsFragment.newInstance();
                break;
            case R.id.monitored_posts:
                fragment = MonitoredPostsFragment.newInstance();
                break;
            case R.id.about_kongregate_forum:
                fragment = ForumGroupFragment.newInstance(ForumGroupFragment.ABOUT_KONGREGATE);
                break;
            case R.id.games_forum:
                fragment = ForumGroupFragment.newInstance(ForumGroupFragment.GAMES);
                break;
            case R.id.game_creation_forum:
                fragment = ForumGroupFragment.newInstance(ForumGroupFragment.GAME_CREATION);
                break;
            case R.id.non_gaming_forum:
                fragment = ForumGroupFragment.newInstance(ForumGroupFragment.NON_GAMING);
                break;
            default:
                // TODO: change this
                fragment = MyPostsFragment.newInstance();
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int position) {
        switch (position) {
            case R.id.user_posts:
                mTitle = getString(R.string.my_posts_title);
                break;
            case R.id.monitored_posts:
                mTitle = getString(R.string.monitored_posts_title);
                break;
            case R.id.about_kongregate_forum:
                mTitle = getString(R.string.about_kongregate_title);
                break;
            case R.id.games_forum:
                mTitle = getString(R.string.games_title);
                break;
            case R.id.game_creation_forum:
                mTitle = getString(R.string.game_creation_title);
                break;
            case R.id.non_gaming_forum:
                mTitle = getString(R.string.non_gaming_title);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else super.onBackPressed();
    }
}
