package com.acme.blogging.domain.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Lob
    private String content;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post_tags",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;


    public Post(@NotNull String title, @NotNull String description, @NotNull String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Post() {

    }


    public boolean isTaggedWith(Tag tag) {
        return this.getTags().contains(tag);
    }

    public Post tagWith(Tag tag) {
        if (!this.isTaggedWith(tag))
            this.getTags().add(tag);
        return this;
    }

    public Post unTagWith(Tag tag) {
        if (this.isTaggedWith(tag))
            this.getTags().remove(tag);
        return this;
    }

    public Long getId() {
        return id;
    }

    public Post setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Post setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }
}
