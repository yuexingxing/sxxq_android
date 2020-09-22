package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.exam.commonbiz.base.BaseWebViewActivity;
import com.exam.commonbiz.bean.WebViewBaseInfo;
import com.exam.commonbiz.util.BitmapUtil;
import com.exam.commonbiz.util.ToastUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;

public class ExerciseActivity extends BaseWebViewActivity {

    private GoodsDetailInfo mGoodsDetailInfo;

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, ExerciseActivity.class);
        starter.putExtra(Constants.OPT_DATA, url);
        context.startActivity(starter);
    }

    @Override
    public void initData() {

        String url = getIntent().getStringExtra(Constants.OPT_DATA);
        initWebView(url);

        getWebView().loadUrl("file:///android_asset/ExampleApp.html");
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise;
    }

    @Override
    protected void initView() {

    }

    /**
     * 分享
     */
    private void share() {
        if (mGoodsDetailInfo == null || TextUtils.isEmpty(mGoodsDetailInfo.sarti_id)) {
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