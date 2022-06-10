package com.littlejerk.rvdivider.builder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.littlejerk.rvdivider.DividerHelper;
import com.littlejerk.rvdivider.decoration.ILDecoration;
import com.littlejerk.rvdivider.decoration.LDecoration;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;


/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 15:25
 * @Description : LinearLayoutManager分割线构造器
 */
public final class XLinearBuilder extends XDividerDecoration.Builder implements ILDecoration {

    /**
     * 默认分割线的宽（高）度,单位像素px
     */
    private int mSpacing = DividerHelper.dp2px(1);
    /**
     * 是否绘制最后一条分割线
     */
    private boolean mShowLastLine = false;
    /**
     * 是否绘制第一个item的顶部分割线
     */
    private boolean mShowFirstTopLine = false;
    /**
     * 是否绘制RecyclerView的左右padding（竖直）或上下padding（水平）
     */
    private boolean mIsIncludeParentLTPadding = false;
    private boolean mIsIncludeParentRBPadding = false;
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
     * 不画分割线position的回调
     */
    private OnNoDividerPosition mOnNoDividerPosition;
    /**
     * item分割线绘制回调
     */
    private OnItemDivider mOnItemDivider;

    public XLinearBuilder(Context context) {
        super(context);
    }

    /**
     * 设置分割线宽（高）度
     */
    public XLinearBuilder setSpacing(float dpValueSpanSpace) {
        mSpacing = (int) DividerHelper.applyDimension(dpValueSpanSpace, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 设置分割线宽（高）度
     */
    public XLinearBuilder setSpacing(@DimenRes int dimenResId) {
        mSpacing = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    /**
     * 设置左右间距
     */
    @Override
    public XLinearBuilder setPadding(float dpValuePadding) {
        setLeftPadding(dpValuePadding);
        setRightPadding(dpValuePadding);
        setTopPadding(dpValuePadding);
        setBottomPadding(dpValuePadding);
        return this;
    }

    /**
     * 设置左右间距
     */
    @Override
    public XLinearBuilder setPadding(@DimenRes int dimenResId) {
        setLeftPadding(dimenResId);
        setRightPadding(dimenResId);
        setTopPadding(dimenResId);
        setBottomPadding(dimenResId);
        return this;
    }

    /**
     * 设置左间距
     */
    @Override
    public XLinearBuilder setLeftPadding(float dpValuePadding) {
        mLeftPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 设置右间距
     */
    @Override
    public XLinearBuilder setRightPadding(float dpValuePadding) {
        mRightPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 通过资源id设置左间距
     */
    @Override
    public XLinearBuilder setLeftPadding(@DimenRes int dimenResId) {
        mLeftPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    /**
     * 通过资源id设置右间距
     */
    @Override
    public XLinearBuilder setRightPadding(@DimenRes int dimenResId) {
        mRightPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    /**
     * 设置上间距
     */
    @Override
    public XLinearBuilder setTopPadding(float dpValuePadding) {
        mTopPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 设置下间距
     */
    @Override
    public XLinearBuilder setBottomPadding(float dpValuePadding) {
        mBottomPadding = (int) DividerHelper.applyDimension(dpValuePadding, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 通过资源id设置上间距
     */
    @Override
    public XLinearBuilder setTopPadding(@DimenRes int dimenResId) {
        mTopPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    /**
     * 通过资源id设置下间距
     */
    @Override
    public XLinearBuilder setBottomPadding(@DimenRes int dimenResId) {
        mBottomPadding = Resources.getSystem().getDimensionPixelSize(dimenResId);
        return this;
    }

    /**
     * 设置是否展示最后分割线
     *
     * @param showLastLine
     * @return
     */
    public XLinearBuilder setShowLastLine(boolean showLastLine) {
        mShowLastLine = showLastLine;
        return this;
    }

    /**
     * 设置是否展示第一个item顶部分割线
     *
     * @param showFirstTopLine
     * @return
     */
    public XLinearBuilder setShowFirstTopLine(boolean showFirstTopLine) {
        this.mShowFirstTopLine = showFirstTopLine;
        return this;
    }

    /**
     * 是否展示顶部分割线
     *
     * @return
     */
    public boolean isShowFirstTopLine() {
        return mShowFirstTopLine;
    }

    /**
     * 是否最后一条显示分割线
     *
     * @return
     */
    public boolean isShowLastLine() {
        return mShowLastLine;
    }

    /**
     * 是否忽略RecyclerView的padding
     *
     * @return
     */
    public boolean isIncludeParentLTPadding() {
        return mIsIncludeParentLTPadding;
    }

    public boolean isIncludeParentRBPadding() {
        return mIsIncludeParentRBPadding;
    }

    /**
     * 设置是否忽略RecyclerView的padding
     *
     * @param includeParentLTPadding
     * @param includeParentRBPadding
     * @return
     */
    public XLinearBuilder setIncludeParentHVPadding(boolean includeParentLTPadding, boolean includeParentRBPadding) {
        mIsIncludeParentLTPadding = includeParentLTPadding;
        mIsIncludeParentRBPadding = includeParentRBPadding;
        return this;
    }

    /**
     * 获取分割线宽（高）度
     *
     * @return
     */
    public int getSpacing() {
        return mSpacing;
    }

    /**
     * 获取分割线左内边距
     *
     * @return
     */
    @Override
    public int getLeftPadding() {
        return mLeftPadding;
    }

    /**
     * 获取分割线右内边距
     *
     * @return
     */
    @Override
    public int getRightPadding() {
        return mRightPadding;
    }

    /**
     * 获取分割线上内边距
     *
     * @return
     */
    @Override
    public int getTopPadding() {
        return mTopPadding;
    }

    /**
     * 获取分割线下内边距
     *
     * @return
     */
    @Override
    public int getBottomPadding() {
        return mBottomPadding;
    }


    /**
     * 通过资源id设置颜色
     */
    @Override
    public XLinearBuilder setColorRes(@ColorRes int colorResId) {
        setColor(ContextCompat.getColor(mContext, colorResId));
        return this;
    }

    /**
     * 设置颜色
     */
    @Override
    public XLinearBuilder setColor(@ColorInt int color) {
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
     * 通过资源id设置Drawable
     */
    @Override
    public XLinearBuilder setDrawableRes(@DrawableRes int drawableResId) {
        setDrawable(ContextCompat.getDrawable(mContext, drawableResId));
        return this;
    }

    /**
     * 设置分割线Drawable
     */
    @Override
    public XLinearBuilder setDrawable(Drawable drawable) {
        mDividerDrawable = drawable;
        return this;
    }


    /**
     * 获取分割线Drawable
     *
     * @return
     */
    @Override
    public Drawable getDividerDrawable() {
        //创建Drawable
        if (mDividerDrawable == null) {
            mDividerDrawable = new ColorDrawable(mColor);
        }
        return mDividerDrawable;
    }


    /**
     * 获取回调
     *
     * @return
     */
    public OnNoDividerPosition getOnItemNoDivider() {
        return mOnNoDividerPosition;
    }

    /**
     * 设置不画分割线position的回调
     *
     * @param onNoDividerPosition
     * @return
     */
    public XLinearBuilder setOnItemNoDivider(OnNoDividerPosition onNoDividerPosition) {
        this.mOnNoDividerPosition = onNoDividerPosition;
        return this;
    }

    /**
     * 是否画分割线的回调
     */
    public interface OnNoDividerPosition {
        int[] getNoDividerPosition();
    }


    /**
     * 获取分割线绘制的回调
     *
     * @return
     */
    public OnItemDivider getItemDividerDecoration() {
        return mOnItemDivider;
    }

    /**
     * 设置分割线绘制监听
     *
     * @param onItemDivider
     * @return
     */
    public XLinearBuilder setOnItemDividerDecoration(OnItemDivider onItemDivider) {
        this.mOnItemDivider = onItemDivider;
        return this;
    }

    public interface OnItemDivider {
        LDecoration getItemDividerDecoration(int position);
    }

}
