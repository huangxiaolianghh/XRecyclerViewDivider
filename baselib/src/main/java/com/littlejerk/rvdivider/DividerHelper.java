package com.littlejerk.rvdivider;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.littlejerk.rvdivider.builder.XLinearBuilder;
import com.littlejerk.rvdivider.decoration.LDecoration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;


/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 16:03
 * @Description : 设置分割线帮助类
 */
public final class DividerHelper {

    private static final String TAG = "DividerHelper";
    public static final int NO_COLOR = Color.TRANSPARENT;

    private DividerHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 是否为最后一行（Vertical）
     * 是否为最后一列（Horizontal）
     *
     * @param parent
     * @param childCount
     * @param childIndex
     * @return
     */
    public static boolean isLastRow(RecyclerView parent,
                                    int childCount,
                                    int childIndex) {

        int spanCount = getSpanCount(parent);

        if (getOrientation(parent) == VERTICAL) {
            return isLastItemEdgeValid((
                    childIndex >= childCount - spanCount), parent, childCount, childIndex, true
            );
        } else {
            int spanIndex = getItemSpanIndex(parent, childIndex, true);
            int itemSpanSize = getItemSpanSize(parent, childIndex, true);
            return (spanIndex + itemSpanSize) == spanCount;
        }

    }

    /**
     * 是否为最后一列
     *
     * @param parent
     * @return
     */
    public static boolean isLastColumn(RecyclerView parent,
                                       int childCount,
                                       int childIndex) {
        int spanCount = getSpanCount(parent);
        if (getOrientation(parent) == VERTICAL) {
            int spanIndex = getItemSpanIndex(parent, childIndex, false);
            int itemSpanSize = getItemSpanSize(parent, childIndex, false);
            return (spanIndex + itemSpanSize) == spanCount;
        } else {
            return isLastItemEdgeValid((
                    childIndex >= childCount - spanCount), parent, childCount, childIndex, false
            );
        }
    }

//    /**
//     * 是否为边界
//     *
//     * @param parent
//     * @param childCount
//     * @param childIndex
//     * @param itemSpanSize
//     * @param spanIndex
//     * @return
//     */
//    public static boolean isBottomEdge(RecyclerView parent,
//                                          int childCount,
//                                          int childIndex,
//                                          int itemSpanSize,
//                                          int spanIndex) {
//        int spanCount = getSpanCount(parent);
//        if (getOrientation(parent) == VERTICAL) {
//            return isLastItemEdgeValid((
//                    childIndex
//                            >= childCount - spanCount), parent, childCount, childIndex, spanIndex
//            );
//        } else {
//            return (spanIndex + itemSpanSize) == spanCount;
//        }
//    }

    protected static boolean isLastItemEdgeValid(boolean isOneOfLastItems,
                                                 RecyclerView parent,
                                                 int childCount,
                                                 int childIndex, boolean isRow) {
        int totalSpanRemaining = 0;
        if (isOneOfLastItems) {
            for (int i = childIndex; i < childCount; i++) {
                totalSpanRemaining = totalSpanRemaining + getItemSpanSize(parent, i, isRow);
            }
        }
        int spanIndex = getItemSpanIndex(parent, childIndex, isRow);
        return isOneOfLastItems && (totalSpanRemaining <= getSpanCount(parent) - spanIndex);
    }

    /**
     * 获取RecyclerView布局方向
     *
     * @param parent
     * @return
     */
    protected static int getOrientation(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }

