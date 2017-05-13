package com.neven.soft.tinyspider.simple.mcnbeta.controller;

import com.neven.soft.tinyspider.simple.mcnbeta.core.McnbetaArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 默认页面
 * Created by Neven on 2017/3/11.
 */
@Controller("mcnbetaArticleController")
@RequestMapping("/mcnbeta/article")
public class McnbetaArticleController {
    @Resource
    private McnbetaArticleService articleService;

    @ResponseBody
    @GetMapping("/get/page/{page}")
    public String getArticleByStage(@PathVariable("page") Integer page) throws Exception {
        return articleService.forPage(page);
    }
}