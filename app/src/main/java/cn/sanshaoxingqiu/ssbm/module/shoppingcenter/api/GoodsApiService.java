package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.api;

import com.exam.commonbiz.net.BaseResponse;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.ShoppingCenterResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface GoodsApiService {

    //首页数据
    @GET("ssxq/home/list")
    Observable<BaseResponse<ShoppingCenterResponse>> getShoppingCenterList();

    //获取商品列表
    @GET("ssxq/home/tags")
    Observable<BaseResponse<List<GoodsDetailInfo>>> getGoodsList(@Query("artitag_id") String artiTagId,
                                                                 @Query("offset") int offset,
                                                                 @Query("limit") int limit);

    //获取商品详情
    @GET("ssxq/product/detail")
    Observable<BaseResponse<GoodsDetailInfo>> getGoodsDetail(@Query("sarti_id") String sartiId);

    //猜你喜欢
    @GET("ssxq/product/guess/like")
    Observable<BaseResponse<List<GoodsDetailInfo>>> getGuessYouLoveData();
}
