package com.example.bracketsol.sparrow.Model;

public class StoryModel {

    int stry_img_id;
    String user_name_story;

    public StoryModel(int stry_img_id, String user_name_story) {
        this.stry_img_id = stry_img_id;
        this.user_name_story = user_name_story;
    }

    public int getStry_img_id() {
        return stry_img_id;
    }

    public void setStry_img_id(int stry_img_id) {
        this.stry_img_id = stry_img_id;
    }

    public String getUser_name_story() {
        return user_name_story;
    }

    public void setUser_name_story(String user_name_story) {
        this.user_name_story = user_name_story;
    }
}
