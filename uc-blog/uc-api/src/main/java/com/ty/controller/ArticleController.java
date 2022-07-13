package com.ty.controller;


import com.ty.domain.http.ResponseResult;
import com.ty.domain.param.PageParams;
import com.ty.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping("")
    public ResponseResult listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }
}
