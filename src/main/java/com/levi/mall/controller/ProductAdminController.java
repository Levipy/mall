package com.levi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.levi.mall.common.ApiRestResponse;
import com.levi.mall.common.Constant;
import com.levi.mall.entity.Product;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.request.AddProductReq;
import com.levi.mall.request.UpdateProductReq;
import com.levi.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/07/15:44
 * @Description:
 */
@RestController
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    @ApiOperation("新增商品")
    @PostMapping("/admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.addProduct(addProductReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("上传图片")
    @PostMapping("/admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        //获取文件名后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        //新建一个uuid
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffix;
        System.out.println(Constant.FILE_UPLOAD_DIR);
        //新建文件
        File fileDerectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDerectory.exists()) {
            if (!fileDerectory.mkdir()) {
                throw new MallException(MallExceptionEnum.MKDIR_FAILED);
            }
        }
        //把file的内容写入目标file
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(Constant.getHost(new URI(request.getRequestURL()+""))+"/images/"+newFileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ApiRestResponse.error(MallExceptionEnum.UPLOAD_FAILED);
        }
    }

    @ApiOperation("修改商品信息")
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.updateProduct(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("根据id删除商品信息")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(Integer id) {
        productService.deleteProduct(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("批量上下架商品")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateStatus(@RequestParam("ids") Integer[] ids,@RequestParam("status") Integer status) {
        productService.batchUpdateStatus(ids, status);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品信息列表")
    @PostMapping("/admin/product/list")
    public ApiRestResponse productListForAdmin(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize")Integer pageSize) {
        PageInfo<Product> pageInfo = productService.productListForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
