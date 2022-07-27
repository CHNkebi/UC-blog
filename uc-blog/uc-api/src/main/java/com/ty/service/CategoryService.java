package com.ty.service;

import com.ty.domain.vo.CategoryVo;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
