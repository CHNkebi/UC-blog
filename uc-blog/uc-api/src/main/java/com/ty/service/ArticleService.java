package com.ty.service;

import com.ty.domain.http.Result;
import com.ty.domain.param.PageParams;

public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();
}
