package com.neven.soft.tinyspider.simple.douban.core;

import com.neven.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析CSDN每周知识干货html文档具体实现
 * Created by Neven on 2017/3/11.
 */
@Component
public class MovieDocumentAnalyzer implements DocumentAnalyzer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 根据html文档对象获取List<Map>
     *
     * @param document html文档对象
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> forListMap(Document document, Object info) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (ObjectUtils.isEmpty(document))
            return results;
        document.body().getElementsByClass("ul").forEach(ele -> {
            try {
                Map<String, Object> result = new HashMap<>();
                Element tr = ele.nextElementSibling().child(0).child(0);
                Element a = tr.child(1).getElementsByTag("a").first();
                result.put("name", a.text());
                result.put("id", a.attr("href").replaceAll("\\D+", ""));
                Element div = tr.getElementsByClass("pl").get(1).parent();
                result.put("rating", Double.valueOf(div.getElementsByClass("rating_nums").get(0).text()));
                result.put("comments", Integer.valueOf(div.getElementsByClass("pl").get(0).text().replaceAll("\\D+", "")));
                results.add(result);
            } catch (Exception ignored) {
            }
        });
        log.info("MovieDocumentAnalyzer::forListMap complete");
        return results;
    }
}