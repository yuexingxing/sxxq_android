package cn.sanshaoxingqiu.ssbm.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.util.CommonCallBack;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.MainActivity;
import cn.sanshaoxingqiu.ssbm.module.splash.dialog.BenefitPolicyDialog;

/**
 * 启动页
 *
 * @Author yuexingxing
 * @time 2020/7/21
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (checkShowPolicy()) {
            countDownTimer.start();
        } else {
            new BenefitPolicyDialog().show(this, new CommonCallBack() {
                @Override
                public void callback(int postion, Object object) {
                    if (postion == 0) {
                        finish();
                    } else {
                        ACache.get(LaunchActivity.this).put(ConfigSP.SP_PERSONAL_POLICY, "");
                        countDownTimer.start();
                    }
                }
            });
        }
    }

    /**
     * 是否弹出隐私政策弹窗
     * @return
     */
    private boolean checkShowPolicy() {
        if (ACache.get(this).getAsString(ConfigSP.SP_PERSONAL_POLICY) == null) {
            return false;
        }
        return true;
    }

    private CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            jump();
        }
    };

    private void jump() {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}