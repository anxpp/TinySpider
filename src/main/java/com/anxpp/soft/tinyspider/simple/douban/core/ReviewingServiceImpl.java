package com.anxpp.soft.tinyspider.simple.douban.core;

import com.anxpp.soft.tinyspider.Utils.TinySpider;
import com.anxpp.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 文章service实现
 * Created by anxpp.com on 2017/3/11.
 */
@Service
public class ReviewingServiceImpl implements ReviewingService {

    //
    private ConcurrentHashMap<String, ProcessingInfo> info = new ConcurrentHashMap<>();

    //当前网页的cookies
    private volatile Map<String, String> cookiesOfDouban = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${url.douban.movie.search.preurl}")
    private String preUrlOfSearch;

    @Value("${url.douban.movie.comment.preurl}")
    private String preUrlOfComment;

    @Resource
    private DocumentAnalyzer movieDocumentAnalyzer;

    @Resource
    private DocumentAnalyzer commentDocumentAnalyzer;

    @Resource
    private ReviewingRepo reviewingRepo;

    @Resource
    private MovieRepo movieRepo;

    /**
     * 获取电影影评
     *
     * @param id 期号
     * @return 文章列表
     */
    @Override
    public Map<String, Integer> forComments(String id) throws Exception {
        log.info("ReviewingServiceImpl::forComments -> begin:" + id);
        Map<String, Integer> map = new HashMap<>(3);
        ProcessingInfo processingInfo = info.get(id);
        if (processingInfo != null) {
            log.info("ReviewingServiceImpl::forComments -> processing");
            if (processingInfo.isFinish()) {
                map.put("state", 2);
                info.remove(id);
                updateMovieState(id, 2);
            } else {
                //返回进度
                map.put("state", 1);
                map.put("total", processingInfo.getCount());
                map.put("current", processingInfo.getCurrentIndex());
                updateMovieState(id, 1);
            }
            return map;
        } else {
            //check movie from database
            MovieEntity movieEntity = movieRepo.findOne(id);
            if (movieEntity != null) {
                //already complete
                if (movieEntity.getState() == 2) {
                    map.put("state", 2);
                    return map;
                } else {
                    //添加任务
                    log.info("ReviewingServiceImpl::forComments -> begin task");
                    processingInfo = new ProcessingInfo();
                    processingInfo.setCurrentIndex(movieEntity.getCurrent());
                    processingInfo.setCount(movieEntity.getCountReviewing());
                    info.put(id, processingInfo);
                    new SpiderTask().start(id);
                    map.put("state", 1);
                    map.put("total", processingInfo.getCount());
                    map.put("current", processingInfo.getCurrentIndex());
                    updateMovieState(id, 1);
                    return map;
                }
            } else {
                log.info("ReviewingServiceImpl::forComments -> begin task");
                //添加任务
                processingInfo = new ProcessingInfo();
                processingInfo.setCurrentIndex(0);
                processingInfo.setCount(100);
                info.put(id, processingInfo);
                //开始抓取任务
                new SpiderTask().start(id);
                map.put("state", 0);
                updateMovieState(id, 1);
                return map;
            }
        }
    }

    /**
     * 查找电影
     *
     * @param text 关键字
     */
    @Override
    public List<MovieEntity> findMovie(String text) throws Exception {
        List<MovieEntity> result = TinySpider.forEntityList(preUrlOfSearch + text, movieDocumentAnalyzer, MovieEntity.class);
        movieRepo.save(result);
        return result;
    }

    /**
     * 设置总评论数
     *
     * @param id    id
     * @param count count
     */
    @Override
    public void setCount(String id, int count) {
        info.get(id).setCount(count);
        updateMovieCount(id, count);
    }

    /**
     * 保存抓取结果
     *
     * @param result 抓取结果
     */
    private void save(List<CommentEntity> result) {
        reviewingRepo.save(result);
    }

    /**
     * 任务
     */
    private class SpiderTask {

        private Executor executor = Executors.newSingleThreadExecutor();

