package com.resterman.kongregateforums.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.Topic;
import com.resterman.kongregateforums.ui.activities.ForumActivity;
import com.resterman.kongregateforums.ui.activities.TopicActivity;
import com.resterman.kongregateforums.ui.adapters.TopicAdapter;

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

public class ForumFragment extends Fragment {

    public static final String LINK = ForumActivity.LINK;

    private ForumListener mListener;

    private String link;
    private ListView topics;
    private View progressBar;

    private TopicAdapter topicAdapter;

    public static ForumFragment newInstance(String link) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putString(LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    public ForumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            if (getArguments() != null)
                link = getArguments().getString(LINK);
        } else {
            link = savedInstanceState.getString(LINK);
        }

        new TopicsLoader().execute(link);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        topics = (ListView) view.findViewById(R.id.topics);
        topicAdapter = new TopicAdapter(
                getActivity().getApplicationContext(),
                R.layout.item_topic,
                new ArrayList<Topic>()
        );
        topics.setAdapter(topicAdapter);
        topics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = (Topic) parent.getItemAtPosition(position);
                String link = topic.getLink();

                Bundle bundle = new Bundle();
                bundle.putString(LINK, link);

                Intent intent = new Intent(getActivity().getApplicationContext(), TopicActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        progressBar = view.findViewById(R.id.progress_bar);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (ForumListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ForumListener {

        public void onTopicClick();

    }

    private class TopicsLoader extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... params) {
            Document doc = null;

            String url = params[0];
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
            Elements htmlTopics = document.getElementsByClass("hentry");

            for (int i = 0; i < htmlTopics.size(); i++) {
                Element htmlTopic = htmlTopics.get(i);
                Element htmlTitle = htmlTopic.getElementsByClass("entry-title").get(0);
                Elements htmlStats = htmlTopic.getElementsByClass("stat");
                Element htmlLastPost = htmlTopic.getElementsByClass("lp").get(0);

                Topic topic = new Topic();
                topic.setTitle(htmlTitle.text());
                topic.setLink(htmlTitle.absUrl("href"));
                topic.setPosts(htmlStats.get(0).text());
                topic.setViews(htmlStats.get(1).text());
                topic.setLastPostAuthor(htmlLastPost.getElementsByTag("abbr")
                        .get(0).text());
                topic.setLastPostTime(htmlLastPost.getElementsByTag("strong")
                        .get(0).text());
                topic.setSticky(htmlTopic.getElementsByClass("c2").get(0)
                        .getElementsByTag("strong").size() > 0);

                topicAdapter.add(topic);
            }

            progressBar.setVisibility(View.GONE);
            topics.setVisibility(View.VISIBLE);
        }

    }

}
