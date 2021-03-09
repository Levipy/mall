package com.levi.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.levi.mall.common.Constant;
import com.levi.mall.dao.ProductMapper;
import com.levi.mall.entity.Product;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.query.ListProductQuery;
import com.levi.mall.request.AddProductReq;
import com.levi.mall.request.ListForCustomerProductReq;
import com.levi.mall.service.CategoryService;
import com.levi.mall.service.ProductService;
import com.levi.mall.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/07/16:03
 * @Description:
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void addProduct(AddProductReq addProductReq) {
        //先校验添加的商品名称是否已经存在
        Product old_product = productMapper.selectProductByName(addProductReq.getName());
        if (old_product != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq,product);
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public void updateProduct(Product product) {
        System.out.println(product);
        Product old_product = productMapper.selectProductByName(product.getName());
        if (old_product != null && !old_product.getId().equals(product.getId())) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        }
    }
    @Override
    public void deleteProduct(Integer id){
        Product old_product = productMapper.selectByPrimaryKey(id);
        if (old_product == null) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateStatus(Integer[] ids, Integer status){
        productMapper.batchUpdateStatus(ids, status);
    }

    @Override
    public PageInfo<Product> productListForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize,"stock");
        List<Product> list = productMapper.selectList();
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    @Cacheable(value = "product_detail")
    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }


    @Override
    public PageInfo productListForCustomer(ListForCustomerProductReq listForCustomerProductReq) {
        ListProductQuery listProductQuery = new ListProductQuery();
        //拼接字符串%keyword%,实现模糊查询
        if (!StringUtils.isEmpty(listForCustomerProductReq.getKeyword())) {
            String keyword = new StringBuilder().append("%").append(listForCustomerProductReq.getKeyword()).append("%").toString();
            listProductQuery.setKeyword(keyword);
        }//判断前台是否传了category_id
        if (listForCustomerProductReq.getCategoryId() != null) {
            //使用递归获取VO的树形结构，其中包含多个 category_id
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(listForCustomerProductReq.getCategoryId());
            List<Integer> categoryIdList = new ArrayList<>();
            categoryIdList.add(listForCustomerProductReq.getCategoryId());
            //获取目录及子目录的category_id,放入list集合中
            getcategoryList(categoryVOList,categoryIdList);
            listProductQuery.setCategoryIds(categoryIdList);
        }
        //排序设置，后台事先定义好排序的规则
        String orderBy = listForCustomerProductReq.getOrderBy();
        if (Constant.listForCustomerOrderBy.PRICE_DESC_ASC.contains(orderBy)) {
            PageHelper.startPage(listForCustomerProductReq.getPageNum(), listForCustomerProductReq.getPageSize(), orderBy+",id");
        }else {
            PageHelper.startPage(listForCustomerProductReq.getPageNum(), listForCustomerProductReq.getPageSize());
        }
        //获取根据条件查询出来的product集合，然后分页
        List<Product> productList = productMapper.list(listProductQuery);
        PageInfo pageInfo = new PageInfo<>(productList);
        return pageInfo;
    }
    /**
     * 获取目录及子目录的category_id,放入list集合中
     */
    private void getcategoryList(List<CategoryVO> categoryVOList,List<Integer> categoryIdList) {
        if (!CollectionUtils.isEmpty(categoryVOList)) {
            for (int i = 0; i < categoryVOList.size(); i++) {
                //先把所有父级目录的category_id给遍历插入集合，然后遍历子目录的category_id插入集合
                CategoryVO categoryVO =  categoryVOList.get(i);
                categoryIdList.add(categoryVO.getId());
                getcategoryList(categoryVO.getChildCategaryVO(),categoryIdList);
            }
        }
    }
}
