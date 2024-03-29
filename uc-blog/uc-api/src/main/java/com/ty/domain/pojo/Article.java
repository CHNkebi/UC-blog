package com.ty.domain.pojo;

import lombok.Data;
/**
 * 博客管理
 **/

@Data
public class Article {

    public static final Integer Article_TOP = 1;

    public static final Integer Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight = Article_Common;


    /**
     * 创建时间
     */
    private Long createDate;
}




