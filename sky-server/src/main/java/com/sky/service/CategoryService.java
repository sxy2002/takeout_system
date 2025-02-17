package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

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

    /**
     * 根据id删除分类
     * @param catId
     */
    void deleteCategoryById(Long catId);

    /**
     * 根据类型查询分类
     *
     * @param type
     * @return
     */
    List<Category> listCategory(Integer type);
}
