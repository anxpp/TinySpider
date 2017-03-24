package com.anxpp.soft.tinyspider.simple.mcnbeta.controller;

import com.anxpp.soft.tinyspider.simple.csdnweekly.core.ArticleEntity;
import com.anxpp.soft.tinyspider.simple.csdnweekly.core.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 默认页面
 * Created by anxpp.com on 2017/3/11.
 */
@Controller
@RequestMapping("/mcnbeta/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @ResponseBody
    @GetMapping("/get/stage/{stage}")
    public List<ArticleEntity> getArticleByStage(@PathVariable("stage") Integer stage) throws Exception {
        return articleService.forWeekly(stage);
    }
}