package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    void update(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    Orders getByID(Long id);

    @Select("select count(id) from orders where status = #{status}")
    Integer getCountByStatus(Integer status);

    @Select("select * from orders where status = #{status} and order_time < #{time}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime time);
}
