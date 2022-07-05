package com.ty.service;

import com.ty.domain.http.ResponseResult;
import com.ty.domain.param.PageParams;
import com.ty.domain.pojo.Article;

import java.util.List;

public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    ResponseResult listArticle(PageParams pageParams);
}
