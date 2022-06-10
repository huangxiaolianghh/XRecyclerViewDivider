package com.littlejerk.rvdivider.builder;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.littlejerk.rvdivider.DividerHelper;

import androidx.annotation.DimenRes;

/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 15:38
 * @Description : StaggeredGridLayoutManager分割线构造器（不支持draw颜色设置）
 */
public final class XStaggeredGridBuilder extends XDividerDecoration.Builder {

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
     * 是否忽略fullSpan的情况
     */
    private boolean mIsIgnoreFullSpan = false;

    public XStaggeredGridBuilder(Context context) {
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
    public XStaggeredGridBuilder setSpacing(float dpValueSpacing) {
        this.mSpacing = (int) DividerHelper.applyDimension(dpValueSpacing, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    /**
     * 设置分割线间距
     *
     * @param dimenResId
     * @return
     */
    public XStaggeredGridBuilder setSpacing(@DimenRes int dimenResId) {
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
    public XStaggeredGridBuilder setVLineSpacing(float dpValueVLineSpacing) {
        this.mVLineSpacing = (int) DividerHelper.applyDimension(dpValueVLineSpacing, TypedValue.COMPLEX_UNIT_DIP);
        return this;
    }

    public XStaggeredGridBuilder setVLineSpacing(@DimenRes int dimenResId) {
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
    public XStaggeredGridBuilder setHLineSpacing(float dpValueHLineSpacing) {
        this.mHLineSpacing = (int) DividerHelper.applyDimension(dpValueHLineSpacing, TypedValue.COMPLEX_UNIT_DIP);
        return this;

    }

    public XStaggeredGridBuilder setHLineSpacing(@DimenRes int dimenResId) {
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
    public XStaggeredGridBuilder setIncludeEdge(boolean includeEdge) {
        mIsIncludeEdge = includeEdge;
        return this;
    }

    public boolean isIgnoreFullSpan() {
        return mIsIgnoreFullSpan;
    }

    /**
     * 设置是否忽略fullSpan的情况
     *
     * @param ignoreFullSpan
     * @return
     */
    public XStaggeredGridBuilder setIgnoreFullSpan(boolean ignoreFullSpan) {
        mIsIgnoreFullSpan = ignoreFullSpan;
        return this;
    }

}
