package cn.sanshaoxingqiu.ssbm.module.personal.income.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeMenuInfo;

import java.util.List;

/**
 * 进账菜单
 *
 * @Author yuexingxing
 * @time 2020/7/13
 */
public class IncomeTabFragmentAdapter extends FragmentPagerAdapter {

    private List<IncomeMenuInfo> mIncomeMenuInfoList;
    private Context mContext;
    private List<Fragment> mFragments;

    public IncomeTabFragmentAdapter(FragmentManager fm,List<Fragment> fragments, List<IncomeMenuInfo> incomeMenuInfoList, Context context) {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
        this.mIncomeMenuInfoList = incomeMenuInfoList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mIncomeMenuInfoList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mIncomeMenuInfoList.get(position).tilte;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_income_menu_bottom, null);
        IncomeMenuInfo incomeMenuInfo = mIncomeMenuInfoList.get(position);
        ImageView imgIcon  = view.findViewById(R.id.iv_icon);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        imgIcon.setBackgroundResource(incomeMenuInfo.iconUnSelect);
        tvTitle.setText(incomeMenuInfo.tilte);
        return view;
    }
}