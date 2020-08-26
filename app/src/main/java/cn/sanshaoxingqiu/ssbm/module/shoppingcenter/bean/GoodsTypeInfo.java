package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsTypeInfo implements Serializable {
    public String artitag_id;
    public String artitag_name;
    public String artitag_type;
    public String artitag_url;
    public List<GoodsDetailInfo> set_meal_product;
}
