package com.anxpp.soft.tinyspider.api.one.service.impl;

import com.anxpp.soft.tinyspider.api.one.service.OneHomeService;
import com.anxpp.soft.tinyspider.api.one.vo.ArticleVO;
import com.anxpp.soft.tinyspider.api.one.vo.Tag;
import com.anxpp.soft.tinyspider.simple.csdnweekly.core.CsdnWeeklyArticleEntity;
import com.anxpp.soft.tinyspider.simple.csdnweekly.core.CsdnWeeklyArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * service
 * Created by anxpp.com on 2017/3/26.
 */
@Service
public class OneHomeServiceImpl implements OneHomeService {

    @Resource
    private CsdnWeeklyArticleService csdnWeeklyArticleService;

    /**
     * 获取One首页内容
     *
     * @param page 分页
     * @return 首页内容
     */
    @Override
    public List<ArticleVO> page(Integer page) throws Exception {
        List<CsdnWeeklyArticleEntity> csdnWeeklyArticleEntityList = csdnWeeklyArticleService.forWeekly(page);
        List<ArticleVO> articleVOList = new ArrayList<>(csdnWeeklyArticleEntityList.size());
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        csdnWeeklyArticleEntityList.forEach(articleEntity -> {
            //文章基础信息
            ArticleVO articleVO = new ArticleVO();
            articleVO.setImg(articleEntity.getImg());
            articleVO.setAuthorJobTitle(articleEntity.getType());
            articleVO.setTitle(articleEntity.getName());
            articleVO.setAuthorJobTitle("csdn第" + articleEntity.getStage() + "周干货推荐");
            articleVO.setDate(simpleDateFormat.format(articleEntity.getInertAt()));
            articleVO.setText(articleEntity.getName());
            articleVO.setUrl(articleEntity.getUrl());
            //文章tag
            List<Tag> tags = new ArrayList<>(2);
            tags.add(new Tag("第"+articleEntity.getStage()+"期", getIntFromColor(0xf4,0xa2,0x61)));//f4a261
            tags.add(new Tag("csdn干货", getIntFromColor(0xc4, 0x67, 0xf4)));
            articleVO.setTags(tags);
            articleVOList.add(articleVO);
        });
        return articleVOList;
    }

    /**
     * 颜色值转换
     *
     * @param red   红
     * @param green 绿
     * @param blue  蓝
     * @return int颜色值
     */
    private int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;
        return 0xFF000000 | red | green | blue;
    }
}