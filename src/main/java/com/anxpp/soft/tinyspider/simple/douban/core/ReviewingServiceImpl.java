package com.anxpp.soft.tinyspider.simple.douban.core;

import com.anxpp.soft.tinyspider.Utils.TinySpider;
import com.anxpp.soft.tinyspider.Utils.analyzer.DocumentAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private ConcurrentHashMap<String, ProcessingInfo> info = new ConcurrentHashMap<>();

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

    /**
     * 获取电影影评
     *
     * @param id 期号
     * @return 文章列表
     */
    @Override
    public Map<String, Integer> forComments(String id) throws Exception {
        ProcessingInfo processingInfo = info.get(id);
        if (processingInfo != null) {
            log.info("ReviewingServiceImpl::forComments -> processing");
            //返回进度
            Map<String, Integer> map = new HashMap<>(3);
            map.put("total", processingInfo.getCount());
            map.put("current", processingInfo.getCurrentIndex());
            return map;
        } else {
            log.info("ReviewingServiceImpl::forComments -> begin task");
            //添加任务
            processingInfo = new ProcessingInfo();
            info.put(id, processingInfo);
            //开始抓取任务
            new SpiderTask().start(id);
        }
        return null;
    }

    /**
     * 查找影评
     *
     * @param text 关键字
     */
    @Override
    public List<MovieEntity> findReviewing(String text) throws Exception {
        return TinySpider.forEntityList(preUrlOfSearch + text, movieDocumentAnalyzer, MovieEntity.class);
    }

    /**
     * 设置总评论数
     *
     * @param id    id
     * @param count count
     */
    @Override
    public void setCount(String id, Integer count) {
        info.get(id).setCount(count);
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
                ProcessingInfo processingInfo = info.get(id);
                while (true) {
                    try {
                        List<CommentEntity> result = TinySpider.forEntityList(preUrlOfComment + id + "/comments?start=" + processingInfo.getCurrentIndex(), commentDocumentAnalyzer, CommentEntity.class, id);
                        save(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (processingInfo.isFinish())
                        break;
                }
                log.info("SpiderTask::start -> end task");
            });
        }
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

        public void setCount(int count) {
            this.count = count;
        }

        int getCurrentIndex() {
            int current = currentIndex;
            currentIndex += PAGE_SIZE;
            return current;
        }
    }
}