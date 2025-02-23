package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void addSetmealWithSetmealDish(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteBatchDishes(List<Long> ids);

    SetmealVO getByIdWithSetmealDish(Long id);

    void updateSetmealWithSetmealDish(SetmealDTO setmealDTO);

    void enableOrDisableSetmeal(Integer status, Long id);
}
