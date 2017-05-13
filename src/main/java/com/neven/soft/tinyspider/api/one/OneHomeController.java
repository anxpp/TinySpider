package com.neven.soft.tinyspider.api.one;

import com.neven.soft.tinyspider.api.one.service.OneHomeService;
import com.neven.soft.tinyspider.api.one.vo.ArticleVO;
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
 * Created by Neven on 2017/3/11.
 */
@Controller()
@RequestMapping("/api/one/home")
public class OneHomeController {

    private Logger log = LoggerFactory.getLogger(OneHomeController.class);

    @Resource
    private OneHomeService oneHomeService;

    @ResponseBody
    @GetMapping("/get/article/{page}")
    public List<ArticleVO> getArticleByStage(@PathVariable("page") Integer page) throws Exception {
        log.info("获取One首页内容：" + page);
        return oneHomeService.page(page);
    }
}