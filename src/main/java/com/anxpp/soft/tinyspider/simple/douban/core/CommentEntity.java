package com.anxpp.soft.tinyspider.simple.douban.core;

import com.anxpp.soft.tinyspider.config.base.BaseEntity;

import javax.persistence.Entity;

/**
 * 影评实体
 * Created by anxpp.com on 2017/5/8.
 */
@Entity
public class CommentEntity extends BaseEntity {

    //作者
    private String movieId;
    //作者
    private String author;
    //评分
    private Integer rating;
    //日期
    private String date;
    //有用数
    private Integer countUseful;
    //评论内容
    private String comment;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCountUseful() {
        return countUseful;
    }

    public void setCountUseful(Integer countUseful) {
        this.countUseful = countUseful;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
