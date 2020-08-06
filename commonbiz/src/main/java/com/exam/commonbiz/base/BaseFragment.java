package com.exam.commonbiz.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.exam.commonbiz.R;
import com.exam.commonbiz.util.LoadingDialogUtil;
import com.exam.commonbiz.util.StatusBarUtil;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

/**
 * @Author yuexingxing
 * @time 2020/6/30
 */
public abstract class BaseFragment<VM extends ViewModel, VDB extends ViewDataBinding> extends Fragment implements BaseViewCallBack {

    public final String TAG = BaseFragment.class.getSimpleName();

    public Context context;
    protected VM mViewModel;
    protected VDB binding;
    //是否可见
    protected boolean isVisiable;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        binding.setLifecycleOwner(this);
        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(vmClass);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStatusBar();
        initData();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
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
                StatusBarUtil.transparencyBar(getActivity());
            } else {
                StatusBarUtil.setStatusBarColor(getActivity(), getStatusBarColor());
            }

            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(getActivity(), true, isUseFullScreenMode());
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
    protected int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public LoadingDialog createLoadingDialog() {
        return LoadingDialogUtil.createLoadingDialog(getActivity());
    }

    @Override
    public LoadingDialog createLoadingDialog(String text) {
        return LoadingDialogUtil.createLoadingDialog(getActivity(), text);
    }

    @Override
    public boolean visibility() {
        return isVisible();
    }

    @Override
    public boolean viewFinished() {
        return isDetached() || getActivity().isFinishing();
    }

    /**
     * 实现Fragment数据的缓加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisiable = true;
            onVisible();
        } else {
            isVisiable = false;
            onInVisible();
        }
    }

    protected void onInVisible() {
    }

    protected void onVisible() {
        //加载数据
        loadData();
    }

    protected abstract void loadData();
}
