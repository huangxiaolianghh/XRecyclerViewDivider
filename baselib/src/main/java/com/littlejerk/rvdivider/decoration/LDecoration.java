package com.littlejerk.rvdivider.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.littlejerk.rvdivider.DividerHelper;

import androidx.core.content.ContextCompat;

/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 16:07
 * @Description : 线性item分割线修饰类
 */
public class LDecoration implements ILDecoration {
    private Context mContext;
    /**
     * 分割线左右内边距（垂直）
     */
    private int mLeftPadding = 0;
    private int mRightPadding = 0;
    /**
     * 分割线上下内边距（水平）
     */
    private int mTopPadding = 0;
    private int mBottomPadding = 0;
    /**
     * 分割线颜色或背景
     */
    private int mColor;
    private Drawable mDividerDrawable;

    /**
     * 默认对边界不处理
     */
    private Boolean isDrawLeft = null;
    private Boolean isDrawTop = null;
    private Boolean isDrawRight = null;
    private Boolean isDrawBottom = null;

    public LDecoration(Context context) {
        mContext = context;
    }

    /**
     * 设置绘制item左上右下属性
     *
     * @param isDrawLeft
     * @param isDrawTop
     * @param isDrawRight
     * @param isDrawBottom
     * @return
     */
    public LDecoration setAroundEdge(Boolean isDrawLeft, Boolean isDrawTop, Boolean isDrawRight, Boolean isDrawBottom) {
        this.isDrawLeft = isDrawLeft;
        this.isDrawTop = isDrawTop;
        this.isDrawRight = isDrawRight;
        this.isDrawBottom = isDrawBottom;
        return this;
    }

    public Boolean[] getAroundEdge() {
        return new Boolean[]{isDrawLeft, isDrawTop, isDrawRight, isDrawBottom};
    }

    @Override
    public LDecoration setPadding(float dpValuePadding) {
        setLeftPadding(dpValuePadding);
        setRightPadding(dpValuePadding);
        setTopPadding(dpValuePadding);
        setBottomPadding(dpValuePadding);
        return this;
    }

    @Override
    public LDecoration setPadding(int dimenResId) {
        setLeftPadding(dimenResId);
        setRightPadding(dimenResId);
        setTopPadding(dimenResId);
        setBottomPadding(dimenResId);
        return this;
    }

    @Override
    public LDecoration setLeftPadding(float dpValuePadding) {
        mLeftPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    @Override
    public LDecoration setRightPadding(float dpValuePadding) {
        mRightPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    @Override
    public LDecoration setLeftPadding(int dimenResId) {
        mLeftPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    @Override
    public LDecoration setRightPadding(int dimenResId) {
        mRightPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    @Override
    public LDecoration setTopPadding(float dpValuePadding) {
        mTopPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    @Override
    public LDecoration setBottomPadding(float dpValuePadding) {
        mBottomPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    @Override
    public LDecoration setTopPadding(int dimenResId) {
        mTopPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    @Override
    public LDecoration setBottomPadding(int dimenResId) {
        mBottomPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    @Override
    public LDecoration setColorRes(int colorResId) {
        setColor(ContextCompat.getColor(mContext, colorResId));
        return this;
    }

    @Override
    public LDecoration setColor(int color) {
        mColor = color;
        return this;
    }

    @Override
    public LDecoration setDrawableRes(int drawableResId) {
        setDrawable(ContextCompat.getDrawable(mContext, drawableResId));
        return this;
    }

    @Override
    public LDecoration setDrawable(Drawable drawable) {
        mDividerDrawable = drawable;
        return this;
    }

    @Override
    public int getLeftPadding() {
        return mLeftPadding;
    }

    @Override
    public int getRightPadding() {
        return mRightPadding;
    }

    @Override
    public int getTopPadding() {
        return mTopPadding;
    }

    @Override
    public int getBottomPadding() {
        return mBottomPadding;
    }

    @Override
    public Drawable getDividerDrawable() {
        //创建Drawable
        if (mDividerDrawable == null) {
            mDividerDrawable = new ColorDrawable(mColor);
        }
        return mDividerDrawable;
    }

}
