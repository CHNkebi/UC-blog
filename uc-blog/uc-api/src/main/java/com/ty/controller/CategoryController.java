package com.ty.controller;


import com.ty.domain.http.Result;
import com.ty.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }
}
