package cn.sanshaoxingqiu.ssbm.module.order.model;

public interface OnPayListener {
    void onPaySuccess();

    void onPayFailed();
}
