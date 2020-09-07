package cn.sanshaoxingqiu.ssbm.module.register.model;

import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import com.exam.commonbiz.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public interface IRegisterCallBack {

    void onGetCode();

    void registerSucc(LoginResponse response);

    void registerFail();

    void showGoods(List<GoodsDetailInfo> goodsList);

    void getUserInfoSucc(UserInfo userInfo);
}
