package com.sanshao.bs.module.register.model;

import com.sanshao.bs.module.login.bean.LoginResponse;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public interface IRegisterCallBack {

    void onGetCode();

    void registerSucc(LoginResponse response);

    void registerFail();

    void showGoods(List<GoodsDetailInfo> goodsList);

    void getUserInfoSucc(UserInfo userInfo);
}
