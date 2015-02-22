package com.resterman.kongregateforums.ui.fragments;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.OutOfTopicPost;
import com.resterman.kongregateforums.ui.activities.HomeActivity;
import com.resterman.kongregateforums.ui.adapters.OutOfTopicPostAdapter;
import com.resterman.kongregateforums.util.OutOfTopicPostsLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class MonitoredPostsFragment extends Fragment {

    OutOfTopicPostAdapter postAdapter;

    View progressBar;
    ListView postList;

    public static MonitoredPostsFragment newInstance() {
        return new MonitoredPostsFragment();
    }

    public MonitoredPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAdapter = new OutOfTopicPostAdapter(
                getActivity(),
                R.layout.item_out_of_topic_post,
                new ArrayList<OutOfTopicPost>()
        );

        new PostsLoader().execute("http://www.kongregate.com/accounts/resterman/monitored");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        postList = (ListView) view.findViewById(R.id.posts);
        postList.setAdapter(postAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((HomeActivity) activity)
                .onSectionAttached(R.id.monitored_posts);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class PostsLoader extends OutOfTopicPostsLoader {

        public PostsLoader() {
            super(postAdapter);
        }

        protected void onPostExecute(Document document) {
            super.onPostExecute(document);

            progressBar.setVisibility(View.GONE);
            postList.setVisibility(View.VISIBLE);
        }

    }

}
