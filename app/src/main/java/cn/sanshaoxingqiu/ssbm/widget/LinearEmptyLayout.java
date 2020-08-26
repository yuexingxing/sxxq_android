package cn.sanshaoxingqiu.ssbm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * recyclerview空数据/网络错误页面封装
 *
 * @Author yuexingxing
 * @time 2020/7/31
 */
public class LinearEmptyLayout extends LinearLayout {

    private View mEmptyView;
    private View mBindView;
    private View mErrorView;
    private TextView mBtnReset;
    private TextView mEmptyText;
    private ImageView ivEmptyImg;
    private ImageView ivErrorImg;

    public LinearEmptyLayout(Context context) {
        this(context, null);
    }

    public LinearEmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //居中
        params.gravity = Gravity.CENTER;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinearEmptyLayout, 0, 0);

        //数据为空时的布局
        int emptyLayout = ta.getResourceId(R.styleable.LinearEmptyLayout_elEmptyLayout, R.layout.layout_empty);
        mEmptyView = View.inflate(context, emptyLayout, null);
        mEmptyText = mEmptyView.findViewById(R.id.tv_title);
        ivEmptyImg = mEmptyView.findViewById(R.id.iv_empty_icon);
        addView(mEmptyView, params);

        //错误时的布局
        int errorLayout = ta.getResourceId(R.styleable.LinearEmptyLayout_elErrorLayout, R.layout.layout_error);
        mErrorView = View.inflate(context, errorLayout, null);
        mBtnReset = (TextView) mErrorView.findViewById(R.id.tv_title);
        ivErrorImg = mErrorView.findViewById(R.id.iv_error_icon);
        addView(mErrorView, params);

        //全部隐藏
        setGone();
    }

    /**
     * 全部隐藏
     */
    public void setGone() {
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showError() {
        showError(null);
    }

    public void showError(String text) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) mBtnReset.setText(text);
        setGone();
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showEmpty(String text, int imaSrc) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        mEmptyText.setText(text);
        ivEmptyImg.setBackgroundResource(imaSrc);
        setGone();
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void showSuccess() {
        if (mBindView != null) mBindView.setVisibility(View.VISIBLE);
        setGone();
    }

    public void bindView(View view) {
        mBindView = view;
    }

    public void setOnButtonClick(OnClickListener listener) {
        ivErrorImg.setOnClickListener(listener);
    }
}
