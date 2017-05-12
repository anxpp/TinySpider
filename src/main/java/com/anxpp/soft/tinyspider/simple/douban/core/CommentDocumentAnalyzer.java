package com.anxpp.soft.tinyspider.simple.douban.core;

import com.anxpp.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析CSDN每周知识干货html文档具体实现
 * Created by anxpp.com on 2017/3/11.
 */
@Component
public class CommentDocumentAnalyzer implements DocumentAnalyzer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final HashMap<String, Integer> rating = new HashMap<>(8);

    static {
        rating.put("allstar10 rating", 1);
        rating.put("allstar20 rating", 2);
        rating.put("allstar30 rating", 3);
        rating.put("allstar40 rating", 4);
        rating.put("allstar50 rating", 5);
    }

    @Resource
    private ReviewingService reviewingService;

    /**
     * 根据html文档对象获取List<Map>
     *
     * @param document html文档对象
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> forListMap(Document document, Object id) {
//        log.info("CommentDocumentAnalyzer::forListMap start");
        List<Map<String, Object>> results = new ArrayList<>();
        if (ObjectUtils.isEmpty(document))
            return results;
        int count = Integer.valueOf(document.body().getElementsByClass("CommentTabs").get(0).getElementsByTag("span").get(0).text().replaceAll("\\D+", ""));
        reviewingService.setCount(id.toString(), count);
        document.body().getElementById("comments").getElementsByClass("comment-item").forEach(ele -> {
            try {
                Map<String, Object> result = new HashMap<>();
                result.put("author", ele.getElementsByClass("avatar").get(0).getElementsByTag("a").get(0).attr("title").replaceAll("[^\\u0000-\\uFFFF]", ""));
                result.put("countUseful", Integer.valueOf(ele.getElementsByClass("comment").get(0).getElementsByClass("votes").get(0).text()));
                //需要过滤emoji
                result.put("comment", ele.getElementsByClass("comment").get(0).getElementsByTag("p").get(0).text().replaceAll("[^\\u0000-\\uFFFF]", ""));
                result.put("date", ele.getElementsByClass("comment").get(0).getElementsByClass("comment-time").get(0).text());
                result.put("rating", rating.get(ele.getElementsByClass("comment").get(0).getElementsByClass("rating").get(0).attr("class")));
                result.put("movieId", id.toString());
                results.add(result);
            } catch (Exception ignored) {
            }
        });
//        log.info("CommentDocumentAnalyzer::forListMap complete");
        return results;
    }
}