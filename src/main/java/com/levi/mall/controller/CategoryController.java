package com.levi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.entity.Category;
import com.levi.mall.request.AddCategoryReq;
import com.levi.mall.request.UpdateCategoryReq;
import com.levi.mall.service.CategoryService;
import com.levi.mall.service.UserService;
import com.levi.mall.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 *
 * @Auther: levi
 * @Date: 2021/03/05/15:21
 * @Description:
 */
@RestController
public class CategoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 管理员添加商品类别
     * @param addCategoryReq
     * @return
     */
    @ApiOperation("管理员添加商品类别")
    @PostMapping("/admin/category/add")
    public ApiRestResponse addCategory(@Valid @RequestBody AddCategoryReq addCategoryReq) {
            categoryService.insertCategory(addCategoryReq);
            return ApiRestResponse.success();
    }

    /**
     * 管理员修改商品目录信息
     * @param updateCategory
     * @return
     */
    @ApiOperation("管理员修改商品目录类别")
    @PostMapping("/admin/category/update")
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategory){

            Category category = new Category();
            BeanUtils.copyProperties(updateCategory,category);
            categoryService.updateCategory(category);
            return ApiRestResponse.success();

    }

    /**
     * 管理员删除商品目录类别
     * @param id
     * @return
     */
    @ApiOperation("管理员删除商品目录类别")
    @PostMapping("/admin/category/delete")
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.deleteCategory(id);
        return ApiRestResponse.success();
    }

    /**
     * 显示管理员后台的商品目录列表信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("显示管理员后台的商品目录列表信息")
    @PostMapping("/admin/category/list")
    public ApiRestResponse categoryListForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = categoryService.categoryListforAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("显示给用户看的商品信息列表")
    @PostMapping("/user/category/list")
    public ApiRestResponse categoryListForCustomer() {
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOS);
    }
}
