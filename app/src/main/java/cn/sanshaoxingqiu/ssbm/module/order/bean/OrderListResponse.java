package cn.sanshaoxingqiu.ssbm.module.order.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

public class OrderListResponse implements Serializable {

    public int count;

    @SerializedName("rows")
    public List<GoodsDetailInfo> content;
}
