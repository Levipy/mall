package com.levi.mall.dao;

import com.levi.mall.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNoAndUserId(String orderNo, Integer userId);

    List<Order> selectAllByUserId(Integer userId);

    List<Order> selectAll();

    Order selectByOrderNo(String orderNo);

}