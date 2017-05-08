package com.anxpp.soft.tinyspider.api.one.vo;

import java.util.List;

/**
 * 文章
 * Created by anxpp.com on 16.09.16.
 */
public class ArticleVO {
    private String title;
    private String authorJobTitle;
    private String img;
    private String date;
    private String text;
    private String url;
    private List<Tag> tags;

    public ArticleVO(){

    }

    public ArticleVO(String title, String authorJobTitle, String img, String date, String text, List<Tag> tags) {
        this.title = title;
        this.authorJobTitle = authorJobTitle;
        this.img = img;
        this.date = date;
        this.text = text;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorJobTitle() {
        return authorJobTitle;
    }

    public void setAuthorJobTitle(String authorJobTitle) {
        this.authorJobTitle = authorJobTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
