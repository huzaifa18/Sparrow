package com.example.bracketsol.sparrow.SocialLife;

public class ModelSocial {

    int announcement_id,attachment_id,sender_id,is_active,likes,comments,total_views;
    String statement,url,start_date,end_date,created_at,sender_name,profile_pic,type;

    public ModelSocial(int announcement_id, int attachment_id, int sender_id, int is_active, String type, String statement, String url, String start_date, String end_date, String created_at, String sender_name, String profile_pic,int likes,int comments,int total_views) {
        this.announcement_id = announcement_id;
        this.attachment_id = attachment_id;
        this.sender_id = sender_id;
        this.is_active = is_active;
        this.type = type;
        this.statement = statement;
        this.url = url;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
        this.sender_name = sender_name;
        this.profile_pic = profile_pic;
        this.likes = likes;
        this.comments = comments;
        this.total_views = total_views;
    }

    public int getTotal_views() {
        return total_views;
    }

    public void setTotal_views(int total_views) {
        this.total_views = total_views;
    }

    public ModelSocial(int announcement_id, int attachment_id, String end_date) {
        this.announcement_id = announcement_id;
        this.attachment_id = attachment_id;
        this.end_date = end_date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public int getAnnouncement_id() {
        return announcement_id;
    }

    public void setAnnouncement_id(int announcement_id) {
        this.announcement_id = announcement_id;
    }

    public int getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(int attachment_id) {
        this.attachment_id = attachment_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
