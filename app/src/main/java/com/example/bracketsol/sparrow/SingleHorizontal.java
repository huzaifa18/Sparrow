package com.example.bracketsol.sparrow;



public class SingleHorizontal {

    private int status_image,pro_img;
    private String name;

    public SingleHorizontal() {

    }

    public SingleHorizontal(int status_image, int pro_img, String name) {
        this.status_image = status_image;
        this.pro_img = pro_img;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
