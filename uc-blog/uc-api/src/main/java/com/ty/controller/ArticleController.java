package com.ty.controller;


import com.ty.domain.http.Result;
import com.ty.domain.vo.param.ArticleParam;
import com.ty.domain.vo.param.PageParams;
import com.ty.service.ArticleService;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle() {
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 首页文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public  Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);

    }


    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

}