        return VERTICAL;
    }

    /**
     * 获取item跨的列数
     *
     * @param parent
     * @param childIndex
     * @return
     */
    protected static int getItemSpanSize(RecyclerView parent, int childIndex, boolean isRow) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(childIndex);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            //瀑布流是不规则的，对其区别处理
            if (isRow) {
                return 1;
            } else {
                View view = layoutManager.findViewByPosition(childIndex);
                Objects.requireNonNull(view, "findViewByPosition for view is null");
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                return params.isFullSpan() ? getSpanCount(parent) : 1;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return 1;
        }

        return -1;
    }

    /**
     * 获取item的第一个检索
     *
     * @param parent
     * @param childIndex
     * @return
     */
    protected static int getItemSpanIndex(RecyclerView parent, int childIndex, boolean isRow) {
        int spanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanIndex(childIndex, spanCount);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            //瀑布流是不规则的，对其区别处理
            if (isRow) {
                return childIndex % spanCount;
            } else {
                View view = layoutManager.findViewByPosition(childIndex);
                Objects.requireNonNull(view, "findViewByPosition for view is null");
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                // 列
                return params.getSpanIndex();
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return 0;
        }

        return -1;
    }


    /**
     * 获取LayoutManager的span
     *
     * @param parent
     * @return
     */
    public static int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            spanCount = 1;
        }
        return spanCount;
    }

    /**
     * 获取当前RecyclerView分割线类型
     *
     * @param parent
     * @return
     */
    public static DividerType getDividerType(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager).getOrientation();
            return orientation == GridLayoutManager.VERTICAL ?
                    DividerType.GRID_VERTICAL : DividerType.GRID_HORIZONTAL;
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            return orientation == LinearLayoutManager.VERTICAL ?
                    DividerType.LINEAR_VERTICAL : DividerType.LINEAR_HORIZONTAL;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            return orientation == StaggeredGridLayoutManager.VERTICAL ?
                    DividerType.STAGGERED_GRID_VERTICAL : DividerType.STAGGERED_GRID_HORIZONTAL;
        } else {
            return DividerType.UNKNOWN;
        }
    }


    public enum DividerType {

        LINEAR_VERTICAL,
        LINEAR_HORIZONTAL,
        GRID_VERTICAL,
        GRID_HORIZONTAL,
        STAGGERED_GRID_VERTICAL,
        STAGGERED_GRID_HORIZONTAL,
        UNKNOWN

    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Edge.LEFT, Edge.TOP, Edge.RIGHT, Edge.BOTTOM})
    public @interface Edge {
        int LEFT = 0;
        int TOP = 1;
        int RIGHT = 2;
        int BOTTOM = 3;
    }


    /**
     * 检测数组是否存在某个position
     *
     * @param list
     * @param position
     * @return
     */
    public static boolean isContains(int[] list, int position) {
        for (int j : list) {
            if (position == j) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取分割线装饰器
     *
     * @param builder
     * @param itemPosition
     * @return
     */
    public static LDecoration getDecoration(XLinearBuilder builder, int itemPosition) {
        if (builder.getItemDividerDecoration() != null && builder.getItemDividerDecoration().getItemDividerDecoration(itemPosition) != null) {
            return builder.getItemDividerDecoration().getItemDividerDecoration(itemPosition);
        }
        return null;
    }


    /**
     * 获取Decoration的左内边距
     *
     * @param decoration
     * @return
     */
    public static int getDecorationLeftPadding(LDecoration decoration) {
        if (decoration == null) return 0;
        return decoration.getLeftPadding();
    }

    /**
     * 获取Decoration的右内边距
     *
     * @param decoration
     * @return
     */
    public static int getDecorationRightPadding(LDecoration decoration) {
        if (decoration == null) return 0;
        return decoration.getRightPadding();
    }

    /**
     * 获取Decoration的顶部内边距
     *
     * @param decoration
     * @return
     */
    public static int getDecorationTopPadding(LDecoration decoration) {
        if (decoration == null) return 0;
        return decoration.getTopPadding();
    }

    /**
     * 获取Decoration的底部内边距
     *
     * @param decoration
     * @return
     */
    public static int getDecorationBottomPadding(LDecoration decoration) {
        if (decoration == null) return 0;
        return decoration.getBottomPadding();
    }


    /**
     * 是否绘制边界的颜色
     *
     * @param decoration
     * @param edge
     * @return
     */
    public static Boolean isDrawEdge(LDecoration decoration, @Edge int edge) {
        if (decoration == null) return null;
        Boolean[] aroundEdge = decoration.getAroundEdge();
        return aroundEdge[edge];
    }

    /**
     * dp转px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dp(final float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 不同单位的值转px
     *
     * @param value
     * @param unit
     * @return
     */
    public static float applyDimension(final float value, final int unit) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}
