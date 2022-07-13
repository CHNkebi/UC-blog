package com.ty.controller;


import com.ty.domain.http.ResponseResult;
import com.ty.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Resource
    public TagService tagService;

    /**
     * /tags/hot
     * @return
     */
    @GetMapping("hot")
    public ResponseResult hot() {
        int limit = 6;
        return tagService.hots(limit);
    }

}
