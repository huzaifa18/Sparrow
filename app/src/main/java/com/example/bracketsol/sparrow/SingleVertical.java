package com.example.bracketsol.sparrow;



public class SingleVertical {

    private String header;
    private int image,pro_image;

    public SingleVertical( ) {

    }

    public SingleVertical(String header, int image, int pro_image) {
        this.header = header;
        this.image = image;
        this.pro_image = pro_image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPro_image() {
        return pro_image;
    }

    public void setPro_image(int pro_image) {
        this.pro_image = pro_image;
    }
}
