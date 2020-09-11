package com.sanshao.livemodule.zhibo.live;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.FragmentLayoutAnchorWorksBinding;
import com.sanshao.livemodule.zhibo.main.videolist.utils.TCVideoInfo;

/**
 * 主播作品
 *
 * @Author yuexingxing
 * @time 2020/9/10
 */
public class AnchorWorksFragment extends BaseFragment<BaseViewModel, FragmentLayoutAnchorWorksBinding> {

    private AnchorWorksAdapter mAnchorWorksAdapter;

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
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void initData() {

        mAnchorWorksAdapter = new AnchorWorksAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(mAnchorWorksAdapter);
        binding.recyclerView.setNestedScrollingEnabled(true);
        binding.recyclerView.setHasFixedSize(true);

        String picUrl = "http://img.cyw.com/shopx/20130606155913125664/shopinfo/201605041441522.jpg";
        for (int i = 0; i < 10; i++) {
            TCVideoInfo tcVideoInfo = new TCVideoInfo();
            tcVideoInfo.title = i + "";
            tcVideoInfo.frontCover = picUrl;
            mAnchorWorksAdapter.addData(tcVideoInfo);
        }
    }

}