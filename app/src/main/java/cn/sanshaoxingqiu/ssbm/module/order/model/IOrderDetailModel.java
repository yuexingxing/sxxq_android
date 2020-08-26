package cn.sanshaoxingqiu.ssbm.module.order.model;

import com.exam.commonbiz.base.IBaseModel;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

public interface IOrderDetailModel extends IBaseModel {

    void returnOrderDetailInfo(GoodsDetailInfo goodsDetailInfo);

    void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse);

    void returnCancelOrder();
}
