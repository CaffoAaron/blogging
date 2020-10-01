package com.acme.blogging.resource;

import com.acme.blogging.domain.model.AuditModel;

public class PostResource extends AuditModel {
    private Long id;
    private String title;
    private String description;
    private String content;

    public Long getId() {
        return id;
    }

    public PostResource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostResource setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PostResource setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostResource setContent(String content) {
        this.content = content;
        return this;
    }
}
