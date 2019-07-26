package com.example.bracketsol.sparrow.Model;

public class NotificationModel {

    int _id;
    int user_id;
    String username;
    String picture_url;
    String content;
    String is_read;
    int reference_id;
    int type;
    String created_at;

    public NotificationModel(int _id, int user_id, String username, String picture_url, String content, String is_read, int reference_id, int type, String created_at) {
        this._id = _id;
        this.user_id = user_id;
        this.username = username;
        this.picture_url = picture_url;
        this.content = content;
        this.is_read = is_read;
        this.reference_id = reference_id;
        this.type = type;
        this.created_at = created_at;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public int getReference_id() {
        return reference_id;
    }

    public void setReference_id(int reference_id) {
        this.reference_id = reference_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
