package com.resterman.kongregateforums.model;

import com.resterman.kongregateforums.R;

public class OutOfTopicPost extends Post {

    private String link;
    private String forum;
    private String topic;

    public OutOfTopicPost() {}

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

}
