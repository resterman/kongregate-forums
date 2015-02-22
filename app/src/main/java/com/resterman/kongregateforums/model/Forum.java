package com.resterman.kongregateforums.model;

import android.util.Log;

public class Forum {

    private String name;
    private String description;
    private String lastPoster;
    private String lastPostTime;
    private String link;
    private int topics;
    private int posts;

    public Forum() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastPoster() {
        return lastPoster;
    }

    public void setLastPoster(String lastPoster) {
        this.lastPoster = lastPoster;
    }

    public String getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(String lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }
}
