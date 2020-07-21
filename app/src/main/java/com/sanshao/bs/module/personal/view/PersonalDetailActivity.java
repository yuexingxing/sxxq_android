package com.sanshao.bs.module.personal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.QRCodeUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityPersonalDetailBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.view.dialog.ChangeAvatarDialog;
import com.sanshao.bs.module.personal.view.dialog.SelectBirthdayDialog;
import com.sanshao.bs.module.personal.view.dialog.SelectSexDialog;
import com.sanshao.bs.module.personal.viewmodel.PersonalDetailViewModel;
import com.sanshao.bs.util.BitmapUtil;
import com.sanshao.bs.util.FileUtil;
import com.sanshao.bs.util.ToastUtil;

import java.io.File;


/**
 * 个人设置
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class PersonalDetailActivity extends BaseActivity<PersonalDetailViewModel, ActivityPersonalDetailBinding> {

    private final static int REQUEST_IMAGE_GET = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private String mCurrentPhotoPath;
    private SelectBirthdayDialog mSelectBirthdayDialog;

    public static void start(Context context) {
        Intent starter = new Intent(context, PersonalDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        binding.lcvName.setContent(userInfo.name);
        binding.lcvNickName.setContent(userInfo.nickName);
        binding.lcvSex.setContent(userInfo.sexName);
        binding.lcvSignature.setContent(userInfo.signature);
        binding.lcvBirthday.setContent(userInfo.birthday);

        String content = "www.baidu.com";
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(content, ScreenUtil.dp2px(context, 38),
                ScreenUtil.dp2px(context, 38));
        binding.ivQrcode.setImageBitmap(bitmap);
    }

    @Override
    public void initData() {

        binding.setUser(SSApplication.getInstance().getUserInfo());
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        binding.llAvatar.setOnClickListener(v -> {
            new ChangeAvatarDialog().show(context, (postion, object) -> {
                if (postion == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        checkPermission(0);
                    } else {
                        selectFromCamera();
                    }
                } else if (postion == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        checkPermission(1);
                    } else {
                        selectFromAlbum();
                    }
                }
            });
        });
        Bitmap bitmap = ACache.get(context).getAsBitmap(ConfigSP.UserInfo.AVATAR);
        if (bitmap != null) {
            binding.ivAvatar.setImageBitmap(bitmap);
        }

        binding.lcvName.setOnClickListener(v -> SettingNameActivity.start(context, SettingNameActivity.MODIFY_NAME));
        binding.lcvNickName.setOnClickListener(v -> SettingNameActivity.start(context, SettingNameActivity.MODIFY_NICK_NAME));
        binding.llQrcode.setOnClickListener(v -> {
            RecommendCodeActivity.start(context);
        });
        binding.lcvSex.setOnClickListener(v -> {
            new SelectSexDialog().show(context, (postion, object) -> {
                UserInfo userInfo = SSApplication.getInstance().getUserInfo();
                userInfo.sex = postion;
                if (postion == 0) {
                    userInfo.sexName = "女";
                } else {
                    userInfo.sexName = "男";
                }
                SSApplication.getInstance().saveUserInfo(userInfo);
                binding.lcvSex.setContent(userInfo.sexName);
            });
        });
        binding.lcvSignature.setOnClickListener(v -> {
            PersonalSignatureActivity.start(context);
        });
        binding.lcvBirthday.setOnClickListener(v -> {
            if (mSelectBirthdayDialog == null) {
                mSelectBirthdayDialog = new SelectBirthdayDialog();
            }
            mSelectBirthdayDialog.setCommonCallBack(new CommonCallBack() {
                @Override
                public void callback(int postion, Object object) {
                    if (object == null) {
                        return;
                    }
                    String birthday = ((String) object).split(" ")[0];
                    UserInfo userInfo = SSApplication.getInstance().getUserInfo();
                    userInfo.birthday = birthday;
                    SSApplication.getInstance().saveUserInfo(userInfo);
                    binding.lcvBirthday.setContent(birthday);
                }
            });
            mSelectBirthdayDialog.showDateDialog(context, 3);
        });
    }

    public void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //判断系统中是否有处理该Intent的Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        } else {
            ToastUtil.showShortToast("未找到图片查看器");
        }
    }

    private void selectFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断系统中是否有处理该Intent的Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            // 创建文件来保存拍的照片
            File photoFile = FileUtil.createImageFile();
            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            ToastUtil.showShortToast("无法启动相机");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 回调成功
        if (resultCode == RESULT_OK) {
            String filePath = null;
            //判断是哪一个的回调
            if (requestCode == REQUEST_IMAGE_GET) {
                //返回的是content://的样式
                filePath = getFilePathFromContentUri(data.getData(), this);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    filePath = mCurrentPhotoPath;
                }
            }
            if (!TextUtils.isEmpty(filePath)) {
                Bitmap bitmap = BitmapUtil.getSmallBitmap(filePath, 200, 200);
                binding.ivAvatar.setImageBitmap(bitmap);
                ACache.get(context).put(ConfigSP.UserInfo.AVATAR, bitmap);
            }
        }
    }

    /**
     * @param uri     content:// 样式
     * @param context
     * @return real file path
     */
    public static String getFilePathFromContentUri(Uri uri, Context context) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    @SuppressLint("CheckResult")
    private void checkPermission(int index) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(PermissionGroup.TAKE_PHOTO)
                .subscribe(aBoolean -> {
                    if (!aBoolean) {
//                            RxPermissions.showPermissionDenyDialog(GroupFileListActivity.this,
//                                    PermissionGroups.GENERAL_PERMISSION_DESCRIPTION);
                        return;
                    }
                    if (index == 0) {
                        selectFromCamera();
                    } else {
                        selectFromAlbum();
                    }
                });
    }
}