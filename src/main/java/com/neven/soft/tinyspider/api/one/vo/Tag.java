package com.neven.soft.tinyspider.api.one.vo;

/**
 * 标签
 * Created by Neven on 16.09.16.
 */
public class Tag {

    //文字
    private String text;

    //颜色
    private int color;

    public Tag(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