        void start(String id) {
            log.info("SpiderTask::start -> begin task");
            executor.execute(() -> {
                //首先登陆获取cookies
                if (cookiesOfDouban.isEmpty())
                    doLogin();
                //开始任务
                ProcessingInfo processingInfo = info.get(id);
                while (true) {
                    try {
                        int current = processingInfo.getAndIncreaseCurrentIndex();
                        List<CommentEntity> result = TinySpider.forEntityList(preUrlOfComment + id + "/comments?start=" + current, commentDocumentAnalyzer, CommentEntity.class, id, cookiesOfDouban);
                        //保存评论
                        save(result);
                        //更新影评抓取进度
                        updateMovieCurrent(id, current);
                    } catch (Exception e) {
                        log.info("SpiderTask::start");
                        e.printStackTrace();
                        doLogin();
                        try {
                            Thread.currentThread().sleep(1000 * 30);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (processingInfo.isFinish()) {
                        log.info("complete:" + id);
                        break;
                    }
                }
                log.info("SpiderTask::start -> end task");
            });
        }
    }

    private void updateMovieCurrent(String id, int current) {
        MovieEntity movieEntity = movieRepo.findOne(id);
        movieEntity.setCurrent(current);
        movieRepo.save(movieEntity);
    }

    private void updateMovieCount(String id, int count) {
        MovieEntity movieEntity = movieRepo.findOne(id);
        movieEntity.setCountReviewing(count);
        movieRepo.save(movieEntity);
    }

    private void updateMovieState(String id, int state) {
        MovieEntity movieEntity = movieRepo.findOne(id);
        movieEntity.setState(state);
        movieRepo.save(movieEntity);
    }

    private void doLogin() {
        try {
            cookiesOfDouban = Jsoup.connect("https://accounts.douban.com/login").headers(header()).method(Connection.Method.POST).data(params()).execute().cookies();
        } catch (IOException e) {
            log.info("doLogin");
            e.printStackTrace();
        }
    }

    //登陆需要的header
    private Map<String, String> header() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        header.put("Accept-Encoding", "gzip, deflate, sdch, br");
        header.put("Accept-Language", "zh-CN,zh;q=0.8");
        header.put("Connection", "keep-alive");
        header.put("Cookie", "bid=wfWEGLIV_NI; ll=\"108296\"; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1494244457%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DpXUgsqzqW_NO4uLREOlJQo4jlCNmwr-5oCyxAzIVz4C%26wd%3D%26eqid%3D975a0c4c00001b6d0000000459105c65%22%5D; _pk_id.100001.8cb4=2d5a11571a1aff9f.1490751991.8.1494244458.1494205377.; ps=y; _vwo_uuid_v2=3169EE1AEB5C338D6B43765087F1EC68|951b48cc61b26e01db33b23ffbbe56eb; push_noty_num=0; push_doumail_num=0; as=\"https://movie.douban.com/subject/25818101/comments?start=10000&limit=20&sort=new_score&status=P\"; ap=1; __utmt=1; __utma=30149280.1958286939.1490751992.1494308103.1494310923.11; __utmb=30149280.3.10.1494310923; __utmc=30149280; __utmz=30149280.1494308103.10.7.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/login");
        header.put("Host", "movie.douban.com");
        header.put("Referer", "https://movie.douban.com/explore");
        header.put("Upgrade-Insecure-Requests", "1");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        return header;
    }

    private Map<String, String> params() {
        Map<String, String> header = new HashMap<>();
        header.put("source", "movie");
        header.put("redir", "https://movie.douban.com/");
        header.put("form_email", "15215229221");
        header.put("form_password", "123698745");
//        header.put("captcha-solution", "hearing");
        header.put("login", "登录");
        return header;
    }

    /**
     * 数据抓取的进度信息
     */
    private static class ProcessingInfo {
        private final static int PAGE_SIZE = 20;
        //总评论数
        volatile int count;
        //当前抓取的位置
        volatile int currentIndex;

        boolean isFinish() {
            return currentIndex >= count;
        }

        int getCount() {
            return count;
        }

        void setCount(int count) {
            this.count = count;
        }

        void setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        int getAndIncreaseCurrentIndex() {
            int current = currentIndex;
            currentIndex += PAGE_SIZE;
            return current;
        }

        int getCurrentIndex() {
            return currentIndex;
        }
    }
}