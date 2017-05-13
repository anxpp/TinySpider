package com.neven.soft.tinyspider.simple.csdnweekly.controller;

import com.neven.soft.tinyspider.simple.csdnweekly.core.CsdnWeeklyArticleEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章接口单元测试
 * Created by Neven on 2017/3/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleEntityControllerTest {

    private int stage;

    @Resource
    private CSDNArticleController articleController;

    @Before
    public void setUp() throws Exception {
        this.stage = 1;
    }

    @Test
    public void getArticleByStage() throws Exception {
        List<CsdnWeeklyArticleEntity> articleEntityList = articleController.getArticleByStage(stage);
        articleController.getArticleByStage(stage);
        articleController.getArticleByStage(stage);
        Assert.assertNotNull(articleEntityList);
    }

}