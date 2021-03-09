package com.levi.mall.service;

import com.github.pagehelper.PageInfo;
import com.levi.mall.entity.Product;
import com.levi.mall.request.AddProductReq;
import com.levi.mall.request.ListForCustomerProductReq;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/07/16:03
 * @Description:
 */
public interface ProductService {
    /**
     * 管理员添加商品信息
     * @param addProductReq
     */
    void addProduct(AddProductReq addProductReq);

    /**
     * 管理员修改商品信息
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 管理员删除商品信息
     * @param id
     */
    void deleteProduct(Integer id);

    void batchUpdateStatus(Integer[] ids, Integer status);

    PageInfo<Product> productListForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo productListForCustomer(ListForCustomerProductReq listForCustomerProductReq);
}
