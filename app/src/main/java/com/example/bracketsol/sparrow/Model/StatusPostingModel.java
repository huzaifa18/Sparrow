package com.example.bracketsol.sparrow.Model;

public class StatusPostingModel {

    int status_img,user_img,more_imgbtn,like_imgbtn,coment_imgbtn,send_imgbtn,fav_imgbtn;
    String name_txt,loc;

    public StatusPostingModel(int status_img, int user_img, int more_imgbtn, int like_imgbtn, int coment_imgbtn, int send_imgbtn, int fav_imgbtn, String name_txt, String loc) {
        this.status_img = status_img;
        this.user_img = user_img;
        this.more_imgbtn = more_imgbtn;
        this.like_imgbtn = like_imgbtn;
        this.coment_imgbtn = coment_imgbtn;
        this.send_imgbtn = send_imgbtn;
        this.fav_imgbtn = fav_imgbtn;
        this.name_txt = name_txt;
        this.loc = loc;
    }

    public int getStatus_img() {
        return status_img;
    }

    public void setStatus_img(int status_img) {
        this.status_img = status_img;
    }

    public int getUser_img() {
        return user_img;
    }

    public void setUser_img(int user_img) {
        this.user_img = user_img;
    }

    public int getMore_imgbtn() {
        return more_imgbtn;
    }

    public void setMore_imgbtn(int more_imgbtn) {
        this.more_imgbtn = more_imgbtn;
    }

    public int getLike_imgbtn() {
        return like_imgbtn;
    }

    public void setLike_imgbtn(int like_imgbtn) {
        this.like_imgbtn = like_imgbtn;
    }

    public int getComent_imgbtn() {
        return coment_imgbtn;
    }

    public void setComent_imgbtn(int coment_imgbtn) {
        this.coment_imgbtn = coment_imgbtn;
    }

    public int getSend_imgbtn() {
        return send_imgbtn;
    }

    public void setSend_imgbtn(int send_imgbtn) {
        this.send_imgbtn = send_imgbtn;
    }

    public int getFav_imgbtn() {
        return fav_imgbtn;
    }

    public void setFav_imgbtn(int fav_imgbtn) {
        this.fav_imgbtn = fav_imgbtn;
    }

    public String getName_txt() {
        return name_txt;
    }

    public void setName_txt(String name_txt) {
        this.name_txt = name_txt;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
