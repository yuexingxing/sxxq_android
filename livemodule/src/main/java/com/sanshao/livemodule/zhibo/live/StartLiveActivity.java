package com.sanshao.livemodule.zhibo.live;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.exam.commonbiz.api.oss.IOssModel;
import com.exam.commonbiz.api.oss.OssViewModel;
import com.exam.commonbiz.api.oss.UploadPicResponse;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.BitmapUtil;
import com.exam.commonbiz.util.FileUtil;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogAdapter;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.ActivityStartLiveBinding;
import com.sanshao.livemodule.liveroom.model.ILiveRoomModel;
import com.sanshao.livemodule.liveroom.roomutil.bean.GetRoomIdResponse;
import com.sanshao.livemodule.liveroom.roomutil.bean.LicenceInfo;
import com.sanshao.livemodule.liveroom.roomutil.bean.UserSignResponse;
import com.sanshao.livemodule.liveroom.viewmodel.LiveViewModel;
import com.sanshao.livemodule.zhibo.anchor.TCCameraAnchorActivity;
import com.sanshao.livemodule.zhibo.anchor.prepare.TCLocationHelper;
import com.sanshao.livemodule.zhibo.common.net.TCHTTPMgr;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 开播设置
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class StartLiveActivity extends BaseActivity<LiveViewModel, ActivityStartLiveBinding> implements IOssModel, ILiveRoomModel, TCLocationHelper.OnLocationListener {

    private final static int REQUEST_IMAGE_GET = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private String mCurrentPhotoPath;
    private OssViewModel mOssViewModel;
    private String mRoomId;

    public static void start(Context context) {
        Intent starter = new Intent(context, StartLiveActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_live;
    }

    @Override
    public void initData() {

        mOssViewModel = new OssViewModel();
        mOssViewModel.setCallBack(this);
        mViewModel.setILiveRoomModel(this);
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
        binding.edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 15) {
                    binding.edtTitle.setText(charSequence.subSequence(0, 15));
                    binding.edtTitle.setSelection(binding.edtTitle.getText().length());
                    ToastUtil.showShortToast("字数超出范围（15字以内）");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.flContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
                commonDialogInfoList.add(new CommonDialogInfo("拍照"));
                commonDialogInfoList.add(new CommonDialogInfo("相册"));

                new CommonBottomDialog()
                        .init(context)
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
            }
        });
        binding.tvStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPublish();
            }
        });
        binding.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvLocation.setText(R.string.text_live_location);
                startLocation();
            }
        });

        setLiveBg(TCUserMgr.getInstance().getCoverPic());
        startLocation();
        mViewModel.getRoomId();
    }

    // 发起定位
    private void startLocation() {
        if (TCLocationHelper.checkLocationPermission(StartLiveActivity.this)) {
            if (!TCLocationHelper.getMyLocation(StartLiveActivity.this, StartLiveActivity.this)) {
                binding.tvLocation.setText(getString(R.string.text_live_lbs_fail));
            }
        }
    }

    /**
     * 发起推流
     */
    private void startPublish() {

        String title = binding.edtTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showLongToast("请输入直播标题");
            return;
        }
        if (TextUtils.isEmpty(mRoomId)) {
            mRoomId = BasicApplication.getUserInfo().invitation_code;
        }
        if (TextUtils.isEmpty(TCUserMgr.getInstance().getCoverPic())) {
            ToastUtil.showLongToast("未上传封面");
            return;
        }

        UserInfo userInfo = BasicApplication.getUserInfo();
        Intent intent = new Intent(this, TCCameraAnchorActivity.class);
        if (intent != null) {
            intent.putExtra(TCConstants.ROOM_ID, mRoomId);
            intent.putExtra(TCConstants.ROOM_TITLE, title);
            intent.putExtra(TCConstants.USER_ID, userInfo.mem_phone);
            intent.putExtra(TCConstants.USER_NICK, userInfo.nickname);
            intent.putExtra(TCConstants.USER_HEADPIC, userInfo.avatar);
            intent.putExtra(TCConstants.COVER_PIC, TCUserMgr.getInstance().getCoverPic());
            intent.putExtra(TCConstants.USER_LOC, TCUserMgr.getInstance().getLocation());
            startActivity(intent);
            finish();
        }
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
                bitmap = BitmapUtil.getLocalBitmap(filePath);
                bitmap = BitmapUtil.getSmallBitmap(filePath, bitmap.getWidth() / 5, bitmap.getHeight() / 5);
                binding.ivBg.setImageBitmap(bitmap);
            }

            if (bitmap == null) {
                return;
            }

            LoadDialogMgr.getInstance().show(context);
            mOssViewModel.uploadPic(bitmap);
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
    private void checkPermission(final int index) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(PermissionGroup.TAKE_PHOTO)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (!aBoolean) {

                            return;
                        }
                        if (index == 0) {
                            selectFromCamera();
                        } else {
                            selectFromAlbum();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void returnUploadPic(int type, UploadPicResponse uploadPicResponse) {
        if (uploadPicResponse == null) {
            return;
        }
        setLiveBg(uploadPicResponse.url);
    }

    private void setLiveBg(String url) {

        if (TextUtils.isEmpty(url)) {
            return;
        }
        TCUserMgr.getInstance().setCoverPic(url, null);
        GlideUtil.loadImage(url, binding.ivBg);
        binding.tvUpload.setText("重新选择");
        binding.tvUpload.setAlpha(0.7f);
        binding.llLabel.setVisibility(View.GONE);
        binding.llBg.setVisibility(View.VISIBLE);
    }

    @Override
    public void returnGetLicense(LicenceInfo licenceInfo) {

    }

    @Override
    public void returnUserSign(UserSignResponse userSignResponse) {

    }

    @Override
    public void returnGetRoomId(GetRoomIdResponse getRoomIdResponse) {
        if (getRoomIdResponse == null) {
            return;
        }
        mRoomId = getRoomIdResponse.room_number;
    }

    @Override
    public void returnUploadLiveRoomInfo() {

    }

    /**
     *     /////////////////////////////////////////////////////////////////////////////////
     *     //
     *     //                      定位相关
     *     //
     *     /////////////////////////////////////////////////////////////////////////////////
     */
    /**
     * 定位结果的回调
     *
     * @param code
     * @param lat1
     * @param long1
     * @param location
     */
    @Override
    public void onLocationChanged(int code, double lat1, double long1, String location) {
        if (0 == code) {
            binding.tvLocation.setText(location);
            setLocation(location);
        } else {
            binding.tvLocation.setText(getString(R.string.text_live_lbs_fail));
        }
    }

    /**
     * 上传定位的结果，设置到开播的信息
     *
     * @param location
     */
    private void setLocation(String location) {
        TCUserMgr.getInstance().setLocation(location, new TCHTTPMgr.Callback() {
            @Override
            public void onSuccess(JSONObject data) {

            }

            @Override
            public void onFailure(int code, final String msg) {
                StartLiveActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "设置位置失败 " + msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}