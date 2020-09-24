package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.exam.commonbiz.base.BaseWebViewActivity;
import com.exam.commonbiz.bean.WebViewBaseInfo;
import com.exam.commonbiz.dialog.CommonTipDialog;
import com.exam.commonbiz.util.BitmapUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.order.view.OrderListActivity;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;

/**
 * 活动h5
 *
 * @Author yuexingxing
 * @time 2020/9/21
 */
public class ExerciseActivity extends BaseWebViewActivity {

    private GoodsDetailInfo mGoodsDetailInfo;
    private String mTitle;
    private String mUrl;

    public static void start(Context context, String title, String url) {
        Intent starter = new Intent(context, ExerciseActivity.class);
        starter.putExtra(Constants.OPT_DATA, title);
        starter.putExtra(Constants.OPT_DATA2, url);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mUrl)) {
            initWebView(mUrl);
        }
    }

    @Override
    public void initData() {

        mTitle = getIntent().getStringExtra(Constants.OPT_DATA);
        mUrl = getIntent().getStringExtra(Constants.OPT_DATA2);
        mTitleBar.setTitle(mTitle);
//        url = "file:///android_asset/ExampleApp.html";//ExampleApp
//        initWebView(mUrl);
        registerHandler();
    }

    private void registerHandler() {

        //app登陆
        mWebView.registerHandler("loginFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据:" + data;
                Log.i(TAG, "handler = loginFunction, data from web = " + data);
                function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));
                LoginActivity.start(ExerciseActivity.this, LoginActivity.FROM_PAGE_EXERCISE);
            }
        });
        // app弹窗
        mWebView.registerHandler("alertFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = alertFunction, data from web = " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    CommonTipDialog commonTipDialog = new CommonTipDialog(ExerciseActivity.this);
                    commonTipDialog
                            .setTitle(jsonObject.optString("title"))
                            .setContent(jsonObject.optString("content"))
                            .setLeftButton("取消")
                            .setOnLeftButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                }
                            })
                            .setRightButton(jsonObject.optString("otherButtonTitle"))
                            .setOnRightButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                    function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));
                                }
                            })
                            .showBottomLine(View.VISIBLE)
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // 跳转到产品页
        mWebView.registerHandler("productDetailFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据:" + data;
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));
            }
        });
        // 分享微信小程序
        mWebView.registerHandler("shareProductFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据:" + data;
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));

                try {
                    mGoodsDetailInfo = new GoodsDetailInfo();
                    JSONObject jsonObject = new JSONObject(data);
                    mGoodsDetailInfo.thumbnail_img = jsonObject.optString("thumbnail_img");
                    mGoodsDetailInfo.sarti_id = jsonObject.optString("sarti_id");
                    mGoodsDetailInfo.sarti_name = jsonObject.optString("sarti_name");
                    share();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // 分享微信小程序，分享链接
        mWebView.registerHandler("shareToMiniAppFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据:" + data;
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));

                try {
                    mGoodsDetailInfo = new GoodsDetailInfo();
                    JSONObject jsonObject = new JSONObject(data);
                    mGoodsDetailInfo.thumbnail_img = jsonObject.optString("thumbnail_img");
                    mGoodsDetailInfo.share_url = jsonObject.optString("shared_URL");
                    mGoodsDetailInfo.sarti_name = jsonObject.optString("title");
                    share();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // 跳转到产品页
        mWebView.registerHandler("productDetailFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据:" + data;
                Log.i(TAG, "handler = productDetailFunction, data from web = " + data);
                function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    GoodsDetailActivity.start(ExerciseActivity.this, jsonObject.optString("sarti_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // 跳转到订单列表
        mWebView.registerHandler("orderFunction", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                String str = "这是html返回给java的数据:" + data;
                Log.i(TAG, "handler = orderFunction, data from web = " + data);
                function.onCallBack(CommandTools.beanToJson(new WebViewBaseInfo()));
                OrderListActivity.start(ExerciseActivity.this, OrderInfo.State.ALL);
            }
        });
    }

    /**
     * 分享
     */
    private void share() {
        if (mGoodsDetailInfo == null) {
            return;
        }

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("分享到微信"));

        new CommonBottomDialog()
                .init(this)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 0) {

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapUtil.getBitmap(mGoodsDetailInfo.thumbnail_img);
                                Message message = new Message();
                                message.obj = bitmap;
                                mHandler.sendMessage(message);
                            }
                        }).start();
                    } else {
                        new GoodsPosterDialog().show(ExerciseActivity.this, new GoodsDetailInfo());
                    }
                })
                .show();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            new ShareUtils()
                    .init(ExerciseActivity.this)
                    .shareMiniProgram(mGoodsDetailInfo.sarti_name, mGoodsDetailInfo.sarti_desc,
                            (Bitmap) message.obj, mGoodsDetailInfo.getSharePath());
        }
    };
}