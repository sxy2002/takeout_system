package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public TurnoverReportVO turnover(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Double> turnoverList = new ArrayList<>();
        for(LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double sum = reportMapper.getSumByMap(map);
            sum = sum == null ? 0.0 : sum;
            turnoverList.add(sum);
        }
        TurnoverReportVO turnoverReportVO = TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
        return turnoverReportVO;
    }

    @Override
    public UserReportVO user(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> userList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        for(LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("end", endTime);
            Integer sum = reportMapper.getCountByMap(map);
            sum = sum == null ? 0 : sum;
            userList.add(sum);

            map.put("begin", beginTime);
            Integer newSum = reportMapper.getCountByMap(map);
            newSum = newSum == null ? 0 : newSum;
            newUserList.add(newSum);
        }

        UserReportVO userReportVO = UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(userList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
        return userReportVO;
    }

    @Override
    public OrderReportVO order(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderList = new ArrayList<>();
        Integer totalCnt = 0, valCnt = 0;
        for(LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer count = reportMapper.getOrderCountByMap(map);
            count = count == null ? 0 : count;
            orderCountList.add(count);
            totalCnt += count;

            map.put("status", Orders.COMPLETED);
            Integer newCnt = reportMapper.getOrderCountByMap(map);
            newCnt = newCnt == null ? 0 : newCnt;
            validOrderList.add(newCnt);
            valCnt += newCnt;
        }
//        orderCountList.stream().reduce(Integer::sum).get();

        OrderReportVO orderReportVO = OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderList, ","))
                .totalOrderCount(totalCnt)
                .validOrderCount(valCnt)
                .orderCompletionRate(totalCnt != 0 ? valCnt.doubleValue() / totalCnt : 0)   // 判断除数不为零
                .build();
        return orderReportVO;
    }

    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        Map map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", Orders.COMPLETED);
        List<GoodsSalesDTO> sales = reportMapper.getDishCountByMap(map);

        SalesTop10ReportVO salesTop10ReportVO = SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(sales.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()), ","))
                .numberList(StringUtils.join(sales.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()), ","))
                .build();

        return salesTop10ReportVO;
    }
}
