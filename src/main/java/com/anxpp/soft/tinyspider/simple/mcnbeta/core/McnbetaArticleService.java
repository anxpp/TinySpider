package com.anxpp.soft.tinyspider.simple.mcnbeta.core;

import java.util.List;

/**
 * 文章数据service
 * Created by anxpp.com on 2017/3/11.
 */
public interface McnbetaArticleService {
    /**
     * 根据期号获取文章列表
     *
     * @param page 页号
     * @return 文章列表
     */
    String forPage(Integer page) throws Exception;
}
