package com.levi.mall.query;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/08/9:54
 * @Description:
 */
@Data
public class ListProductQuery {
    private String keyword;
    private List<Integer> categoryIds;
}
