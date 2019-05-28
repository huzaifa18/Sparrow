package com.example.bracketsol.sparrow.Utils;

import com.google.gson.annotations.SerializedName;

public class UserData {

    public UserData(int id, String username, String email, String phone_no) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone_no = phone_no;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_no")
    private String phone_no;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
