package cn.sanshaoxingqiu.ssbm.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * 图片选择
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */

public class SelectPicPopupWindow extends PopupWindow {
    private TextView mTvCamera;
    private TextView mTvPhoto;
    private TextView mTvCancel;
    private View mMenuView;

    public interface OnItemClick {
        void onSelectFromCamera();

        void onSelectFromAlbum();

        void onCancel();
    }

    /**
     * 上传图片*************************
     *
     * @param context
     * @param callBack
     */
    public SelectPicPopupWindow(Activity context, OnItemClick callBack) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.item_popupwindows, null);
        mTvCamera = mMenuView.findViewById(R.id.tv_camera); //拍照按钮
        mTvCancel = mMenuView.findViewById(R.id.tv_cancel); //取消按钮
        mTvPhoto = mMenuView.findViewById(R.id.tv_photo); //图库按钮

        /**
         * 取消按钮销毁事件
         */
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
                if (callBack != null) {
                    callBack.onCancel();
                }
            }
        });
        mTvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (callBack != null) {
                    callBack.onSelectFromCamera();
                }
            }
        });
        mTvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (callBack != null) {
                    callBack.onSelectFromAlbum();
                }
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow**弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        //修改高度显示，解决被手机底部虚拟键挡住的问题 by黄海杰 at:2015-4-30
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = mMenuView.findViewById(R.id.ll_popup).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}


