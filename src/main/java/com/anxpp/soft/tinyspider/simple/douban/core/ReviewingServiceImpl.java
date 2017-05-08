package com.anxpp.soft.tinyspider.simple.douban.core;

import com.anxpp.soft.tinyspider.Utils.TinySpider;
import com.anxpp.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章service实现
 * Created by anxpp.com on 2017/3/11.
 */
@Service
public class ReviewingServiceImpl implements ReviewingService {

    @Value("${url.douban.movie.search.preurl}")
    private String preUrl;

    @Resource
    private DocumentAnalyzer movieDocumentAnalyzer;

    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    @Override
    @Cacheable(value = "reportcache", keyGenerator = "csdnKeyGenerator")
    public List<ReviewingEntity> forWeekly(Integer stage) throws Exception {
//        List<ReviewingEntity> articleEntityList = TinySpider.forEntityList(preUrl + stage, mcnbetaDocumentAnalyzer, ReviewingEntity.class);
//        articleEntityList.forEach(article -> article.setStage(stage));
        return null;
    }

    /**
     * 查找影评
     *
     * @param text 关键字
     */
    @Override
    public List<MovieEntity> findReviewing(String text) throws Exception {
        return TinySpider.forEntityList(preUrl + text, movieDocumentAnalyzer, MovieEntity.class);
    }
}