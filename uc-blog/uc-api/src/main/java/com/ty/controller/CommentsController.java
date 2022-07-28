package com.ty.controller;


import com.ty.domain.http.Result;
import com.ty.domain.vo.param.CommentParam;
import com.ty.service.CommentsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Resource
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id) {
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam) {
        return commentsService.commment(commentParam);
    }
}
