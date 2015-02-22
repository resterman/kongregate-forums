package com.resterman.kongregateforums.model;

public class Topic {

    private String title;
    private String posts;
    private String views;
    private String lastPostTime;
    private String lastPostAuthor;
    private boolean isSticky;

    private String link;
    private String lastPostLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(String lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    public String getLastPostAuthor() {
        return lastPostAuthor;
    }

    public void setLastPostAuthor(String lastPostAuthor) {
        this.lastPostAuthor = lastPostAuthor;
    }

    public boolean isSticky() {
        return isSticky;
    }

    public void setSticky(boolean isSticky) {
        this.isSticky = isSticky;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastPostLink() {
        return lastPostLink;
    }

    public void setLastPostLink(String lastPostLink) {
        this.lastPostLink = lastPostLink;
    }
}
