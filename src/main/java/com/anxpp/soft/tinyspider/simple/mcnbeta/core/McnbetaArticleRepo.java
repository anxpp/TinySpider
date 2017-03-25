package com.anxpp.soft.tinyspider.simple.mcnbeta.core;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文章数据访问层
 * Created by anxpp.com on 2017/3/11.
 */
public interface McnbetaArticleRepo extends JpaRepository<McnbetaArticleEntity, Long> {
}
