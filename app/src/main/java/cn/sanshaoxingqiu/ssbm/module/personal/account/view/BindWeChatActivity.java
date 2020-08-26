package cn.sanshaoxingqiu.ssbm.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.log.XLog;
import com.exam.commonbiz.util.CommonCallBack;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityBindWeChatBinding;
import cn.sanshaoxingqiu.ssbm.module.login.bean.AuthInfo;
import cn.sanshaoxingqiu.ssbm.util.UMSocialUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 绑定微信
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class BindWeChatActivity extends BaseActivity<BaseViewModel, ActivityBindWeChatBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, BindWeChatActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_we_chat;
    }

    @Override
    public void initData() {

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

        binding.tvBind.setOnClickListener(view -> {
            UMSocialUtil.authorization(context, SHARE_MEDIA.WEIXIN, new CommonCallBack() {
                @Override
                public void callback(int postion, Object object) {
                    if (postion == 0 && object != null){
                        AuthInfo authInfo = (AuthInfo) object;
                        XLog.d(TAG, authInfo.openid);
                    }
                }
            });
        });
    }
}