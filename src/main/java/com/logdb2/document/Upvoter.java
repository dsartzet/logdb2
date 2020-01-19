package com.logdb2.document;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

@CompoundIndexes({
        @CompoundIndex(def = "{'username' : 1, 'email': 1}")
})
public class Upvoter {
    private String username;
    private String email;

    public Upvoter(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
