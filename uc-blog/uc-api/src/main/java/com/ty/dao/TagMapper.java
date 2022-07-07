package com.ty.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ty.domain.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {


    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> selectTagsByArticleId(Long articleId);
}