package cn.sanshaoxingqiu.ssbm.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.sanshaoxingqiu.ssbm.R;

public class RedPointImageView extends androidx.appcompat.widget.AppCompatImageView {

    //红点长度类型
    private static final int TYPE_ZERO = 0;
    private static final int TYPE_SHORT = 1;
    private static final int TYPE_LONG = 2;

    private int type;

    //保存onSizeChange()里的宽高
    private int width;
    private int height;

    //按钮Tag，用来识别
    private String mTag;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mRect = new Rect();
    private RelativeLayout mRlPoint = null;
    private Drawable mDPoint = null;
    private int radius;
    private boolean mShow = false;
    private int number;
    private TextView mTvPoint;

    public RedPointImageView(Context context) {
        super(context);
        this.number = -1;
        init();
    }

    public RedPointImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.number = -1;
        init();
    }

    public RedPointImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.number = -1;
        init();
    }

    private void init() {
        if (number < 0) return;//数量小于0直接返回
        mPaint.setFilterBitmap(true);
        radius = getResources().getDimensionPixelSize(R.dimen.red_point_radius);

        mRlPoint = new RelativeLayout(getContext());

        mTvPoint = new TextView(getContext());

        mTvPoint.setTextSize(14);
        mTvPoint.setTextColor(getResources().getColor(R.color.white));
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0, 0, 0, 0);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRlPoint.addView(mTvPoint, params1);
        initUI();
    }

    private void initUI() {
        if (number == 0) {//ZERO类型
            mTvPoint.setText("");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.margin_8), getResources().getDimensionPixelOffset(R.dimen.margin_8));
            params.setMargins(0, 0, 0, 0);
            mRlPoint.setLayoutParams(params);
            mRlPoint.setBackgroundResource(R.drawable.shape_red_radius_10);
            type = TYPE_ZERO;
        } else if (number > 0 && number < 10) {//SHORT类型
            mTvPoint.setText(String.valueOf(number));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.margin_15), getResources().getDimensionPixelOffset(R.dimen.margin_15));
            params.setMargins(10, 0, 10, 0);
            mRlPoint.setLayoutParams(params);
            mRlPoint.setBackgroundResource(R.drawable.shape_red_radius_10);
            type = TYPE_SHORT;
        } else if (number > 9 && number < 100) {//LONG类型
            mTvPoint.setText(String.valueOf(number));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.margin_30), getResources().getDimensionPixelOffset(R.dimen.margin_30));
            params.setMargins(0, 0, 0, 0);
            mRlPoint.setLayoutParams(params);
            mRlPoint.setBackgroundResource(R.drawable.shape_red_radius_30);
            type = TYPE_SHORT;
        } else {//LONG类型
            mTvPoint.setText("99+");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.margin_30), getResources().getDimensionPixelOffset(R.dimen.margin_30));
            params.setMargins(0, 0, 0, 0);
            mRlPoint.setLayoutParams(params);
            mRlPoint.setBackgroundResource(R.drawable.shape_red_radius_10);
            type = TYPE_LONG;
        }
        mDPoint = new BitmapDrawable(null, convertViewToBitmap(mRlPoint));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        updateRect(w, h);
    }

    private void updateRect(int w, int h) {
        int left, top, right, bottom;
        if (type == TYPE_SHORT) {
            left = w - radius;
            top = 0;
            right = w;
            bottom = radius;
        } else if (type == TYPE_ZERO) {
            left = w - radius * 2 / 3;
            top = 0;
            right = w;
            bottom = radius * 2 / 3;
        } else {
            left = w - radius / 3 * 4;
            top = 0;
            right = w;
            bottom = radius / 5 * 4;
        }

        mRect.set(left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShow) {
            drawRedPoint(canvas);
        }
    }

    private void drawRedPoint(Canvas canvas) {
        if (mDPoint == null) {
            return;
        }

        canvas.save();
//        canvas.clipRect(mRect, Region.Op.DIFFERENCE);

        mDPoint.setBounds(mRect);
        mDPoint.draw(canvas);

        canvas.restore();
    }

    public void setShow(boolean isShow) {
        this.mShow = isShow;
        invalidate();
    }

    public boolean isShow() {
        return mShow;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public void updateItem() {

    }

    public void setNumber(int number) {
        this.number = number;
        if (number < 0) mShow = false;
        else mShow = true;
        init();
        onSizeChanged(width, height, width, height);
        invalidate();
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }
}