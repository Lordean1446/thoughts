package com.lordean1446.thoughts.domain;

import com.lordean1446.thoughts.dto.AuthorDTO;
import com.lordean1446.thoughts.dto.ReactionDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Date date;
    private String body;
    private AuthorDTO author;

    private List<ReactionDTO> reactions = new ArrayList<>();

    public Post() {
    }

    public Post(String id, Date date, String body, AuthorDTO author) {
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

    public List<ReactionDTO> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionDTO> reactions) {
        this.reactions = reactions;
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


