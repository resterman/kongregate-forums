package com.resterman.kongregateforums.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.ui.fragments.ForumFragment;

public class ForumActivity extends ActionBarActivity
    implements ForumFragment.ForumListener {

    public static final String LINK = "link";

    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        ForumFragment fragment;
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            link = bundle.getString(LINK);
        } else {
            link = savedInstanceState.getString(LINK);
        }

        fragment = ForumFragment.newInstance(link);

        getFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(LINK, link);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum, menu);
        return true;
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
    public void onTopicClick() {
        // TODO: implement method
    }

}
