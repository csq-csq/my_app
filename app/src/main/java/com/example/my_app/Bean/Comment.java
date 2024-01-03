package com.example.my_app.Bean;

import java.io.Serializable;

public class Comment implements Serializable {
    private String username;
    private String comment;

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}
