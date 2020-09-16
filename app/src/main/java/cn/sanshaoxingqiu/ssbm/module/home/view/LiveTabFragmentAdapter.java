package cn.sanshaoxingqiu.ssbm.module.home.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * Title:
 * Description:
 * <p>
 * Created by pei
 * Date: 2018/12/12
 */
public class LiveTabFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private Context mContext;
    private List<Fragment> mFragments;

    public LiveTabFragmentAdapter(List<Fragment> fragments, String[] titles, FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item_layout_home_live, null);
        TextView textView=view.findViewById(R.id.tv_title);
        textView.setText(mTitles[position]);
        return view;
    }
}