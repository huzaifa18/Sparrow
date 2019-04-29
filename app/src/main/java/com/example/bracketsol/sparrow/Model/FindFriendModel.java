package com.example.bracketsol.sparrow.Model;

public class FindFriendModel {

    int profileUrl,id;
    String username, mutualfriend,email,phone;

    public FindFriendModel() {
    }

    public FindFriendModel(int profileUrl, String username, String mutualfriend) {
        this.profileUrl = profileUrl;
        this.username = username;
        this.mutualfriend = mutualfriend;
    }

    public FindFriendModel(int profileUrl, int id, String username, String mutualfriend, String email, String phone) {
        this.profileUrl = profileUrl;
        this.id = id;
        this.username = username;
        this.mutualfriend = mutualfriend;
        this.email = email;
        this.phone = phone;
    }

    public int getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(int profileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMutualfriend() {
        return mutualfriend;
    }

    public void setMutualfriend(String mutualfriend) {
        this.mutualfriend = mutualfriend;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
