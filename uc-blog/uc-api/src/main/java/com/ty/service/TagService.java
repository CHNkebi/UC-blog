package com.ty.service;

import com.ty.domain.http.Result;
import com.ty.domain.vo.TagVo;

import java.util.List;

public interface TagService {


    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
