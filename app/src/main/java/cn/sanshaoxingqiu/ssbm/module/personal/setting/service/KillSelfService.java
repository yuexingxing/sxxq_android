package cn.sanshaoxingqiu.ssbm.module.personal.setting.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * 关闭应用后多久重新启动
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class KillSelfService extends Service {
    private static long stopDelayed = 50;
    private Handler handler;
    private String PackageName;

    public KillSelfService() {
        handler = new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        stopDelayed = intent.getLongExtra("Delayed", 50);
        PackageName = intent.getStringExtra("PackageName");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
                startActivity(LaunchIntent);
                KillSelfService.this.stopSelf();
            }
        }, stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

