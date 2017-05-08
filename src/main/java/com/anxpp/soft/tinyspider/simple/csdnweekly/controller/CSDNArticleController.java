package com.anxpp.soft.tinyspider.simple.csdnweekly.controller;

import com.anxpp.soft.tinyspider.simple.csdnweekly.core.CsdnWeeklyArticleEntity;
import com.anxpp.soft.tinyspider.simple.csdnweekly.core.CsdnWeeklyArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller("csdnArticleController")
@RequestMapping("/csdnweekly/article")
public class CSDNArticleController {

    private Logger log = LoggerFactory.getLogger(CSDNArticleController.class);

    @Resource
    private CsdnWeeklyArticleService articleService;

    @ResponseBody
    @GetMapping("/get/stage/{stage}")
    public List<CsdnWeeklyArticleEntity> getArticleByStage(@PathVariable("stage") Integer stage) throws Exception {
        log.info("抓取CSDN每周干货");
        return articleService.forWeekly(stage);
    }
}