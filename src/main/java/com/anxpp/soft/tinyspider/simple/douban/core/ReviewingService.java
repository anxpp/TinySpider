package com.anxpp.soft.tinyspider.simple.douban.core;

import java.util.List;
import java.util.Map;

/**
 * 文章数据service
 * Created by anxpp.com on 2017/3/11.
 */
public interface ReviewingService {
    /**
     * 获取电影影评
     *
     * @param id 期号
     * @return 文章列表
     */
    Map<String, Integer> forComments(String id) throws Exception;

    /**
     * 查找影评
     *
     * @param text 关键字
     */
    List<MovieEntity> findReviewing(String text) throws Exception;

    /**
     * 设置总评论数
     *
     * @param id    id
     * @param count count
     */
    void setCount(String id, Integer count);
}
