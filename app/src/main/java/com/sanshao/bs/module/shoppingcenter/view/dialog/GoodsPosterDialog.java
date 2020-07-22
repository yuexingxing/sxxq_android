package com.sanshao.bs.module.shoppingcenter.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.util.FileUtil;
import com.exam.commonbiz.util.QRCodeUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.util.BitmapUtil;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;

/**
 * 商品海报
 *
 * @Author yuexingxing
 * @time 2020/7/3
 */
public class GoodsPosterDialog {

    @SuppressLint("CheckResult")
    public void show(Context context) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_goods_poster, null);
        Dialog dialog = new Dialog(context, R.style.dialogSupply);
        dialog.setContentView(rootView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        RoundedImageView imgAvatar = rootView.findViewById(R.id.iv_avatar);
        ImageView imgIcon = rootView.findViewById(R.id.iv_icon);
        ImageView imgQrcode = rootView.findViewById(R.id.iv_qrcode);
        LinearLayout llContent = rootView.findViewById(R.id.ll_content);

        String userId = SSApplication.getInstance().getUserInfo().nickName;
        if (TextUtils.isEmpty(userId)) {
            userId = "sanshao";
        }
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(userId, 100, 100);
        imgQrcode.setImageBitmap(bitmap);

        Bitmap bitmapAvatar = ACache.get(context).getAsBitmap(ConfigSP.UserInfo.AVATAR);
        imgAvatar.setImageBitmap(bitmapAvatar);

        Glide.with(SSApplication.app).load(Constants.DEFAULT_IMG_URL).into(imgIcon);
        rootView.findViewById(R.id.iv_close).setOnClickListener(view -> dialog.dismiss());
        rootView.findViewById(R.id.tv_save).setOnClickListener(v -> {

            RxPermissions rxPermissions = new RxPermissions((Activity) context);
            rxPermissions.request(PermissionGroup.STORAGE)
                    .subscribe(aBoolean -> {
                        if (!aBoolean) {
                            return;
                        }
                        String picName = System.currentTimeMillis() + ".png";
                        Bitmap bitmap2 = BitmapUtil.viewConversionBitmap(llContent);
                        boolean saveSuccess = FileUtil.saveImageToGallery(context, bitmap2, picName);
                        if (saveSuccess) {
                            ToastUtil.showShortToast("图片保存成功");
                        }
                    });
        });
    }
}
