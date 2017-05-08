package com.anxpp.soft.tinyspider.api.one.service;

import com.anxpp.soft.tinyspider.api.one.vo.ArticleVO;

import java.util.List;

/**
 * service
 * Created by anxpp.com on 2017/3/26.
 */
public interface OneHomeService {

    /**
     * 获取One首页内容
     *
     * @param page 分页
     * @return 首页内容
     */
    List<ArticleVO> page(Integer page) throws Exception;
}
