package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 菜品新增
     * @param dishDTO
     */
    void addDishWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatchDishes(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateDishWithFlavor(DishDTO dishDTO);

    void enableOrDisableDish(Integer status, Long id);

    List<Dish> list(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}
