package com.anxpp.soft.tinyspider.simple.douban.api;

import com.anxpp.soft.tinyspider.simple.douban.core.MovieEntity;
import com.anxpp.soft.tinyspider.simple.douban.core.ReviewingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 影评
 * Created by yangtao on 2017/5/8.
 */
@RestController
@RequestMapping("/douban/movie")
public class ReviewingApi {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ReviewingService reviewingService;

    /**
     * 搜索电影
     *
     * @param text 电影名称
     * @return 返回信息
     */
    @ResponseBody
    @GetMapping("/search/{text}")
    public List<MovieEntity> search(@PathVariable("text") String text) throws Exception {
        return reviewingService.findMovie(text);
    }

    /**
     * 处理影评
     *
     * @param movie 电影ID
     * @return 处理详情
     */
    @ResponseBody
    @GetMapping("/comments/{movie}")
    public Map<String, Object> reviewing(@PathVariable("movie") String movie, String code, String robot) throws Exception {
        log.info("reviewing：" + movie);
        return reviewingService.forComments(movie, code, robot);
    }
}
