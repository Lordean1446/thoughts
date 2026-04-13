package com.lordean1446.thoughts.domain;

import com.lordean1446.thoughts.dto.AuthorDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Date date;
    private AuthorDTO author;
    private String body;

    public Post() {
    }

    public Post(String id, Date date, AuthorDTO author, String body) {
        this.id = id;
        this.date = date;
        this.author = author;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}


