package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityGoodsListBinding;
import cn.sanshaoxingqiu.ssbm.module.invitation.view.InvitationActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderBenefitResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderNumStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderPayInfoResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderDetailModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IOrderModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IPayModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.OnPayListener;
import cn.sanshaoxingqiu.ssbm.module.order.util.PayUtils;
import cn.sanshaoxingqiu.ssbm.module.order.view.ConfirmOrderActivity;
import cn.sanshaoxingqiu.ssbm.module.order.view.ConfirmPayActivity;
import cn.sanshaoxingqiu.ssbm.module.order.view.PayCompleteActivity;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderDetailViewModel;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderViewModel;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.PayViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.register.view.RegisterActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsTypeInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGoodsListModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.util.ShoppingCenterUtil;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter.GoodsListAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.BenefitsRightDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel.GoodsListViewModel;
import cn.sanshaoxingqiu.ssbm.util.BitmapUtil;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.OnItemEnterOrExitVisibleHelper;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.log.XLog;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * 商品列表
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsListActivity extends BaseActivity<GoodsListViewModel, ActivityGoodsListBinding> implements BaseQuickAdapter.RequestLoadMoreListener,
        IGoodsListModel, IOrderModel, IOrderDetailModel, IPayModel {

    private String mArtiTagId;
    private int mPageNum = 0;
    private GoodsListAdapter mGoodsListAdapter;
    private UserInfo mUserInfo;
    private OrderViewModel mOrderViewModel;
    private OrderDetailViewModel mOrderDetailViewModel;
    private PayViewModel mPayViewModel;
    private String mPayType;
    private String mSalebillId;

    public static void start(Context context, GoodsTypeInfo goodsTypeInfo) {
        Intent starter = new Intent(context, GoodsListActivity.class);
        starter.putExtra(Constants.OPT_DATA, goodsTypeInfo);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {

        mUserInfo = SSApplication.getInstance().getUserInfo();
        GoodsTypeInfo goodsTypeInfo = (GoodsTypeInfo) getIntent().getSerializableExtra(Constants.OPT_DATA);
        mArtiTagId = goodsTypeInfo.artitag_id;
        if (!TextUtils.isEmpty(goodsTypeInfo.artitag_name)) {
            binding.titleBar.setTitle(goodsTypeInfo.artitag_name);
        }
        mOrderViewModel = new OrderViewModel();
        mOrderDetailViewModel = new OrderDetailViewModel();
        mPayViewModel = new PayViewModel();

        mOrderViewModel.setCallBack(this);
        mOrderDetailViewModel.setCallBack(this);
        mPayViewModel.setCallBack(this);
        mViewModel.setCallBack(this);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mPageNum = 0;
            mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
        });

        mGoodsListAdapter = new GoodsListAdapter();
        mGoodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void onBuyClick(GoodsDetailInfo goodsDetailInfo) {
                if (!SSApplication.isLogin()) {
                    RegisterActivity.start(context, ShoppingCenterUtil.getRegisterTagId());
                    return;
                }
                mSalebillId = goodsDetailInfo.salebill_id;
                if (goodsDetailInfo.isPayByMoney() && !goodsDetailInfo.isFree()) {
                    ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
                } else if (goodsDetailInfo.isPayByPoint()) {
                    if (mUserInfo.available_point == 0) {
                        InvitationActivity.start(context, ShoppingCenterUtil.getInviteTagId());
                    } else {
                        ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
                    }
                } else {
                    if (!mUserInfo.hasBenefitsRight() && goodsDetailInfo.isFree()) {
                        new BenefitsRightDialog().show(context, new CommonCallBack() {
                            @Override
                            public void callback(int postion, Object object) {
                                showPayTypeBottomDialog();
                            }
                        });
                        return;
                    }
                    ConfirmOrderActivity.start(context, goodsDetailInfo.sarti_id);
                }
            }

            @Override
            public void onGoToDetail(GoodsDetailInfo goodsDetailInfo) {
                GoodsDetailActivity.start(GoodsListActivity.this, goodsDetailInfo.sarti_id);
            }

            @Override
            public void onShareClick(GoodsDetailInfo goodsDetailInfo) {
                share(goodsDetailInfo);
            }

            @Override
            public void onConsultClick() {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.goodsListRecyclerView.setLayoutManager(linearLayoutManager);
        binding.goodsListRecyclerView.setAdapter(mGoodsListAdapter);
        mGoodsListAdapter.setEnableLoadMore(true);
        mGoodsListAdapter.setOnLoadMoreListener(this, binding.goodsListRecyclerView);
        binding.goodsListRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd == null || jzvd.jzDataSource == null) {
                    return;
                }
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
        binding.emptyLayout.bindView(binding.goodsListRecyclerView);
        binding.emptyLayout.setOnButtonClick(view -> {
            mPageNum = 0;
            mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
        });

        binding.ivToTop.setOnClickListener(view -> binding.goodsListRecyclerView.smoothScrollToPosition(0));
        OnItemEnterOrExitVisibleHelper helper = new OnItemEnterOrExitVisibleHelper();
        helper.setRecyclerScrollListener(binding.goodsListRecyclerView);
        helper.setOnScrollStatusListener(new OnItemEnterOrExitVisibleHelper.OnScrollStatusListener() {
            public void onSelectEnterPosition(int postion) {
                XLog.d(TAG, "进入Enter：" + postion);
                if (postion >= 6) {
                    binding.ivToTop.setVisibility(View.VISIBLE);
                } else {
                    binding.ivToTop.setVisibility(View.GONE);
                }
            }

            public void onSelectExitPosition(int postion) {
                XLog.d(TAG, "退出Exit：" + postion);
                if (postion >= mGoodsListAdapter.getData().size()) {
                    return;
                }
                mGoodsListAdapter.getData().get(postion).isPlay = false;
                mGoodsListAdapter.notifyItemChanged(postion);
            }
        });

        mGoodsListAdapter.setPreLoadNumber(1);
        mGoodsListAdapter.disableLoadMoreIfNotFullPage();
        LoadDialogMgr.getInstance().show(context);
        mViewModel.getGoodsList(mArtiTagId, mPageNum, Constants.PAGE_SIZE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mPayType)) {
            mPayViewModel.fVipPay(PayViewModel.CHECK_ORDER_STATUS, mPayType);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        ++mPageNum;
        mViewModel.getGoodsList(mArtiTagId, mPageNum * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayStatusChangedEvent(PayStatusChangedEvent payStatusChangedEvent) {
        if (payStatusChangedEvent == null) {
            return;
        }
        if (payStatusChangedEvent.paySuccess) {
            finish();
        }
    }

    private void share(GoodsDetailInfo goodsDetailInfo) {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("分享到微信"));
//        commonDialogInfoList.add(new CommonDialogInfo("生成海报"));

        new CommonBottomDialog()
                .init(this)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 0) {

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapUtil.getBitmap(goodsDetailInfo.thumbnail_img);
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constants.OPT_DATA, goodsDetailInfo);
                                message.setData(bundle);
                                message.obj = bitmap;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    } else {
                        new GoodsPosterDialog().show(context, new GoodsDetailInfo());
                    }
                })
                .show();
    }

    private void showPayTypeBottomDialog() {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("微信支付"));
        commonDialogInfoList.add(new CommonDialogInfo("支付宝支付"));

        new CommonBottomDialog()
                .init(this)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 0) {
                        mPayType = ConfirmPayActivity.PAY_BY_WECHAT;
                        mPayViewModel.fVipPay(PayViewModel.GET_PAY_INFO, ConfirmPayActivity.PAY_BY_WECHAT);
                    } else {
                        mPayType = ConfirmPayActivity.PAY_BY_ALI_APP;
                        mPayViewModel.fVipPay(PayViewModel.GET_PAY_INFO, ConfirmPayActivity.PAY_BY_ALI_APP);
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onRefreshData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            binding.emptyLayout.showEmpty("暂无商品", R.drawable.image_nogoods);
            return;
        }
        List<GoodsDetailInfo> list = (List<GoodsDetailInfo>) object;
        if (ContainerUtil.isEmpty(list)) {
            binding.emptyLayout.showEmpty("暂无商品", R.drawable.image_nogoods);
            return;
        }
        mGoodsListAdapter.setEnableLoadMore(true);
        mGoodsListAdapter.setNewData(list);
        binding.emptyLayout.showSuccess();
    }

    @Override
    public void onLoadMoreData(Object object) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (object == null) {
            return;
        }
        List<GoodsDetailInfo> list = (List<GoodsDetailInfo>) object;
        if (ContainerUtil.isEmpty(list)) {
            mGoodsListAdapter.loadMoreComplete();
            mGoodsListAdapter.setEnableLoadMore(false);
            addFootView();
            return;
        }
        mGoodsListAdapter.loadMoreComplete();
        mGoodsListAdapter.addData(list);
    }

    @Override
    public void onNetError() {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.emptyLayout.showEmpty("没有网络", R.drawable.image_servererror);
        binding.emptyLayout.showError();
    }

    @Override
    public LoadingDialog createLoadingDialog() {
        return null;
    }

    @Override
    public LoadingDialog createLoadingDialog(String text) {
        return null;
    }

    @Override
    public boolean visibility() {
        return false;
    }

    @Override
    public boolean viewFinished() {
        return false;
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            GoodsDetailInfo goodsDetailInfo = (GoodsDetailInfo) bundle.getSerializable(Constants.OPT_DATA);
            Bitmap bitmap = (Bitmap) message.obj;
            new ShareUtils()
                    .init(context)
                    .shareMiniProgram(goodsDetailInfo.sarti_name, goodsDetailInfo.sarti_desc,
                            bitmap, goodsDetailInfo.getSharePath());
        }
    };

    private void addFootView() {

        View footView = getLayoutInflater().inflate(R.layout.layout_recyclerview_nomore_data, null);
        footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(context, 45)));
        mGoodsListAdapter.setFooterView(footView);
    }

    @Override
    public void returnOrderBenefit(OrderBenefitResponse orderBenefitResponse) {
        if (orderBenefitResponse == null) {
            return;
        }
        mOrderDetailViewModel.getOrderDetailInfo(context, orderBenefitResponse.salebill_id);
    }

    @Override
    public void returnOrderPayInfo(int optType, OrderPayInfoResponse orderPayInfoResponse) {

    }

    @Override
    public void returnOrderStatus(OrderStatusResponse orderStatusResponse) {
        GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
        ConfirmPayActivity.start(context, goodsDetailInfo);
    }

    @Override
    public void returnFVipPay(int optType, OrderPayInfoResponse orderPayInfoResponse) {
        if (PayViewModel.CHECK_ORDER_STATUS == optType) {
            if (orderPayInfoResponse == null) {
                ToastUtil.showShortToast("支付成功");
                PayCompleteActivity.start(context, mSalebillId);
            } else {
                return;
            }
            return;
        }
        if (orderPayInfoResponse == null) {
            return;
        }
        if (TextUtils.equals(mPayType, ConfirmPayActivity.PAY_BY_WECHAT)) {
            String path = "/pages/order/appPay?" + "salebill_id="
                    + "&mem_phone=" + mUserInfo.mem_phone + "&benefits_level=" + mUserInfo.benefits_level;
            new ShareUtils()
                    .init(context)
                    .jump2WxMiniProgram(path);
        } else {
            PayUtils payUtils = new PayUtils();
            payUtils.registerApp(context);
            payUtils.setOnPayListener(new OnPayListener() {
                @Override
                public void onPaySuccess() {

                }

                @Override
                public void onPayFailed() {
                    ToastUtil.showShortToast("支付失败");
                }
            });
            payUtils.startPay(GoodsListActivity.this, mPayType, CommandTools.beanToJson(orderPayInfoResponse));
        }
    }

    @Override
    public void returnOrderDetailInfo(GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            return;
        }
        if (goodsDetailInfo.order_product != null) {
            goodsDetailInfo.sarti_name = goodsDetailInfo.order_product.sarti_name;
            goodsDetailInfo.sarti_saleprice = goodsDetailInfo.order_product.sarti_saleprice;
            goodsDetailInfo.pay_type = goodsDetailInfo.order_product.pay_type;
            goodsDetailInfo.sarti_point_price = goodsDetailInfo.order_product.sum_point;
        }
        ConfirmPayActivity.start(context, goodsDetailInfo);
    }

    @Override
    public void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse) {

    }

    @Override
    public void returnCancelOrder() {

    }
}