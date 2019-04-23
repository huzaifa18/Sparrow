package com.example.bracketsol.sparrow.Model;

public class TryDiscussionModel {

    int imgid;
    int imgbtn;

    public TryDiscussionModel(int imgid, int imgbtn) {
        this.imgid = imgid;
        this.imgbtn = imgbtn;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getImgbtn() {
        return imgbtn;
    }

    public void setImgbtn(int imgbtn) {
        this.imgbtn = imgbtn;
    }
}
