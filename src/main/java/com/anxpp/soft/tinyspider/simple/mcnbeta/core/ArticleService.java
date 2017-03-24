package com.anxpp.soft.tinyspider.simple.mcnbeta.core;

import java.util.List;

/**
 * 文章数据service
 * Created by anxpp.com on 2017/3/11.
 */
public interface ArticleService {
    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    List<ArticleEntity> forWeekly(Integer stage) throws Exception;
}
