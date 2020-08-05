package com.sanshao.bs.module.home.model;

/**
 * @Author yuexingxing
 * @time 2020/6/15
 */
public class BannerInfo {
    public String artitag_id;
    public String artitag_name;
    public String artitag_url;
    public String artitag_type;//1：轮播（显示标签），2：爆款促销（显示商品），3：好物推荐（显示商品），4：商品分类（显示标签）,5:静态广告
    public String action_type;//GOODS=跳转到商品详情页
    public String videoUrl;
    public String videoPic;

    public interface ActionType{
        String REG = "REG";
        String NEW_MEM = "NEW_MEM";
        String GOODS_LIST = "GOODS_LIST";
    }
}
