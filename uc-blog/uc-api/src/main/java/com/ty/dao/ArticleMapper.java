package com.ty.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.dao.dos.Archives;
import com.ty.domain.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();


    //mybatis-plus分页
    IPage<Article> listAticle(Page<Article> page,
                              Long categoryId,
                              Long tagId,
                              String year,
                              String month);

}
