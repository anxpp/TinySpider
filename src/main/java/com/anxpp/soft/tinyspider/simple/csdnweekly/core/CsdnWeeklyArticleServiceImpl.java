package com.anxpp.soft.tinyspider.simple.csdnweekly.core;

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
public class CsdnWeeklyArticleServiceImpl implements CsdnWeeklyArticleService {
    @Value("${url.csdnweekly.preurl}")
    private String preUrl;
    @Resource
    private DocumentAnalyzer csdnWeeklyDocumentAnalyzer;
    @Resource
    private CsdnWeeklyArticleRepo csdnWeeklyArticleRepo;

    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    @Override
    @Cacheable(value = "csdnweekly", keyGenerator = "keyGenerator")
    public List<CsdnWeeklyArticleEntity> forWeekly(Integer stage) throws Exception {
        List<CsdnWeeklyArticleEntity> articleEntityList = TinySpider.forEntityList(preUrl + stage, csdnWeeklyDocumentAnalyzer, CsdnWeeklyArticleEntity.class);
        articleEntityList.forEach(article -> article.setStage(stage));
        csdnWeeklyArticleRepo.save(articleEntityList);
        return articleEntityList;
    }
}