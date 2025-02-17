package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {

    /**
     * 新增菜品分类
     * @param categoryDTO
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 分类启用已禁用
     * @param status
     * @param catId
     */
    void enableOrDisableCategory(Integer status, Long catId);

    /**
     * 分类修改
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);
}
