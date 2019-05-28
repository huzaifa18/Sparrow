package com.example.bracketsol.sparrow;



public class SingleHorizontalTwo {

    private int status_image,pro_img,like_imgbtn,coment_imgbtn;
    private String name,desc;

    public SingleHorizontalTwo() {

    }

    public SingleHorizontalTwo(int status_image, int pro_img, int like_imgbtn, int coment_imgbtn, String name, String desc) {
        this.status_image = status_image;
        this.pro_img = pro_img;
        this.like_imgbtn = like_imgbtn;
        this.coment_imgbtn = coment_imgbtn;
        this.name = name;
        this.desc = desc;
    }

    public int getStatus_image() {
        return status_image;
    }

    public void setStatus_image(int status_image) {
        this.status_image = status_image;
    }

    public int getPro_img() {
        return pro_img;
    }

    public void setPro_img(int pro_img) {
        this.pro_img = pro_img;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
