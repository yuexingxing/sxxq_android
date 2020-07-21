package com.sanshao.bs.module.personal.view;

import android.graphics.Bitmap;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.PersonalFragmentBinding;
import com.sanshao.bs.module.TestMenuActivity;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.view.OrderListActivity;
import com.sanshao.bs.module.personal.adapter.PersonalOrderSubjectAdapter;
import com.sanshao.bs.module.personal.income.view.IncomeMenuActivity;
import com.sanshao.bs.module.personal.personaldata.view.PersonalDetailActivity;
import com.sanshao.bs.module.personal.setting.view.SettingActivity;
import com.sanshao.bs.module.personal.viewmodel.PersonalViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class PersonalFragment extends BaseFragment<PersonalViewModel, PersonalFragmentBinding> {

    private List<OrderInfo> mOrderInfoList = new ArrayList<>();
    private PersonalOrderSubjectAdapter mPersonalOrderSubjectAdapter;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.personal_fragment;
    }

    @Override
    public void initData() {

        binding.titleBar.getLeftView().setVisibility(View.INVISIBLE);
        binding.btnTest.setOnClickListener(v -> TestMenuActivity.start(getContext()));
        binding.llPersonal.setOnClickListener(v -> PersonalDetailActivity.start(getContext()));
        binding.includeOrder.rlAllOrder.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ALL));
        binding.includeOrder.llOrderTobepaid.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBePaid));
        binding.includeOrder.llOrderTobeuse.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBeUse));
        binding.includeOrder.llOrderTobesure.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.ToBePaid));
        binding.includeOrder.llOrderComplete.setOnClickListener(v -> OrderListActivity.start(context, OrderInfo.State.Complete));
        binding.pavIncome.setOnClickListener(v -> IncomeMenuActivity.start(context));
        binding.pavSetting.setOnClickListener(v -> SettingActivity.start(context));
        initOrderList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bitmap bitmap = ACache.get(context).getAsBitmap(ConfigSP.UserInfo.AVATAR);
        if (bitmap != null) {
            binding.ivAvatar.setImageBitmap(bitmap);
        }
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.transparent;
    }


    private void initOrderList() {

        mPersonalOrderSubjectAdapter = new PersonalOrderSubjectAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.includeOrder.recyclerView.setLayoutManager(linearLayoutManager);
        binding.includeOrder.recyclerView.setAdapter(mPersonalOrderSubjectAdapter);
        binding.includeOrder.recyclerView.setNestedScrollingEnabled(false);
        binding.includeOrder.recyclerView.setFocusable(false);

        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = i + "黄金微针你的美容必备，美容必备…";
            mOrderInfoList.add(orderInfo);
        }

        binding.includeOrder.rlOpen.setOnClickListener(v -> {
            binding.includeOrder.rlOpen.setVisibility(View.GONE);
            mPersonalOrderSubjectAdapter.getData().clear();
            mPersonalOrderSubjectAdapter.addData(mOrderInfoList);
        });
        mPersonalOrderSubjectAdapter.addData(mOrderInfoList.get(0));
    }
}