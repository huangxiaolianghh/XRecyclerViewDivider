package com.littlejerk.rvdivider.builder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.littlejerk.rvdivider.DividerHelper;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import static com.littlejerk.rvdivider.DividerHelper.NO_COLOR;

/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 15:30
 * @Description : GridLayoutManager分割线构造器
 */
public final class XGridBuilder extends XDividerDecoration.Builder {

    /**
     * 分割线宽或高,mVLineSpacing||mHLineSpacing > mSpacing
     */
    private int mVLineSpacing;
    private int mHLineSpacing;
    private int mSpacing;
    /**
     * 是否需要画边界
     */
    private boolean mIsIncludeEdge;
    /**
     * 竖直方向分割线是否包括item角边的距离
     */
    private boolean mVerticalIncludeEdge;
    /**
     * 分割线颜色,mVLineColor||mHLineColor > mColor
     */
    private int mVLineColor = NO_COLOR;
    private int mHLineColor = NO_COLOR;
    private int mColor = NO_COLOR;
    /**
     * 分割线drawable,mVLineDividerDrawable||mHLineDividerDrawable > mDividerDrawable
     */
    private Drawable mVLineDividerDrawable;
    private Drawable mHLineDividerDrawable;
    private Drawable mDividerDrawable;

    public XGridBuilder(Context context) {
        super(context);
    }

    public int getSpacing() {
        return mSpacing;
    }

