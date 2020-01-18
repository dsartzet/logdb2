package com.logdb2.result;

import java.util.List;

public class SameEmailDifferentUsernamesUpvotedLogsResult {
    private String _id;
    private String email;
    private List<String> usernames;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
