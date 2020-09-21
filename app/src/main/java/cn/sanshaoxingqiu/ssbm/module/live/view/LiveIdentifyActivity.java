package cn.sanshaoxingqiu.ssbm.module.live.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.FileUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityLiveIdentifyBinding;
import com.exam.commonbiz.api.oss.IOssModel;
import com.exam.commonbiz.api.oss.OssViewModel;
import com.exam.commonbiz.api.oss.UploadPicResponse;
import cn.sanshaoxingqiu.ssbm.module.live.api.LiveApplyRequest;
import cn.sanshaoxingqiu.ssbm.module.live.bean.LiveApplyResponse;
import cn.sanshaoxingqiu.ssbm.module.live.model.IIdentityModel;
import cn.sanshaoxingqiu.ssbm.module.live.viewmodel.IdentityViewModel;
import com.exam.commonbiz.util.BitmapUtil;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

/**
 * 身份认证
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class LiveIdentifyActivity extends BaseActivity<IdentityViewModel, ActivityLiveIdentifyBinding> implements IIdentityModel, IOssModel {

    public static final int UPLOAD_ID_CARD_1 = 1;
    public static final int UPLOAD_ID_CARD_2 = 2;
    public static final int UPLOAD_ID_CARD_3 = 3;

    private final static int REQUEST_IMAGE_GET = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private String mCurrentPhotoPath;
    private int mUploadType;//当前拍照类型0，1，2
    private OssViewModel mOssViewModel;
    private String mIdCard1;
    private String mIdCard2;
    private String mIdCard3;

    public static void start(Context context) {
        Intent starter = new Intent(context, LiveIdentifyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_identify;
    }

    @Override
    public void initData() {

        mOssViewModel = new OssViewModel();
        mOssViewModel.setCallBack(this);
        mViewModel.setCallBack(this);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onTitleClick(View view) {

            }

            @Override
            public void onRightClick(View view) {

            }
        });

        binding.llStep1.setOnClickListener(v -> {
            mUploadType = UPLOAD_ID_CARD_1;
            takePhoto();
        });
        binding.llStep2.setOnClickListener(v -> {
            mUploadType = UPLOAD_ID_CARD_2;
            takePhoto();
        });
        binding.llStep3.setOnClickListener(v -> {
            mUploadType = UPLOAD_ID_CARD_3;
            takePhoto();
        });
        binding.tvSubmit.setOnClickListener(v -> {
            liveApply();
        });
    }

    private void liveApply() {

        String name = binding.edtName.getText().toString();
        String idCard = binding.edtId.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showLongToast("姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            ToastUtil.showLongToast("身份证号不能为空");
            return;
        }
        if (TextUtils.isEmpty(mIdCard1)) {
            ToastUtil.showLongToast("请上传身份证正面照");
            return;
        }
        if (TextUtils.isEmpty(mIdCard2)) {
            ToastUtil.showLongToast("请上传身份证反面照");
            return;
        }
        if (TextUtils.isEmpty(mIdCard3)) {
            ToastUtil.showLongToast("请上传手持身份证照片");
            return;
        }

        LiveApplyRequest liveApplyRequest = new LiveApplyRequest();
        liveApplyRequest.anchor_name = name;
        liveApplyRequest.identity_number = idCard;
        liveApplyRequest.identity_card_front = mIdCard1;
        liveApplyRequest.identity_card_back = mIdCard2;
        liveApplyRequest.identity_handle = mIdCard3;
        LoadDialogMgr.getInstance().show(context);
        mViewModel.liveApply(liveApplyRequest);
    }

    private void takePhoto() {

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
    }

    private void selectFromAlbum() {
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
                filePath = CommandTools.getFilePathFromContentUri(data.getData(), this);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    filePath = mCurrentPhotoPath;
                }
            }
            Bitmap bitmap = null;
            if (!TextUtils.isEmpty(filePath)) {
                bitmap = BitmapUtil.getSmallBitmap(filePath, 200, 200);
                if (mUploadType == UPLOAD_ID_CARD_1) {
                    binding.ivStep1.setImageBitmap(bitmap);
                } else if (mUploadType == UPLOAD_ID_CARD_2) {
                    binding.ivStep2.setImageBitmap(bitmap);
                } else {
                    binding.ivStep3.setImageBitmap(bitmap);
                }
            }

            if (bitmap == null) {
                return;
            }
            mOssViewModel.uploadIdCard(mUploadType, bitmap);
        }
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
                    FileUtil.initPath();
                    if (index == 0) {
                        selectFromCamera();
                    } else {
                        selectFromAlbum();
                    }
                });
    }

    @Override
    public void returnUploadPic(int type, UploadPicResponse uploadPicResponse) {
        if (uploadPicResponse == null) {
            return;
        }
        if (UPLOAD_ID_CARD_1 == type) {
            mIdCard1 = uploadPicResponse.fileName;
            GlideUtil.loadImage(uploadPicResponse.url, binding.ivStep1);
            binding.ivStep1.setVisibility(View.VISIBLE);
            binding.flStep1.setVisibility(View.GONE);
        } else if (UPLOAD_ID_CARD_2 == type) {
            mIdCard2 = uploadPicResponse.fileName;
            GlideUtil.loadImage(uploadPicResponse.url, binding.ivStep2);
            binding.ivStep2.setVisibility(View.VISIBLE);
            binding.flStep2.setVisibility(View.GONE);
        } else {
            mIdCard3 = uploadPicResponse.fileName;
            GlideUtil.loadImage(uploadPicResponse.url, binding.ivStep3);
            binding.ivStep3.setVisibility(View.VISIBLE);
            binding.flStep3.setVisibility(View.GONE);
        }
    }

    @Override
    public void returnLiveApply() {
        ToastUtil.showLongToast("提交成功");
        finish();
    }

    @Override
    public void returnAnchorDetail(LiveApplyResponse liveApplyResponse) {

    }
}