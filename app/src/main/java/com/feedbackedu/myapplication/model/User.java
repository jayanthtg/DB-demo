package com.feedbackedu.myapplication.model;

public class User {
    private String id, picture, passcode;

    public User(String id, String picture, String passcode) {
        this.id = id;
        this.picture = picture;
        this.passcode = passcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
