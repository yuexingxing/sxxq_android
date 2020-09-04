package com.sanshao.livemodule.zhibo.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.liveroom.roomutil.misc.NameGenerator;
import com.sanshao.livemodule.net.LiveRetrofit;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.sanshao.livemodule.zhibo.common.net.TCHTTPMgr;
import com.sanshao.livemodule.zhibo.common.utils.TCUtils;
import com.sanshao.livemodule.zhibo.main.TCMainActivity;

import org.json.JSONObject;

/**
 * Module:   TCLoginActivity
 * <p>
 * Function: 用于登录小直播的页面
 * <p>
 * 1. 未登陆过，输入账号密码登录
 * <p>
 * 2. 已经登陆过，小直播获取读取缓存，并且自动登录。 详见{@link TCUserMgr}
 */
public class TCLoginActivity extends Activity {
    private static final String TAG = TCLoginActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_login);

        etPassword = findViewById(R.id.et_password);
        etUsername = findViewById(R.id.et_username);
        tvRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressbar);

        userNameLoginViewInit();

        //检测是否存在缓存
        checkLogin();
    }

    /**
     * 用户名密码登录界面init
     */
    public void userNameLoginViewInit() {
        etUsername.setText("");
        etUsername.setError(null, null);

        etPassword.setText("");
        etPassword.setError(null, null);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册界面 phoneView 与 normalView跳转逻辑一致
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), TCRegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用normal登录逻辑
                showOnLoading(true);

                attemptNormalLogin(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    /**
     * trigger loading模式
     *
     * @param active
     */
    private void showOnLoading(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            etUsername.setEnabled(false);
            etPassword.setEnabled(false);
            tvRegister.setClickable(false);
        } else {
            progressBar.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            etUsername.setEnabled(true);
            etPassword.setEnabled(true);
            tvRegister.setClickable(true);
            tvRegister.setTextColor(getResources().getColor(R.color.colorTransparentGray));
        }

    }

    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOnLoadingInUIThread(final boolean active) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showOnLoading(active);
            }
        });
    }

    private void showLoginError(String errorString) {
        etUsername.setError(errorString);
        showOnLoading(false);
    }

    private void showPasswordError(String errorString) {
        etPassword.setError(errorString);
        showOnLoading(false);
    }

    /**
     * 登录成功后被调用，跳转至TCMainActivity
     */
    private void jumpToHomeActivity() {
        Intent intent = new Intent(this, TCMainActivity.class);
        startActivity(intent);
        finish();
    }

    private void login(String username, String password) {
        final TCUserMgr tcLoginMgr = TCUserMgr.getInstance();
//        LiveRetrofit.login(username, password, new OnLoadListener() {
//            @Override
//            public void onLoadStart() {
//
//            }
//
//            @Override
//            public void onLoadCompleted() {
//
//            }
//
//            @Override
//            public void onLoadSucessed(BaseResponse t) {
//
//            }
//
//            @Override
//            public void onLoadFailed(String errMsg) {
//                Toast.makeText(TCLoginActivity.this, errMsg, Toast.LENGTH_SHORT).show();
//            }
//        });
        tcLoginMgr.login(username, password, new TCHTTPMgr.Callback() {
            @Override
            public void onSuccess(JSONObject data) {
                showToast("登录成功");
                jumpToHomeActivity();
            }

            @Override
            public void onFailure(int code, final String msg) {
                showToast(msg);
                showOnLoadingInUIThread(false);
            }
        });
    }

    private void checkLogin() {
        if (TCUtils.isNetworkAvailable(this)) {
            //返回true表示存在本地缓存，进行登录操作，显示loadingFragment
            if (TCUserMgr.getInstance().hasUser()) {
                showOnLoadingInUIThread(true);
                TCUserMgr.getInstance().autoLogin(new TCHTTPMgr.Callback() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        jumpToHomeActivity();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showToast("自动登录失败");
                        showOnLoadingInUIThread(false);
                    }
                });
            } else if (TextUtils.isEmpty(TCGlobalConfig.APP_SVR_URL)) {
                showOnLoading(true);

                if (TCUtils.isNetworkAvailable(this)) {
                    login(NameGenerator.getRandomUserID(), "");
                }
            }
        }
    }

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public void attemptNormalLogin(String username, String password) {
        if (TCUtils.isUsernameVaild(username)) {
            if (TCUtils.isPasswordValid(password)) {
                if (TCUtils.isNetworkAvailable(this)) {
                    login(username, password);
                } else {
                    Toast.makeText(getApplicationContext(), "当前无网络连接", Toast.LENGTH_SHORT).show();
                }
            } else {
                showPasswordError("密码长度应为8-16位");
            }
        } else {
            showLoginError("用户名不符合规范");
        }
    }

}
