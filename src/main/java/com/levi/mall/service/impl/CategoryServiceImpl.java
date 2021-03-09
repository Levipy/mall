package com.levi.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.levi.mall.dao.CategoryMapper;
import com.levi.mall.entity.Category;
import com.levi.mall.exception.MallException;
import com.levi.mall.exception.MallExceptionEnum;
import com.levi.mall.request.AddCategoryReq;
import com.levi.mall.service.CategoryService;
import com.levi.mall.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: levi
 * @Date: 2021/03/05/17:53
 * @Description: CategoryService实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public void insertCategory(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        //查询数据库中是否有重名的category
        Category old_category = categoryMapper.selectByName(addCategoryReq.getName());
        if (old_category != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count ==0) {
            throw new MallException(MallExceptionEnum.INSERT_FAILED);
        }
    }


    @Override
    public void updateCategory(Category category) {
        Category old_category = categoryMapper.selectByName(category.getName());
        if (old_category != null && !category.getId().equals(old_category.getId())) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        System.out.println(count);
    }


    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        }
    }


    @Override
    public PageInfo categoryListforAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize,"type,order_num");
        List<Category> list = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Cacheable(value = "listCategoryForCustomer")
    @Override
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        List<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList,parentId);
        return categoryVOList;
    }

    /**
     * 递归查询商品分级目录
     * @param categoryVOList
     * @param parentId
     */
    public void recursivelyFindCategories(List<CategoryVO> categoryVOList,Integer parentId) {
        //根据parentId查找
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        System.out.println("size: "+categoryList.size());
        for (Category category : categoryList) {
            System.out.println(category);
        }
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i <categoryList.size() ; i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                //复制对应属性的信息
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);
                //依次循环给childCategory赋值,直到parentId查找到的集合为空为止
                recursivelyFindCategories(categoryVO.getChildCategaryVO(),categoryVO.getId());
            }
        }
    }

}