    /**
     * 设置分割线间距
     *
     * @param dpValueSpacing
     * @return
     */
    public XGridBuilder setSpacing(float dpValueSpacing) {
        this.mSpacing = (int) DividerHelper.applyDimension(dpValueSpacing, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 设置分割线间距
     *
     * @param dimenResId
     * @return
     */
    public XGridBuilder setSpacing(@DimenRes int dimenResId) {
        this.mSpacing = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;

    }

    public int getVLineSpacing() {
        return mVLineSpacing;
    }

    /**
     * 设置竖直线间距
     *
     * @param dpValueVLineSpacing
     * @return
     */
    public XGridBuilder setVLineSpacing(float dpValueVLineSpacing) {
        this.mVLineSpacing = (int) DividerHelper.applyDimension(dpValueVLineSpacing, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    public XGridBuilder setVLineSpacing(@DimenRes int dimenResId) {
        this.mVLineSpacing = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }


    public int getHLineSpacing() {
        return mHLineSpacing;
    }

    /**
     * 设置水平线间距
     *
     * @param dpValueHLineSpacing
     * @return
     */
    public XGridBuilder setHLineSpacing(float dpValueHLineSpacing) {
        this.mHLineSpacing = (int) DividerHelper.applyDimension(dpValueHLineSpacing, TypedValue.COMPLEX_UNIT_DIP);
        return this;

    }

    public XGridBuilder setHLineSpacing(@DimenRes int dimenResId) {
        this.mHLineSpacing = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    public boolean isIncludeEdge() {
        return mIsIncludeEdge;
    }

    /**
     * 设置是否包含边界
     *
     * @param includeEdge
     * @return
     */
    public XGridBuilder setIncludeEdge(boolean includeEdge) {
        mIsIncludeEdge = includeEdge;
        return this;
    }

    public boolean isVerticalIncludeEdge() {
        return mVerticalIncludeEdge;
    }

    /**
     * 设置边界是否包含于竖直方向上的分割线
     *
     * @param verticalIncludeEdge
     * @return
     */
    public XGridBuilder setVerticalIncludeEdge(boolean verticalIncludeEdge) {
        this.mVerticalIncludeEdge = verticalIncludeEdge;
        return this;
    }


    public int getVLineColor() {
        return mVLineColor;
    }

    /**
     * 设置竖直分割线颜色
     *
     * @param vLineColor
     * @return
     */
    public XGridBuilder setVLineColor(@ColorInt int vLineColor) {
        this.mVLineColor = vLineColor;
        return this;
    }

    /**
     * 通过资源id设置竖直分割线颜色
     *
     * @param colorResId
     * @return
     */
    public XGridBuilder setVLineColorRes(@ColorRes int colorResId) {
        setVLineColor(ContextCompat.getColor(mContext, colorResId));
        return this;
    }


    public int getHLineColor() {
        return mHLineColor;
    }

    /**
     * 设置水平分割线颜色
     *
     * @param hLineColor
     * @return
     */
    public XGridBuilder setHLineColor(@ColorInt int hLineColor) {
        this.mHLineColor = hLineColor;
        return this;
    }

    /**
     * 通过资源id设置水平分割线颜色
     *
     * @param colorResId
     * @return
     */
    public XGridBuilder setHLineColorRes(@ColorRes int colorResId) {
        setHLineColor(ContextCompat.getColor(mContext, colorResId));
        return this;
    }


    /**
     * 通过资源id设置颜色
     *
     * @param colorResId
     * @return
     */
    public XGridBuilder setColorRes(@ColorRes int colorResId) {
        setColor(ContextCompat.getColor(mContext, colorResId));
        return this;
    }


    /**
     * 设置颜色，如果不设置mHLineColor的颜色，默认水平方向和竖直共用此颜色
     *
     * @param color
     * @return
     */
    public XGridBuilder setColor(@ColorInt int color) {
        mColor = color;
        return this;
    }

    /**
     * 获取颜色值
     *
     * @return
     */
    protected int getColor() {
        return mColor;
    }

    /**
     * 设置竖直方向分割线的drawable
     *
     * @param vLineDividerDrawable
     * @return
     */
    public XGridBuilder setVLineDrawable(Drawable vLineDividerDrawable) {
        this.mVLineDividerDrawable = vLineDividerDrawable;
        return this;
    }

    /**
     * 通过资源id设置竖直方向分割线的drawable
     */
    public XGridBuilder setVLineDrawableRes(@DrawableRes int drawableResId) {
        setVLineDrawable(ContextCompat.getDrawable(mContext, drawableResId));
        return this;
    }

    /**
     * 设置水平方向分割线的drawable
     *
     * @param hLineDividerDrawable
     * @return
     */
    public XGridBuilder setHLineDrawable(Drawable hLineDividerDrawable) {
        this.mHLineDividerDrawable = hLineDividerDrawable;
        return this;
    }

    /**
     * 通过资源id设置水平方向分割线的drawable
     */
    public XGridBuilder setHLineDrawableRes(@DrawableRes int drawableResId) {
        setHLineDrawable(ContextCompat.getDrawable(mContext, drawableResId));
        return this;
    }

    /**
     * 通过资源id设置Drawable
     */
    public XGridBuilder setDrawableRes(@DrawableRes int drawableResId) {
        setDrawable(ContextCompat.getDrawable(mContext, drawableResId));
        return this;
    }

    /**
     * 设置分割线Drawable
     */
    public XGridBuilder setDrawable(Drawable drawable) {
        mDividerDrawable = drawable;
        return this;
    }

    /**
     * 获取分割线Drawable
     *
     * @return
     */
    public Drawable getVLineDividerDrawable() {
        //创建Drawable
        if (mVLineDividerDrawable == null) {
            if (mVLineColor == NO_COLOR) {
                if (mDividerDrawable == null) {
                    if (mColor != NO_COLOR) {
                        mVLineDividerDrawable = new ColorDrawable(mColor);
                    }
                } else {
                    mVLineDividerDrawable = mDividerDrawable;
                }
            } else {
                mVLineDividerDrawable = new ColorDrawable(mVLineColor);
            }
        }
        return mVLineDividerDrawable;
    }


    /**
     * 获取分割线Drawable
     *
     * @return
     */
    public Drawable getHLineDividerDrawable() {
        //创建Drawable
        if (mHLineDividerDrawable == null) {
            if (mHLineColor == NO_COLOR) {
                if (mDividerDrawable == null) {
                    if (mColor != NO_COLOR) {
                        mHLineDividerDrawable = new ColorDrawable(mColor);
                    }
                } else {
                    mHLineDividerDrawable = mDividerDrawable;
                }
            } else {
                mHLineDividerDrawable = new ColorDrawable(mHLineColor);
            }
        }
        return mHLineDividerDrawable;
    }
}
