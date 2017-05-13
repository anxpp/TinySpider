package com.neven.soft.tinyspider.simple.douban.core;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 文章数据访问层
 * Created by Neven on 2017/3/11.
 */
public interface MovieRepo extends JpaRepository<MovieEntity, String> {
    List<MovieEntity> findByState(int state);
}
