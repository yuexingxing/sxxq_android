package com.sanshao.livemodule.zhibo.live;

import android.os.Bundle;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.FragmentLayoutAnchorWorksBinding;

/**
 * 主播作品
 *
 * @Author yuexingxing
 * @time 2020/9/10
 */
public class AnchorWorksFragment extends BaseFragment<BaseViewModel, FragmentLayoutAnchorWorksBinding> {

    public static AnchorWorksFragment newInstance() {
        AnchorWorksFragment fragment = new AnchorWorksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_anchor_works;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void loadData() {
    }

    @Override
    public void initData() {

    }

}