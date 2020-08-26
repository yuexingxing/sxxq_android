package cn.sanshaoxingqiu.ssbm.widget;

/**
 * @Author yuexingxing
 * @time 2020/7/8
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

import cn.sanshaoxingqiu.ssbm.R;

/*
 *用来显示不规则图片，
 * 上面两个是圆角，下面两个是直角
 * */
public class OvalImageView extends AppCompatImageView {

    private int topLeftRadius;
    private int topRightRaidus;
    private int bottomLeftRadius;
    private int bottomRightRaidus;

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {10.0f, 10.0f, 10.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,};

    public OvalImageView(Context context) {
        super(context);
    }

    public OvalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OvalImageView, 0, 0);
        try {
            topLeftRadius = typedArray.getInt(R.styleable.OvalImageView_OvalImageView_top_left, 0);
            topRightRaidus = typedArray.getInt(R.styleable.OvalImageView_OvalImageView_top_right, 0);
            bottomLeftRadius = typedArray.getInt(R.styleable.OvalImageView_OvalImageView_bottom_left, 0);
            bottomRightRaidus = typedArray.getInt(R.styleable.OvalImageView_OvalImageView_bottom_right, 0);
            rids[0] = topLeftRadius;
            rids[1] = topLeftRadius;

            rids[2] = topRightRaidus;
            rids[3] = topRightRaidus;

            rids[4] = bottomLeftRadius;
            rids[5] = bottomLeftRadius;

            rids[6] = bottomRightRaidus;
            rids[7] = bottomRightRaidus;
        } finally {
            typedArray.recycle();
        }
    }

    public OvalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 画图
     * by Hankkin at:2015-08-30 21:15:53
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}