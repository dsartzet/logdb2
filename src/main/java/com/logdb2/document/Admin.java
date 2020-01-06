package com.logdb2.document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "admin")
public class Admin {
    @Id
    private ObjectId _id;

    private String username;
    private String email;
    private String phoneNumber;
    private List<ObjectId> upvotes;

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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

    public List<ObjectId> getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(List<ObjectId> upvotes) {
        this.upvotes = upvotes;
    }
}
