package cn.sanshaoxingqiu.ssbm.module.invitation.view;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.util.GlideUtil;

public class InvitationListAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {

    InvitationListAdapter() {
        super(R.layout.item_invitaion_record, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        // 头像
        if (!TextUtils.isEmpty(item.avatar)) {
            RoundedImageView userAvatar = helper.getView(R.id.iv_user_avatar);
            GlideUtil.loadImage(item.avatar, userAvatar, R.drawable.image_placeholder_two);
        }
        // 名称
        helper.setText(R.id.tv_user_name, item.nickname);
        helper.setText(R.id.tv_time, item.nickname);
    }
}
