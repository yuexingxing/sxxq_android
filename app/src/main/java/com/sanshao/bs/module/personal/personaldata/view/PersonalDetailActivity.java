package com.sanshao.bs.module.personal.personaldata.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.QRCodeUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityPersonalDetailBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.personaldata.dialog.SelectBirthdayDialog;
import com.sanshao.bs.module.personal.setting.viewmodel.PersonalDetailViewModel;
import com.sanshao.bs.module.personal.viewmodel.PersonalViewModel;
import com.sanshao.bs.util.BitmapUtil;
import com.sanshao.bs.util.FileUtil;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogAdapter;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private PersonalViewModel mPersonalViewModel;

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

        mPersonalViewModel = new PersonalViewModel();
        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
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
            List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
            commonDialogInfoList.add(new CommonDialogInfo("拍照"));
            commonDialogInfoList.add(new CommonDialogInfo("相册"));

            new CommonBottomDialog()
                    .init(this)
                    .setData(commonDialogInfoList)
                    .setOnItemClickListener(new CommonDialogAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(CommonDialogInfo commonDialogInfo) {
                            if (commonDialogInfo.position == 0) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    checkPermission(0);
                                } else {
                                    selectFromCamera();
                                }
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    checkPermission(1);
                                } else {
                                    selectFromAlbum();
                                }
                            }
                        }
                    })
                    .show();
        });
        Bitmap bitmap = ACache.get(context).getAsBitmap(ConfigSP.UserInfo.AVATAR);
        if (bitmap != null) {
            binding.ivAvatar.setImageBitmap(bitmap);
        }

        binding.lcvNickName.setOnClickListener(v -> SettingNameActivity.start(context));
        binding.llQrcode.setOnClickListener(v -> {
            RecommendCodeActivity.start(context);
        });
        binding.lcvSex.setOnClickListener(v -> {
            List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
            commonDialogInfoList.add(new CommonDialogInfo("男"));
            commonDialogInfoList.add(new CommonDialogInfo("女"));

            new CommonBottomDialog()
                    .init(this)
                    .setData(commonDialogInfoList)
                    .setOnItemClickListener(new CommonDialogAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(CommonDialogInfo commonDialogInfo) {
                            UserInfo userInfo = SSApplication.getInstance().getUserInfo();
                            userInfo.sexName = commonDialogInfo.name;
                            SSApplication.getInstance().saveUserInfo(userInfo);
                            binding.lcvSex.setContent(userInfo.sexName);
                            //TODO 更新性别
                            //mPersonalSignatureViewModel.updateUserInfo(null);
                        }
                    })
                    .show();
        });
        binding.lcvSignature.setOnClickListener(v -> {
            PersonalSignatureActivity.start(context);
        });
        binding.lcvBirthday.setOnClickListener(v -> {

            new SelectBirthdayDialog()
                    .init(context, "出生日期")
                    .setCommonCallBack(new CommonCallBack() {
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

                            //TODO 更新生日
                            //mPersonalSignatureViewModel.updateUserInfo(null);
                        }
                    }).show();
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

            //TODO 上传图片
            //实例化OSSClient (自己是在onCreate()中实例化的，当然考虑到token的过期问题，也有在onResume()中再次实例化一次)
//            OssService ossService = OssService.initOSS(tokenBean.getBucket().getEndPoint(), tokenBean.getBucket().getBucketName());
//            //上传图片,需要根据自己的逻辑传参数
//            ossService.asyncPutImage("图片在阿里上的存储路径", "本地路径", null, "1");
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