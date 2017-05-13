package com.neven.soft.tinyspider.simple.douban.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 电影
 * Created by Neven on 2017/5/8.
 */
@Entity
public class MovieEntity {

    @Id
    @Column(length = 128)
    private String id;

    private String name;

    private Double rating;

    private Integer comments;

    private String info;

    private String img;

    private Integer countReviewing;

    private Integer current;

    private Integer state;

    public MovieEntity() {
        this.countReviewing = 100;
        this.current = 0;
        this.state = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getCountReviewing() {
        return countReviewing;
    }

    public void setCountReviewing(Integer countReviewing) {
        this.countReviewing = countReviewing;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
