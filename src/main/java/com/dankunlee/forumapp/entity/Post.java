package com.dankunlee.forumapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Post extends Auditing{
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private long id;

    @NotNull // for validating the input
    @Column(nullable = false) // for setting the key NOT NULL
    private String title;

    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Post post = (Post) obj;
        return Objects.equals(post.id, id) &&
                Objects.equals(post.title, title) &&
                Objects.equals(post.content, content);
    }
}
