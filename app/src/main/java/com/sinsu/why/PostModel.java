package com.sinsu.why;

public class PostModel {

    public String userId;
    public String title;
    public String contents;
    public String userName;

    public String userProfileImg;

    public int heartCount;

    public PostModel() {   }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserProfileImg() {
        return userProfileImg;
    }

    public int getHeartCount() {
        return heartCount;
    }
}
