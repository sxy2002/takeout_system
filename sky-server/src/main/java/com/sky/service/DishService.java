package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 菜品新增
     * @param dishDTO
     */
    void addDishWithFlavor(DishDTO dishDTO);
}
