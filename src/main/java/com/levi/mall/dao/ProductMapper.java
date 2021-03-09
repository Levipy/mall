package com.levi.mall.dao;

import com.levi.mall.entity.Product;
import com.levi.mall.query.ListProductQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectProductByName(String name);

    void batchUpdateStatus(@Param("ids") Integer[] ids, @Param("status") Integer status);

    List<Product> selectList();

    List<Product> list(@Param("query") ListProductQuery query);
}