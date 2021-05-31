package com.littlejerk.rvdivider.decoration;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;

/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 16:07
 * @Description : 接口类
 */
public interface ILDecoration {

    ILDecoration setPadding(float dpValuePadding);

    ILDecoration setPadding(@DimenRes int dimenResId);

    ILDecoration setLeftPadding(float dpValuePadding);

    ILDecoration setRightPadding(float dpValuePadding);

    ILDecoration setLeftPadding(@DimenRes int dimenResId);

    ILDecoration setRightPadding(@DimenRes int dimenResId);

    ILDecoration setTopPadding(float dpValuePadding);

    ILDecoration setBottomPadding(float dpValuePadding);

    ILDecoration setTopPadding(@DimenRes int dimenResId);

    ILDecoration setBottomPadding(@DimenRes int dimenResId);

    ILDecoration setColorRes(@ColorRes int colorResId);

    ILDecoration setColor(@ColorInt int color);

    ILDecoration setDrawableRes(@DrawableRes int drawableResId);

    ILDecoration setDrawable(Drawable drawable);

//    int getDividerSpace();

    int getLeftPadding();

    int getRightPadding();

    int getTopPadding();

    int getBottomPadding();

    Drawable getDividerDrawable();


}
