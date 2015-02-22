package com.resterman.kongregateforums.util;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.OutOfTopicPost;
import com.resterman.kongregateforums.ui.adapters.OutOfTopicPostAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OutOfTopicPostsLoader extends AsyncTask<String, Void, Document> {

    OutOfTopicPostAdapter adapter;

    public OutOfTopicPostsLoader(OutOfTopicPostAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Document doInBackground(String... urls) {
        Document doc = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(urls[0]));
            doc = Jsoup.parse(httpResponse.getEntity().getContent(), null, urls[0]);
        } catch (Exception ex) {
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
            Element htmlTopic = htmlPost.getElementsByClass("topic").get(0);

            OutOfTopicPost post = new OutOfTopicPost();
            post.setUsername(htmlUsername.text());
            post.setTime(htmlTime.text());
            post.setContent(htmlPostContent.outerHtml());
            post.setAvatarUrl(htmlAvatar.attr("src").replace("40x40", "140x140"));
            post.setLink(htmlTopic.getElementsByTag("a").get(1).attr("href"));
            post.setForum(htmlTopic.getElementsByTag("a").get(0).text());
            post.setTopic(htmlTopic.getElementsByTag("a").get(1).text());

            adapter.add(post);
        }
    }
}
