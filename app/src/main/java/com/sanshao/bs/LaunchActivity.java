package com.sanshao.bs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        SplashActivity.start(this);
    }
}