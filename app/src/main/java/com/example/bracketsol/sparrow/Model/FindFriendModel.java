package com.example.bracketsol.sparrow.Model;

/**
 * Created by bracketsol on 4/16/2019.
 */

public class FindFriendModel {


    int imgid,imgplus,imgminus;
    String name,subname;

    public FindFriendModel(int imgid, int imgplus, int imgminus, String name, String subname) {
        this.imgid = imgid;
        this.imgplus = imgplus;
        this.imgminus = imgminus;
        this.name = name;
        this.subname = subname;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getImgplus() {
        return imgplus;
    }

    public void setImgplus(int imgplus) {
        this.imgplus = imgplus;
    }

    public int getImgminus() {
        return imgminus;
    }

    public void setImgminus(int imgminus) {
        this.imgminus = imgminus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }
}
