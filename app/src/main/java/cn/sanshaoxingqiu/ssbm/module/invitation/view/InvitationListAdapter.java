package cn.sanshaoxingqiu.ssbm.module.invitation.view;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import cn.sanshaoxingqiu.ssbm.R;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.GlideUtil;

public class InvitationListAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {

    InvitationListAdapter() {
        super(R.layout.item_invitaion_record, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {

        // 头像
        if (!TextUtils.isEmpty(item.avatar)) {
            RoundedImageView userAvatar = helper.getView(R.id.iv_user_avatar);
            GlideUtil.loadImage(item.avatar, userAvatar, R.drawable.image_graphofbooth_avatar);
        }
        // 名称
        helper.setText(R.id.tv_user_name, item.nickname);
        helper.setText(R.id.tv_time, item.mem_class_start_date);
        if (TextUtils.equals(item.point_status, "APPLY")) {
            helper.setText(R.id.tv_status, "已使用");
        } else if (TextUtils.equals(item.point_status, "DISABLE")) {
            helper.setText(R.id.tv_status, "未激活");
        } else {
            helper.setText(R.id.tv_status, "已激活");
        }
    }
}
