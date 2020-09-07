package cn.sanshaoxingqiu.ssbm.module.personal.personaldata.view;

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

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityPersonalDetailBinding;
import cn.sanshaoxingqiu.ssbm.module.common.oss.IOssModel;
import cn.sanshaoxingqiu.ssbm.module.common.oss.OssViewModel;
import cn.sanshaoxingqiu.ssbm.module.common.oss.UploadPicResponse;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.model.IPersonalCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.personaldata.dialog.SelectBirthdayDialog;
import cn.sanshaoxingqiu.ssbm.module.personal.viewmodel.PersonalViewModel;
import cn.sanshaoxingqiu.ssbm.util.BitmapUtil;
import cn.sanshaoxingqiu.ssbm.util.FileUtil;
import com.exam.commonbiz.util.GlideUtil;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人设置
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class PersonalDetailActivity extends BaseActivity<PersonalViewModel, ActivityPersonalDetailBinding> implements IPersonalCallBack, IOssModel {

    private final static int REQUEST_IMAGE_GET = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private String mCurrentPhotoPath;
    private OssViewModel mOssViewModel;

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
        mViewModel.getUserInfo();
    }

    @Override
    public void initData() {

        mOssViewModel = new OssViewModel();
        mOssViewModel.setCallBack(this);
        mViewModel.setCallBack(this);
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
            //TODO 屏蔽修改头像
            List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
            commonDialogInfoList.add(new CommonDialogInfo("拍照"));
            commonDialogInfoList.add(new CommonDialogInfo("相册"));

            new CommonBottomDialog()
                    .init(this)
                    .setData(commonDialogInfoList)
                    .setOnItemClickListener(commonDialogInfo -> {
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
                    })
                    .show();
        });

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
                    .setOnItemClickListener(commonDialogInfo -> {
                        UserInfo userInfoTemp = new UserInfo();
                        if (commonDialogInfo.position == 0) {
                            userInfoTemp.gender = "M";
                        } else {
                            userInfoTemp.gender = "F";
                        }
                        mViewModel.updateUserInfo(userInfoTemp);
                    })
                    .show();
        });
        binding.lcvSignature.setOnClickListener(v -> {
            PersonalSignatureActivity.start(context);
        });
        binding.lcvBirthday.setOnClickListener(v -> {

            boolean[] timeType = new boolean[]{true, true, true, false, false, false};
            new SelectBirthdayDialog()
                    .init(context, "出生日期", timeType)
                    .setCommonCallBack((postion, object) -> {
                        if (object == null) {
                            return;
                        }
                        String birthday = ((String) object).split(" ")[0];
                        UserInfo userInfoTemp = new UserInfo();
                        userInfoTemp.birthday = birthday;
                        mViewModel.updateUserInfo(userInfoTemp);
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
            Bitmap bitmap = null;
            if (!TextUtils.isEmpty(filePath)) {
                bitmap = BitmapUtil.getSmallBitmap(filePath, 200, 200);
                binding.ivAvatar.setImageBitmap(bitmap);
            }

            if (bitmap == null) {
                return;
            }
            mOssViewModel.uploadPic(0, bitmap);
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

    @Override
    public void returnUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        SSApplication.getInstance().saveUserInfo(userInfo);
        updateUI(userInfo);
    }

    @Override
    public void returnUpdateUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        ToastUtil.showShortToast("修改成功");
        mViewModel.getUserInfo();
    }

    private void updateUI(UserInfo userInfo) {
        binding.lcvNickName.setContent(userInfo.nickname);
        binding.lcvSex.setContent(userInfo.getGender());
        binding.lcvBirthday.setContent(userInfo.birthday);
        binding.lcvSignature.setContent(userInfo.signature);
        GlideUtil.loadImage(userInfo.avatar, binding.ivAvatar, R.drawable.image_placeholder_two);
    }

    @Override
    public void returnUploadPic(int type, UploadPicResponse uploadPicResponse) {
        if (uploadPicResponse == null) {
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.avatar = uploadPicResponse.url;
        mViewModel.updateUserInfo(userInfo);
    }
}