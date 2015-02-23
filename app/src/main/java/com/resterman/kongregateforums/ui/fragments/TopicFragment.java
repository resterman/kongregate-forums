package com.resterman.kongregateforums.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.Post;
import com.resterman.kongregateforums.ui.adapters.PostAdapter;

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

public class TopicFragment extends Fragment {

    private static final String TOPIC_LINK = "topic_link";

    private String topicLink;
    private PostAdapter postsAdapter;

    private ListView posts;
    private View progressBar;

    public static TopicFragment newInstance(String topicLink) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(TOPIC_LINK, topicLink);
        fragment.setArguments(args);
        return fragment;
    }

    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topicLink = getArguments().getString(TOPIC_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);

        postsAdapter = new PostAdapter(
                getActivity().getApplicationContext(),
                R.layout.item_post,
                new ArrayList<Post>()
        );
        posts = (ListView) view.findViewById(R.id.posts);
        posts.setAdapter(postsAdapter);
        posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: do something
            }
        });

        progressBar = (View) view.findViewById(R.id.progress_bar);

        // onPostExecute is using progressBar and posts references
        // AsyncTask execution must be here or after
        new PostsLoader().execute(topicLink);

        return view;
    }

    private class PostsLoader extends AsyncTask<String, Void, Document> {

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
            Element htmlContent = document.getElementById("forum_posts");
            if (htmlContent == null) {
                // TODO: handle error
                return;
            }

            Elements htmlPosts = htmlContent.getElementsByClass("post");

            for (int i = 0; i < htmlPosts.size(); i++) {
                Element htmlAuthor = htmlPosts.get(i).getElementsByClass("author").get(0);

                Element htmlUsername = htmlAuthor.getElementsByClass("name").get(0)
                        .getElementsByTag("a").get(0);
                Element htmlTime = htmlAuthor.getElementsByClass("updated").get(0);
                Element htmlAvatar = htmlAuthor.getElementsByClass("user_avatar").get(0);

                Element htmlPost = htmlPosts.get(i).getElementsByClass("entry-content").get(0);
                Elements htmlPostContent = htmlPost.children().not(".topic");

                Post post = new Post();
                post.setUsername(htmlUsername.text());
                post.setTime(htmlTime.text());
                post.setContent(htmlPostContent.outerHtml());
                post.setAvatarUrl(htmlAvatar.attr("src").replace("40x40", "140x140"));

                postsAdapter.add(post);
            }

            progressBar.setVisibility(View.GONE);
            posts.setVisibility(View.VISIBLE);
        }

    }

}
