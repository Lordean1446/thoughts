package com.lordean1446.thoughts.dto;

import java.io.Serializable;
import java.util.Date;

public class ReactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reaction;
    private Date date;
    private AuthorDTO author;

    public ReactionDTO() {
    }

    public ReactionDTO(String reaction, Date date, AuthorDTO author) {
        this.reaction = reaction;
        this.date = date;
        this.author = author;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
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
}
