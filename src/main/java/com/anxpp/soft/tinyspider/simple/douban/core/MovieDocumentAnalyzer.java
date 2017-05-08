package com.anxpp.soft.tinyspider.simple.douban.core;

import com.anxpp.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析CSDN每周知识干货html文档具体实现
 * Created by anxpp.com on 2017/3/11.
 */
@Component
public class MovieDocumentAnalyzer implements DocumentAnalyzer {
    /**
     * 根据html文档对象获取List<Map>
     *
     * @param document html文档对象
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> forListMap(Document document) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (ObjectUtils.isEmpty(document))
            return results;
        document.body().getElementsByClass("ul").forEach(ele -> {
            Map<String, Object> result = new HashMap<>();
            Element tr = ele.nextElementSibling().child(0);
            Element a = tr.children().get(0).getElementsByTag("a").first();
            result.put("name", a.attr("title"));
            result.put("id", a.attr("href"));
            Element div = ele.nextElementSibling().child(1).getElementsByClass("star").get(0);
            result.put("rating", Double.valueOf(div.child(1).text()));
            result.put("comments", Double.valueOf(div.child(2).text().replaceAll("\\D+", "")));
            results.add(result);
        });
        return results;
    }
}