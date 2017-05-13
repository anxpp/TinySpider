package com.neven.soft.tinyspider.simple.csdnweekly.core;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文章数据访问层
 * Created by Neven on 2017/3/11.
 */
public interface CsdnWeeklyArticleRepo extends JpaRepository<CsdnWeeklyArticleEntity, Long> {
}
