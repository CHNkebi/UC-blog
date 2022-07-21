package com.ty.service;

import com.ty.domain.http.Result;
import com.ty.domain.vo.TagVo;

import java.util.List;

public interface TagService {


    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
