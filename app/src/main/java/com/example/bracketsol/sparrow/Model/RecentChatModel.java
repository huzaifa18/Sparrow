package com.example.bracketsol.sparrow.Model;

public class RecentChatModel {

    int image_path;
    String name;
    String last_text;
    String date;
    int imgid,imgplus,imgminus;
    String subname;

    public RecentChatModel(int imgid, int imgplus, int imgminus, String name, String subname) {
        this.imgid = imgid;
        this.imgplus = imgplus;
        this.imgminus = imgminus;
        this.name = name;
        this.subname = subname;
    }

    public RecentChatModel(int image_path, String name, String last_text, String date) {
        this.image_path = image_path;
        this.name = name;
        this.last_text = last_text;
        this.date = date;
    }

    public int getImage_path() {
        return image_path;
    }

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_text() {
        return last_text;
    }

    public void setLast_text(String last_text) {
        this.last_text = last_text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getImgplus() {
        return imgplus;
    }

    public void setImgplus(int imgplus) {
        this.imgplus = imgplus;
    }

    public int getImgminus() {
        return imgminus;
    }

    public void setImgminus(int imgminus) {
        this.imgminus = imgminus;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }
}
