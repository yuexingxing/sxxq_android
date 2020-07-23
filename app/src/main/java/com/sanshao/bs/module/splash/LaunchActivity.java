package com.sanshao.bs.module.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sanshao.bs.R;

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

        Intent starter = new Intent(this, SplashActivity.class);
        startActivity(starter);
        finish();
    }
}