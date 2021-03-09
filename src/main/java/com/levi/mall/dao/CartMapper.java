package com.levi.mall.dao;

import com.levi.mall.entity.Cart;
import com.levi.mall.vo.CartVO;
import com.levi.mall.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId);

    List<CartVO> selectCartVOList(@Param("userId") Integer userId);

    int updateSelectedOrNot(@Param("userId") Integer userId, @Param("productId") Integer productId,@Param("selected") Integer selectedCategory);


    List<Cart> selectByUserId(@Param("userId") Integer userId);
}