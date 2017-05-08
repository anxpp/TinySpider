package com.anxpp.soft.tinyspider.simple.douban.api;

import com.anxpp.soft.tinyspider.simple.douban.core.MovieEntity;
import com.anxpp.soft.tinyspider.simple.douban.core.ReviewingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 影评
 * Created by yangtao on 2017/5/8.
 */
@RestController
@RequestMapping("/douban/movie")
public class ReviewingApi {

    @Resource
    ReviewingService reviewingService;

    /**
     * 搜索电影
     *
     * @param text 电影名称
     * @return 返回信息
     */
    @ResponseBody
    @GetMapping("/search/{text}")
    public List<MovieEntity> search(@PathVariable("text") String text) throws Exception {
        return reviewingService.findReviewing(text);
    }

    /**
     * 处理影评
     *
     * @param movie 电影ID
     * @return 处理详情
     */
    @ResponseBody
    @GetMapping("/comments/{movie}")
    public String reviewing(@PathVariable("movie") String movie) {
        return null;
    }
}
