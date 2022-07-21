package com.ty.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ty.dao.dos.Archives;
import com.ty.domain.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
