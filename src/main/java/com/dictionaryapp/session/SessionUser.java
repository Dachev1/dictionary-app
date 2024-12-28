package com.dictionaryapp.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionUser {

    private long id = 0;
    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        if (!isLoggedIn()) {
            this.username = null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (isLoggedIn()) {
            this.username = username;
        }
    }

    public boolean isLoggedIn() {
        return id != 0;
    }

    public void logout() {
        this.id = 0;
        this.username = null;
    }
}
