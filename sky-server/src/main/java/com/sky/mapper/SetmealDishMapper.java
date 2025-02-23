package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSeatmealIdsByDishIds(List<Long> ids);

    void insertBatch(List<SetmealDish> setmealDishes);

    void deleteBatchBySetmealIds(List<Long> ids);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);

    List<Long> getDishIdsBySeatmealIds(List<Long> setmealIds);

    @Select("select d.* from setmeal_dish sd left outer join dish d on sd.dish_id = d.id where sd.setmeal_id = #{setmealId}")
    List<Dish> getDishBySeatmealId(Long setmealId);
}
