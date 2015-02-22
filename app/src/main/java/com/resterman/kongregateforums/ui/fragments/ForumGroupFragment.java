package com.resterman.kongregateforums.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.Forum;
import com.resterman.kongregateforums.ui.activities.ForumActivity;
import com.resterman.kongregateforums.ui.activities.HomeActivity;
import com.resterman.kongregateforums.ui.adapters.ForumAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ForumGroupFragment extends Fragment {

    private static final String FORUM_GROUP = "forumGroup";

    public static final String ABOUT_KONGREGATE = "about-kongregate";
    public static final String GAMES = "games";
    public static final String GAME_CREATION = "game-creation";
    public static final String NON_GAMING = "non-gaming";

    private static final String MAIN_FORUMS = "http://www.kongregate.com/forums";
    private static final String GAME_FORUMS = "http://www.kongregate.com/forums/games";

    private String group;

    private ForumAdapter forumsAdapter;

    private ListView forums;
    private ProgressBar progressBar;

    public static ForumGroupFragment newInstance(String group) {
        ForumGroupFragment fragment = new ForumGroupFragment();
        Bundle args = new Bundle();
        args.putString(FORUM_GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }

    public ForumGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            group = getArguments().getString(FORUM_GROUP);
        }

        HomeActivity activity = (HomeActivity) getActivity();
        int id = 0;
        switch (group) {
            case ABOUT_KONGREGATE:
                id = R.id.about_kongregate_forum;
                break;
            case GAMES:
                id = R.id.games_forum;
                break;
            case GAME_CREATION:
                id = R.id.game_creation_forum;
                break;
            case NON_GAMING:
                id = R.id.non_gaming_forum;
                break;
            default:
                // TODO: handle case
        }

        if (activity != null)
            activity.onSectionAttached(id);

        new ForumsLoader().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum_group, container, false);

        forums = (ListView) view.findViewById(R.id.forums);
        forumsAdapter = new ForumAdapter(
                getActivity().getApplicationContext(),
                R.layout.item_forum,
                new ArrayList<Forum>()
        );
        forums.setAdapter(forumsAdapter);
        forums.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Forum forum = (Forum) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(ForumFragment.LINK, forum.getLink());

                Intent i = new Intent(getActivity().getApplicationContext(), ForumActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            }

        });

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private class ForumsLoader extends AsyncTask<Void, Void, Document> {

        @Override
        protected Document doInBackground(Void... params) {
            Document doc = null;

            String url = !group.equals(GAMES) ? MAIN_FORUMS : GAME_FORUMS;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(new HttpGet(url));
                doc = Jsoup.parse(response.getEntity().getContent(), null, url);
            } catch (IOException ex) {
                // TODO: handle exception
            }

            return doc;
        }

        @Override
        protected void onPostExecute(Document document) {
            Element tableBody = document.getElementById(group)
                    .nextElementSibling().getElementsByTag("tbody").get(0);
            Elements forums = tableBody.getElementsByTag("tr");
            for (int i = 0; i < forums.size(); i++) {
                Element htmlRow = forums.get(i);
                Element htmlTitle = htmlRow.getElementsByClass("title").get(0);
                Element htmlDescription = htmlRow.getElementsByClass("desc").get(0)
                        .nextElementSibling();

                Forum forum = new Forum();
                forum.setName(htmlTitle.text());
                forum.setLink(htmlTitle.absUrl("href"));
                forum.setDescription(htmlDescription.text());
                forumsAdapter.add(forum);
            }

            progressBar.setVisibility(View.GONE);
            ForumGroupFragment.this.forums.setVisibility(View.VISIBLE);
        }
    }

}
