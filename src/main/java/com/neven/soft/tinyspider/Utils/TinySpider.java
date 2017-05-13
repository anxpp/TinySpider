package com.neven.soft.tinyspider.Utils;

import com.neven.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文章抓取工具
 * Created by Neven on 2017/3/11.
 */
public class TinySpider {

    private static final Logger log = LoggerFactory.getLogger(TinySpider.class);

    public static <T> List<T> forEntityList(String url, DocumentAnalyzer docAnalyzer, Class<T> type) throws Exception {
        return forEntityList(url, docAnalyzer, type, null, null);
    }

    public static <T> List<T> forEntityList(String url, DocumentAnalyzer docAnalyzer, Class<T> type, Object info, Map<String, String> cookies) throws Exception {

        log.info("spider is working ：" + url);

        List<T> results = new ArrayList<>();
        Connection connection = Jsoup.connect(url).timeout(50000);
        //设置请求头
        if (cookies != null && cookies.size() > 0)
            connection.cookies(cookies);
        Connection.Response response = connection.execute();
        //更新cookies
//        if (cookies != null && cookies.size() > 0)
//            mergeMap(response.cookies(), cookies);
        docAnalyzer.forListMap(response.parse(), info).forEach(map -> {
            try {
                results.add(TinyUtil.mapToBean(map, type));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return results;
    }

    private static Map<String, String> mergeMap(Map<String, String> base, Map<String, String> more) {
        more.forEach(base::put);
        more = base;
        return more;
    }
}