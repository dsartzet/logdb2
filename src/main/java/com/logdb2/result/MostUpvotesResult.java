package com.logdb2.result;

import com.logdb2.document.Log;
import org.bson.types.ObjectId;

import java.util.List;

public class MostUpvotesResult {

    private String _id;
    private String username;
    private String email;
    private String phoneNumber;
    private List<String> upvotes;
    private Long size;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUpvotes(List<String> upvotes) {
        this.upvotes = upvotes;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<String> getUpvotes() {
        return upvotes;
    }
}
