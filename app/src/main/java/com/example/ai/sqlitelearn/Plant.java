package com.example.ai.sqlitelearn;

public class Plant {
    private int _id;
    private int imageId;
    private String name;

    public Plant(int _id, int imageId, String name) {
        this._id = _id;
        this.imageId = imageId;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
