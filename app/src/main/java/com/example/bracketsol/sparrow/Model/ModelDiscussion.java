package com.example.bracketsol.sparrow.Model;

public class ModelDiscussion {

    int pro_image,status_img;
    String pro_name;

    public ModelDiscussion() {
    }

    public ModelDiscussion(int pro_image, int status_img, String pro_name) {
        this.pro_image = pro_image;
        this.status_img = status_img;
        this.pro_name = pro_name;
    }

    public int getPro_image() {
        return pro_image;
    }

    public void setPro_image(int pro_image) {
        this.pro_image = pro_image;
    }

    public int getStatus_img() {
        return status_img;
    }

    public void setStatus_img(int status_img) {
        this.status_img = status_img;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }
}
