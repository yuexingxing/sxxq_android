package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model;

import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public interface IGuessYouLoveModel {
    void returnGuessYouLoveData(List<GoodsDetailInfo> goodsDetailInfoList);
}
