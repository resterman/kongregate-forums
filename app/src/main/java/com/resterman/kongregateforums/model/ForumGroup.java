package com.resterman.kongregateforums.model;

import java.util.ArrayList;
import java.util.List;

public class ForumGroup {

    private String name;
    private List<Forum> forums = new ArrayList<>();

    public ForumGroup() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }
}
