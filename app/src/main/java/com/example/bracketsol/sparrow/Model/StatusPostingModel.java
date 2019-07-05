package com.example.bracketsol.sparrow.Model;

public class StatusPostingModel {

    String sender_name,sender_id,sender_pic,content,attachment,attachment_type;
    int total_likes,total_comments,total_views,post_id;

    public StatusPostingModel(String sender_name,String sender_id, String sender_pic, String content, String attachment, int total_likes, int total_comments, int total_views, int post_id,String attachment_type) {
        this.sender_name = sender_name;
        this.sender_id = sender_id;
        this.sender_pic = sender_pic;
        this.content = content;
        this.attachment = attachment;
        this.total_likes = total_likes;
        this.total_comments = total_comments;
        this.total_views = total_views;
        this.post_id = post_id;
        this.attachment_type = attachment_type;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_pic() {
        return sender_pic;
    }

    public void setSender_pic(String sender_pic) {
        this.sender_pic = sender_pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public int getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }

    public int getTotal_views() {
        return total_views;
    }

    public void setTotal_views(int total_views) {
        this.total_views = total_views;
    }

    public String getAttachment_type() {
        return attachment_type;
    }

    public void setAttachment_type(String attachment_type) {
        this.attachment_type = attachment_type;
    }
}
