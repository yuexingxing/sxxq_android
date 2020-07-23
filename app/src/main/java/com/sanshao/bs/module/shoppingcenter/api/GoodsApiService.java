package com.sanshao.bs.module.shoppingcenter.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.ShoppingCenterResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface GoodsApiService {

    //获取商城列表
    @POST("/util/sms/fetch")
    Observable<BaseResponse<ShoppingCenterResponse>> getShoppingCenterList();

    //获取商品列表
    @POST("/util/sms/fetch")
    Observable<BaseResponse<List<GoodsDetailInfo>>> getGoodsList(@Query("mobile") String mobile,
                                                                 @Query("code") String code);

    //获取商品详情
    @POST("/util/sms/fetch")
    Observable<BaseResponse<List<GoodsDetailInfo>>> getGoodsDetail(@Query("goods_id") String goodsId);
}
