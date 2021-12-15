package com.sinsu.why;

import java.util.Date;

public class PostModel implements Comparable<PostModel>{

    public String title;

    public String content;
    public String userName;

    public String userProfileImg;

    public int heartCount;

    public Date uploadTime;

    public PostModel() {   }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

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


    @Override
    public int compareTo(PostModel o) {
        if (o.heartCount < heartCount)
            return -1;
        else if (o.heartCount > heartCount)
            return 1;
        return 0;
    }
}
