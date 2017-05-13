package com.neven.soft.tinyspider.simple.mcnbeta.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章service实现
 * Created by Neven on 2017/3/11.
 */
@Service
public class McnbetaArticleServiceImpl implements McnbetaArticleService {
    @Value("${url.mcnbeta.preurl}")
    private String preUrl;
    @Resource
    private RestTemplate restTemplate;

    /**
     * 根据期号获取文章列表
     *
     * @param page 期号
     * @return 文章列表
     */
    @Override
    @Cacheable(value = "mcnbeta", keyGenerator = "keyGenerator")
    public String forPage(Integer page) throws Exception {
        Map<String, Integer> params = new HashMap<>(3);
        params.put("page", page);
        return restTemplate.getForObject("http://m.cnbeta.com/touch/default/timeline.json", String.class, params);
    }
}