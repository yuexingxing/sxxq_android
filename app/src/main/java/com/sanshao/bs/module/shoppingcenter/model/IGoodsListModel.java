package com.sanshao.bs.module.shoppingcenter.model;

import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public interface IGoodsListModel {

   void onRefreshData(List<GoodsDetailInfo> list);
}
