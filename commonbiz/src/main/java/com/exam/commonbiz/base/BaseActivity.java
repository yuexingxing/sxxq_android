package com.exam.commonbiz.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.exam.commonbiz.R;
import com.exam.commonbiz.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

/**
 * @Author yuexingxing
 * @time 2020/6/30
 */
public abstract class BaseActivity<VM extends ViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {
    public final String TAG = BaseActivity.class.getSimpleName();
    public Context context;
    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = new ViewModelProvider(this).get(vmClass);
        context = this;
        setStatusBar();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            back();
            return true;
        }
        //继续执行父类其他点击事件
        return super.onKeyUp(keyCode, event);
    }

    public void back() {
        finish();
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean useEventBus() {
        return false;
    }

    public abstract void initData();

    protected abstract int getLayoutId();

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, getStatusBarColor());
            }

            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode());
            }
        }
    }

    /**
     * 是否设置成透明状态栏，即就是全屏模式
     */
    protected boolean isUseFullScreenMode() {
        return false;
    }

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     */
    public int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }
    }
}
