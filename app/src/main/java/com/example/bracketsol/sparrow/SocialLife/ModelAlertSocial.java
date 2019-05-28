package com.example.bracketsol.sparrow.SocialLife;

public class ModelAlertSocial {
    int img_user_alert,more_alert;
    String time_alert,post_alert;

    public ModelAlertSocial(int img_user_alert, int more_alert, String time_alert, String post_alert) {
        this.img_user_alert = img_user_alert;
        this.more_alert = more_alert;
        this.time_alert = time_alert;
        this.post_alert = post_alert;
    }

    public int getImg_user_alert() {
        return img_user_alert;
    }

    public void setImg_user_alert(int img_user_alert) {
        this.img_user_alert = img_user_alert;
    }

    public int getMore_alert() {
        return more_alert;
    }

    public void setMore_alert(int more_alert) {
        this.more_alert = more_alert;
    }

    public String getTime_alert() {
        return time_alert;
    }

    public void setTime_alert(String time_alert) {
        this.time_alert = time_alert;
    }

    public String getPost_alert() {
        return post_alert;
    }

    public void setPost_alert(String post_alert) {
        this.post_alert = post_alert;
    }
}
