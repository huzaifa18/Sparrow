package com.example.bracketsol.sparrow.SocialLife;

public class ModelSocialLife {

    public int image_path;
    public  int image_post;
    public String contant;

    public ModelSocialLife(int announcement_id, int attachment_id, int sender_id, int is_active, int type, String statement, String url, String start_date, String end_date, String created_at, int image_path, int image_post, String contant) {

        this.image_path = image_path;
        this.image_post = image_post;
        this.contant = contant;
    }

    public ModelSocialLife(int announcement_id, int attachment_id, String end_date, int image_path, int image_post, String contant) {

        this.image_path = image_path;
        this.image_post = image_post;
        this.contant = contant;
    }

    public int getImage_path() {
        return image_path;
    }

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }


    public int getImage_post() {
        return image_post;
    }

    public void setImage_post(int image_post) {
        this.image_post = image_post;
    }
}
