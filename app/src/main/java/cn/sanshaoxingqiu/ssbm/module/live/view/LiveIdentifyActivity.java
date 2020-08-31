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
import cn.sanshaoxingqiu.ssbm.module.live.viewmodel.IdentityViewModel;
import cn.sanshaoxingqiu.ssbm.util.BitmapUtil;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.FileUtil;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

/**
 * 身份认证
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class LiveIdentifyActivity extends BaseActivity<IdentityViewModel, ActivityLiveIdentifyBinding> {

    private final static int REQUEST_IMAGE_GET = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private String mCurrentPhotoPath;
    private int mCurrentIndex;//当前拍照类型0，1，2

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
            mCurrentIndex = 0;
            takePhoto();
        });
        binding.llStep2.setOnClickListener(v -> {
            mCurrentIndex = 1;
            takePhoto();
        });
        binding.llStep3.setOnClickListener(v -> {
            mCurrentIndex = 2;
            takePhoto();
        });
        binding.tvSubmit.setOnClickListener(v -> {

        });
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
            if (!TextUtils.isEmpty(filePath)) {
                Bitmap bitmap = BitmapUtil.getSmallBitmap(filePath, 200, 200);
                if (mCurrentIndex == 0) {
                    binding.ivStep1.setImageBitmap(bitmap);
                    binding.ivStep1.setVisibility(View.VISIBLE);
                    binding.flStep1.setVisibility(View.GONE);
                } else if (mCurrentIndex == 1) {
                    binding.ivStep2.setImageBitmap(bitmap);
                    binding.ivStep2.setVisibility(View.VISIBLE);
                    binding.flStep2.setVisibility(View.GONE);
                } else {
                    binding.ivStep3.setImageBitmap(bitmap);
                    binding.ivStep3.setVisibility(View.VISIBLE);
                    binding.flStep3.setVisibility(View.GONE);
                }
            }

            //TODO 上传图片
            //实例化OSSClient (自己是在onCreate()中实例化的，当然考虑到token的过期问题，也有在onResume()中再次实例化一次)
//            OssService ossService = OssService.initOSS(tokenBean.getBucket().getEndPoint(), tokenBean.getBucket().getBucketName());
//            //上传图片,需要根据自己的逻辑传参数
//            ossService.asyncPutImage("图片在阿里上的存储路径", "本地路径", null, "1");
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
                    if (index == 0) {
                        selectFromCamera();
                    } else {
                        selectFromAlbum();
                    }
                });
    }
}