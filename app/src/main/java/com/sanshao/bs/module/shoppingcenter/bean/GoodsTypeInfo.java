package com.sanshao.bs.module.shoppingcenter.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsTypeInfo {
    public String title;
    @JSONField(name="set_meal_product")
    public List<GoodsDetailInfo> setMealProduct;
}
