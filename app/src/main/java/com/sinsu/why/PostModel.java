package com.sinsu.why;

public class PostModel {

    public String title;

    public String content;
    public String userName;

    public String userProfileImg;

    public int heartCount;

    public PostModel() {   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserProfileImg(String userProfileImg) {
        this.userProfileImg = userProfileImg;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public String getContent() {
        return content;
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
