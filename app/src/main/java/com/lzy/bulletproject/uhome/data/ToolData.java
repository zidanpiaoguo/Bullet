package com.lzy.bulletproject.uhome.data;

/**
 * @author bullet
 * @date 2019\1\29 0029.
 */

public class ToolData {
    private String name ;
    private int image;

    public ToolData(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
