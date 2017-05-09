package com.anxpp.soft.tinyspider.simple.douban.core;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文章数据访问层
 * Created by anxpp.com on 2017/3/11.
 */
public interface MovieRepo extends JpaRepository<MovieEntity, String> {
}
