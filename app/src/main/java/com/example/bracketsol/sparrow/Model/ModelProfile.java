package com.example.bracketsol.sparrow.Model;

public class ModelProfile {

    private int image_pathl;
    private String name;

    public ModelProfile(int image_pathl, String name) {
        this.image_pathl = image_pathl;
        this.name = name;
    }

    public int getImage_pathl() {
        return image_pathl;
    }

    public void setImage_pathl(int image_pathl) {
        this.image_pathl = image_pathl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
