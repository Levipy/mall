package com.levi.mall.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: levi
 * @Date: 2021/03/06/20:21
 * @Description:
 */
@Data
public class CategoryVO implements Serializable {


        private Integer id;

        private String name;

        private Integer type;

        private Integer parentId;

        private Integer orderNum;

        private Date createTime;

        private Date updateTime;

        private List<CategoryVO> childCategaryVO = new ArrayList<>();

}
