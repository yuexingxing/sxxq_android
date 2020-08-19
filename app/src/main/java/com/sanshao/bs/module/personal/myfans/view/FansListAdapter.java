package com.sanshao.bs.module.personal.myfans.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sanshao.bs.R;
import com.sanshao.bs.module.invitation.bean.UserReferrals;
import com.sanshao.bs.util.GlideUtil;

public class FansListAdapter extends BaseQuickAdapter<UserReferrals.UserReferralsItem, BaseViewHolder> {

    FansListAdapter(){
        super(R.layout.item_myfans, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserReferrals.UserReferralsItem item) {
        // 头像
        if (!TextUtils.isEmpty(item.avatar)) {
            RoundedImageView userAvatar = helper.getView(R.id.iv_user_avatar);
            GlideUtil.loadImage(item.avatar, userAvatar, R.drawable.image_placeholder_two);
        }
        // 名称
        helper.setText(R.id.tv_user_name, item.nickname);
        // 星级
        ImageView vipIcon = helper.getView(R.id.iv_vip_icon);
        TextView vipName = helper.getView(R.id.tv_vip_name);
        if (TextUtils.equals("1", item.mem_class_key)) {
            vipIcon.setImageResource(R.drawable.icon_universaldrillmembers);
        } else if (TextUtils.equals("2", item.mem_class_key)) {
            vipIcon.setImageResource(R.drawable.icon_universaldrillmembers);
        } else if (TextUtils.equals("3", item.mem_class_key)) {
            vipIcon.setImageResource(R.drawable.icon_universaldrillmembers);
        } else {
            vipIcon.setImageResource(R.drawable.icon_commondiamond);
        }
        vipName.setText(item.mem_class_name);
        // 手机
        String phone = item.mem_phone;
        if (!TextUtils.isEmpty(phone) && phone.length() >= 11) {
            phone = phone.substring(0, 4) + "****" + phone.substring(8);
        }
        helper.setText(R.id.tv_user_phone, phone);

        // 到底提示
        helper.getView(R.id.ll_bottom_line).setVisibility(View.GONE);
        if (getItemCount() > 7 && helper.getAdapterPosition() == getData().size() - 1) {
            helper.getView(R.id.ll_bottom_line).setVisibility(View.VISIBLE);
        }
    }
}
