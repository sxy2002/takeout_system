package com.sky.mapper;

import com.sky.dto.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    Double getSumByMap(Map map);

    Integer getCountByMap(Map map);

    Integer getOrderCountByMap(Map map);

    List<GoodsSalesDTO> getDishCountByMap(Map map);
}