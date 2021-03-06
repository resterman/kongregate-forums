package com.resterman.kongregateforums.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.ui.fragments.TopicFragment;

public class TopicActivity extends ActionBarActivity {

    private static final String LINK = "link";

    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Fragment fragment;
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            link = bundle.getString(LINK);
        } else {
            link = savedInstanceState.getString(LINK);
        }

        fragment = TopicFragment.newInstance(link);

        getFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic, menu);
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

}
