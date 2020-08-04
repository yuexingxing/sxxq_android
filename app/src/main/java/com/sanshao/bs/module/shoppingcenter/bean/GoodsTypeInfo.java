package com.sanshao.bs.module.shoppingcenter.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsTypeInfo {
    public String artitag_name;
    public String artitag_type;
//    @JSONField(name="set_meal_product")
    public List<GoodsDetailInfo> set_meal_product;
}
