package com.sinsu.why;

import java.util.Comparator;

public class PostModelComparator implements Comparator<PostModel> {

    int ret = 0;

    @Override
    public int compare(PostModel o1, PostModel o2) {
        if (o1.getHeartCount() < o2.getHeartCount()) {
            ret = -1;
        }
        if (o1.getHeartCount() == o2.getHeartCount()){
            if (o1.getUploadTime().compareTo(o2.getUploadTime()) < 0){
                if (o1.getUploadTime().compareTo(o2.getUploadTime()) < 0){
                    ret = 1;
                } else if (o1.getUploadTime().compareTo(o2.getUploadTime()) == 0){
                    ret = 0;
                } else if (o1.getUploadTime().compareTo(o2.getUploadTime()) > 0){
                    ret = -1;
                }
            }
            if (o1.getHeartCount() > o2.getHeartCount()){
                ret = 1;
            }
        }
        return ret;
    }
}
