package com.levi.mall.service;

import com.github.pagehelper.PageInfo;
import com.levi.mall.entity.Category;
import com.levi.mall.request.AddCategoryReq;
import com.levi.mall.vo.CategoryVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Levi
 * @Date: 2021/03/05/17:52
 * @Description:
 */
public interface CategoryService {

    /**
     * 新增商品目录
     * @param addCategoryReq
     */
    void insertCategory(AddCategoryReq addCategoryReq);

    /**
     * 管理员修改商品目录
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 管理员删除商品目录
     * @param id
     */
    void deleteCategory(Integer id);

    /**
     *商品目录分页给管理员看
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo categoryListforAdmin(Integer pageNum, Integer pageSize);

    /**
     * 商品目录给用户看
     * @return
     */
    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
