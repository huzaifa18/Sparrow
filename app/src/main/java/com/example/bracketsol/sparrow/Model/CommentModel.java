package com.example.bracketsol.sparrow.Model;

public class CommentModel {
    int id,user_id,post_id;
    String username,content,updated_at,created_at,pic;

    public CommentModel(int id, int user_id, int post_id, String username, String content, String updated_at, String created_at, String pic) {
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.username = username;
        this.content = content;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
