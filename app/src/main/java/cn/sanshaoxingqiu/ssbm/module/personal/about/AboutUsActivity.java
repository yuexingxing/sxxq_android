package cn.sanshaoxingqiu.ssbm.module.personal.about;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.dialog.CommonTipDialog;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityAboutUsBinding;
import cn.sanshaoxingqiu.ssbm.module.EmptyWebViewActivity;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.about.adapter.AboutUsAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.ExerciseActivity;

/**
 * 账户安全
 *
 * @Author yuexingxing
 * @time 2020/7/3
 */
public class AboutUsActivity extends BaseActivity<AboutUsViewModel, ActivityAboutUsBinding> {

    private AboutUsAdapter mAboutUsAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutUsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
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

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommonTipDialog commonTipDialog = new CommonTipDialog(context);
                commonTipDialog.setTitle("提示")
                        .setContent("确认退出登录？")
                        .setLeftButton("取消")
                        .setOnLeftButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                            }
                        })
                        .setRightButton("确认")
                        .setOnRightButtonClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonTipDialog.dismiss();
                                TCUserMgr.getInstance().logout();
                                BasicApplication.app.logout();
                                LoginActivity.start(context);
                            }
                        })
                        .showBottomLine(View.VISIBLE)
                        .show();
            }
        });

        mAboutUsAdapter = new AboutUsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAboutUsAdapter);
        mAboutUsAdapter.setOnItemClickListener(new AboutUsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AgreementInfo agreementInfo) {
                EmptyWebViewActivity.start(context, agreementInfo.url);
            }
        });

        mAboutUsAdapter.addData(new AgreementInfo("三少变美直播服务协议", "https://h5.sanshaoxingqiu.cn/agree/live.html"));
        mAboutUsAdapter.addData(new AgreementInfo("三少变美用户服务协议", "https://h5.sanshaoxingqiu.cn/agree/service.html"));
        mAboutUsAdapter.addData(new AgreementInfo("三少变美隐私协议", "https://h5.sanshaoxingqiu.cn/agree/clause.html"));
        mAboutUsAdapter.addData(new AgreementInfo("三少变美提现协议", "https://h5.sanshaoxingqiu.cn/agree/withdrawalrules.html"));
        mAboutUsAdapter.addData(new AgreementInfo("三少变美用户注销协议", "https://h5.sanshaoxingqiu.cn/agree/cancellation.html"));
    }


}