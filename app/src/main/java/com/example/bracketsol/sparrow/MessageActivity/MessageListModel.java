package com.example.bracketsol.sparrow.MessageActivity;

public class MessageListModel {

    int profileUrl,sender_id,receiverid;
    String username,message,date,url;

    public MessageListModel(int profileUrl, int sender_id, int receiverid, String username, String message, String date, String url) {
        this.profileUrl = profileUrl;
        this.sender_id = sender_id;
        this.receiverid = receiverid;
        this.username = username;
        this.message = message;
        this.date = date;
        this.url = url;
    }

    public MessageListModel(int profileUrl, int sender_id, int receiverid, String username, String message, String date) {
        this.profileUrl = profileUrl;
        this.sender_id = sender_id;
        this.receiverid = receiverid;
        this.username = username;
        this.message = message;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(int profileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(int receiverid) {
        this.receiverid = receiverid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
