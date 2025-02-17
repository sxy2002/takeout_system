package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    @Select("select count(id) from dish where category_id = #{catId}")
    Integer countDishByCategoryId(Long catId);

    @AutoFill(OperationType.INSERT)     // 别忘记！！！ 否则无法自动填充
    void insert(Dish dish);
}
