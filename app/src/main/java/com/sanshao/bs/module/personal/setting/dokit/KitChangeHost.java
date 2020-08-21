package com.sanshao.bs.module.personal.setting.dokit;

import android.content.Context;

import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.sanshao.bs.R;
import com.sanshao.bs.module.personal.setting.view.ChangeHostActivity;

/**
 * 切换环境
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class KitChangeHost implements IKit {
    @Override
    public int getCategory() {
        return Category.BIZ;
    }

    @Override
    public int getName() {
        return R.string.change_host;
    }

    @Override
    public int getIcon() {
        return R.drawable.image_logo_smal;
    }

    @Override
    public void onClick(Context context) {
        ChangeHostActivity.start(context);
    }

    @Override
    public void onAppInit(Context context) {

    }
}
