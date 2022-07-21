package com.ty.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.dao.ArticleMapper;
import com.ty.dao.dos.Archives;
import com.ty.domain.http.Result;
import com.ty.domain.param.PageParams;
import com.ty.domain.pojo.Article;
import com.ty.domain.vo.ArticleVo;
import com.ty.service.ArticleService;
import com.ty.service.SysUserService;
import com.ty.service.TagService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagService tagService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {
        //1. 这个是分页查询的类（代表着分离模式），要传入的是页面的页数和页面总数
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getSize());
        //2. LambdaQueryWrapper是MybatisPlus提供的，需要就导入这个包就可以了
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //3. 这里是根据字体排序
        //queryWrapper.orderByDesc(Article::getWeight);
        //4. 这里设置的是根据时间排序
        //queryWrapper.orderByDesc(Article::getCreateDate);
        //5. 这个方法    default Children orderByDesc(boolean condition, R column, R... columns) {是可变长参数的
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        // 因为articleMapper继承了BaseMapper了的，所有设查询的参数和查询出来的排序方式
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //这个就是我们查询出来的数据的数组了
        List<Article> records = articlePage.getRecords();
        //因为页面展示出来的数据不一定和数据库一样，所有我们要做一个转换
        //将在查出数据库的数组复制到articleVo中实现解耦合,vo和页面数据交互
        List<ArticleVo> articleVoList = copyList(records, true, true);
        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select id, title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select id, title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }


    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor));
        }
        return articleVoList;
    }
    //这个方法是主要点是BeanUtils，又Spring提供的，专门用来拷贝的，想Article和articlevo相同属性的拷贝过来返回
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if(isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        return articleVo;
    }
}
