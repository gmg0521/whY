package com.sinsu.why;

import java.util.Date;

public class CommentModel {
    String comment;
    String userName;
    String userProfileImg;
    String commentID;
    Date uploadTime;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImg() {
        return userProfileImg;
    }

    public void setUserProfileImg(String userProfileImg) {
        this.userProfileImg = userProfileImg;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getCommentID() {
        return commentID;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
