package com.example.bracketsol.sparrow.Model;

public class NotificationModel {

    int img_user_noti,more_noti;
    String time_noti,post_noti;

    public NotificationModel(int img_user_noti, int more_noti, String time_noti, String post_noti) {
        this.img_user_noti = img_user_noti;
        this.more_noti = more_noti;
        this.time_noti = time_noti;
        this.post_noti = post_noti;
    }

    public int getImg_user_noti() {
        return img_user_noti;
    }

    public void setImg_user_noti(int img_user_noti) {
        this.img_user_noti = img_user_noti;
    }

    public int getMore_noti() {
        return more_noti;
    }

    public void setMore_noti(int more_noti) {
        this.more_noti = more_noti;
    }

    public String getTime_noti() {
        return time_noti;
    }

    public void setTime_noti(String time_noti) {
        this.time_noti = time_noti;
    }

    public String getPost_noti() {
        return post_noti;
    }

    public void setPost_noti(String post_noti) {
        this.post_noti = post_noti;
    }
}
