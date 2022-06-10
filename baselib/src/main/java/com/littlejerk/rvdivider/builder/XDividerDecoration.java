package com.littlejerk.rvdivider.builder;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.littlejerk.rvdivider.DividerHelper;
import com.littlejerk.rvdivider.decoration.LDecoration;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static com.littlejerk.rvdivider.DividerHelper.DividerType.LINEAR_VERTICAL;
import static com.littlejerk.rvdivider.DividerHelper.getDividerType;

/**
 * @Author : HHotHeart
 * @Time : 2021/5/31 15:27
 * @Description : RecyclerView分割线Decoration
 */
final class XDividerDecoration extends RecyclerView.ItemDecoration {

    private Builder mBuilder;
    private int mFullSpanPosition = -1;

    private XDividerDecoration() {
    }

    /**
     * 分割线构造器绑定
     *
     * @param builder
     * @return
     */
    private XDividerDecoration bind(Builder builder) {
        mBuilder = builder;
        return this;
    }

    @Override
    public void onDraw(@NonNull Canvas c,
                       @NonNull RecyclerView parent,
                       @NonNull RecyclerView.State state) {
        DividerHelper.DividerType type = getDividerType(parent);
        switch (type) {
            case LINEAR_HORIZONTAL:
                drawDividerForHLinear(c, parent, (XLinearBuilder) mBuilder);
                break;
            case LINEAR_VERTICAL:
                drawDividerForVLinear(c, parent, (XLinearBuilder) mBuilder);
                break;
            case GRID_VERTICAL:
                drawDividerForVGrid(c, parent, (XGridBuilder) mBuilder);
                break;
            case GRID_HORIZONTAL:
                drawDividerForHGrid(c, parent, (XGridBuilder) mBuilder);
                break;
            case STAGGERED_GRID_HORIZONTAL:
            case STAGGERED_GRID_VERTICAL:
                //瀑布流的drawable用的不多，暂时不支持，只是单纯设置分割线
                break;
            case UNKNOWN:
            default:
                super.onDraw(c, parent, state);
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        DividerHelper.DividerType type = getDividerType(parent);
        switch (type) {
            case LINEAR_HORIZONTAL:
            case LINEAR_VERTICAL:
                getItemOffsetsForLinear(outRect, view, parent, type);
                break;
            case GRID_VERTICAL:
            case GRID_HORIZONTAL:
                getItemOffsetsForGrid(outRect, view, parent, (XGridBuilder) mBuilder);
                break;
            case STAGGERED_GRID_VERTICAL:
            case STAGGERED_GRID_HORIZONTAL:
                getItemOffsetsForStaggeredGrid(outRect, view, parent, (XStaggeredGridBuilder) mBuilder);
                break;
            case UNKNOWN:
            default:
                super.getItemOffsets(outRect, view, parent, state);
        }
    }

    /**
     * 绘制水平方向分割线（对应LayoutManage的竖直方法）
     *
     * @param c
     * @param parent
     * @param builder
     */
    protected void drawDividerForVLinear(Canvas c, RecyclerView parent, XLinearBuilder builder) {
        c.save();
        Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter");
        Objects.requireNonNull(builder, "LinearLayoutManager分割线必须设置LinearBuilder");
        int count = builder.isShowLastLine() ? parent.getAdapter().getItemCount() : parent.getAdapter().getItemCount() - 1;
        int parentLPadding = builder.isIncludeParentLTPadding() ? 0 : parent.getPaddingLeft();
        int parentRPadding = builder.isIncludeParentRBPadding() ? 0 : parent.getPaddingRight();

        int childCount = parent.getChildCount();
        Drawable drawable = null;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //这个才是真正的layout position
            int itemPosition = params.getViewLayoutPosition();
            LDecoration decoration = DividerHelper.getDecoration(builder, itemPosition);
            if (decoration != null) {
                drawable = decoration.getDividerDrawable();
            } else {
                drawable = builder.getDividerDrawable();
            }
            int decorationLeftPadding = DividerHelper.getDecorationLeftPadding(decoration);
            int decorationRightPadding = DividerHelper.getDecorationRightPadding(decoration);
            int leftPadding = decorationLeftPadding == 0 ? builder.getLeftPadding() : decorationLeftPadding;
            int rightPadding = decorationRightPadding == 0 ? builder.getRightPadding() : decorationRightPadding;

            int left = parentLPadding + leftPadding;
            int right = parent.getWidth() - parentRPadding - rightPadding;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + builder.getSpacing();

            Boolean isDrawLeft = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.LEFT);
            Boolean isDrawRight = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.RIGHT);
            if (isDrawLeft != null && isDrawLeft) {
                int edgTop = child.getTop() + params.topMargin;
                int edgBottom = child.getBottom() + params.bottomMargin;
                int edgRight = left + builder.getSpacing();
                drawable.setBounds(left, edgTop, edgRight, edgBottom);
                drawable.draw(c);
            }
            if (isDrawRight != null && isDrawRight) {
                int edgTop = child.getTop() + params.topMargin;
                int edgBottom = child.getBottom() + params.bottomMargin;
                int edgLeft = right - builder.getSpacing();
                drawable.setBounds(edgLeft, edgTop, right, edgBottom);
                drawable.draw(c);
            }

            if (i == 0 && builder.isShowFirstTopLine()) {
                int edgBottom = child.getTop() - params.topMargin;
                int edgTop = edgBottom - builder.getSpacing();
                drawable.setBounds(left, edgTop, right, edgBottom);
                drawable.draw(c);
            }
            if (i < count) {
                drawable.setBounds(left, top, right, bottom);
            } else {
                drawable.setBounds(left, top, right, top);
            }
            drawable.draw(c);
        }
        c.restore();
    }


    /**
     * 绘制竖直方向分割线（对应LayoutManage的水平方法）
     *
     * @param c
     * @param parent
     * @param builder
     */
    protected void drawDividerForHLinear(Canvas c, RecyclerView parent, XLinearBuilder builder) {
        c.save();
        Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter");
        Objects.requireNonNull(builder, "LinearLayoutManager分割线必须设置LinearBuilder");
        int count = builder.isShowLastLine() ? parent.getAdapter().getItemCount() : parent.getAdapter().getItemCount() - 1;
        int parentTPadding = builder.isIncludeParentLTPadding() ? 0 : parent.getPaddingTop();
        int parentBPadding = builder.isIncludeParentRBPadding() ? 0 : parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        Drawable drawable = null;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //这个才是真正的layout position
            int itemPosition = params.getViewLayoutPosition();
            LDecoration decoration = DividerHelper.getDecoration(builder, itemPosition);
            if (decoration != null) {
                drawable = decoration.getDividerDrawable();
            } else {
                drawable = builder.getDividerDrawable();
            }

            int decorationTopPadding = DividerHelper.getDecorationTopPadding(decoration);
            int decorationBottomPadding = DividerHelper.getDecorationBottomPadding(decoration);
            int topPadding = decorationTopPadding == 0 ? builder.getLeftPadding() : decorationTopPadding;
            int bottomPadding = decorationBottomPadding == 0 ? builder.getRightPadding() : decorationBottomPadding;

            int top = parentTPadding + topPadding;
            int bottom = parent.getHeight() - parentBPadding - bottomPadding;
            int left = child.getRight() + params.rightMargin;
            int right = left + builder.getSpacing();

            Boolean isDrawTop = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.TOP);
            Boolean isDrawBottom = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.BOTTOM);
            if (isDrawTop != null && isDrawTop) {
                int edgLeft = child.getLeft() + params.leftMargin;
                int edgTop = child.getTop() - params.topMargin - builder.getSpacing();
                int edgRight = child.getRight() + params.rightMargin;
                int edgBottom = edgTop + builder.getSpacing();
                drawable.setBounds(edgLeft, edgTop, edgRight, edgBottom);
                drawable.draw(c);
            }

            if (isDrawBottom != null && isDrawBottom) {
                int edgTop = child.getBottom() + params.bottomMargin;
                int edgBottom = edgTop + builder.getSpacing();
                int edgLeft = child.getLeft() - params.leftMargin;
                int edgRight = child.getRight() + params.rightMargin;
                drawable.setBounds(edgLeft, edgTop, edgRight, edgBottom);
                drawable.draw(c);
            }

            if (i == 0 && builder.isShowFirstTopLine()) {
                int edgRight = child.getLeft() - params.leftMargin;
                int edgLeft = edgRight - builder.getSpacing();
                drawable.setBounds(edgLeft, top, edgRight, bottom);
                drawable.draw(c);
            }

            if (i < count) {
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
            } else {
                drawable.setBounds(left, top, left, bottom);
            }
        }
        c.restore();
    }


    /**
     * 绘制LinearLayoutManger的分割线
     *
     * @param outRect
     * @param view
     * @param parent
     * @param type
     */
    protected void getItemOffsetsForLinear(Rect outRect, View view, RecyclerView parent, DividerHelper.DividerType type) {
        XLinearBuilder builder = (XLinearBuilder) mBuilder;
        Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter");
        //最后分割线
        int count = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        if (builder.getOnItemNoDivider() != null && builder.getOnItemNoDivider().getNoDividerPosition() != null) {
            int[] noDivider = builder.getOnItemNoDivider().getNoDividerPosition();
            if (DividerHelper.isContains(noDivider, itemPosition)) {
                outRect.set(0, 0, 0, 0);
                return;
            }
        }

        LDecoration decoration = DividerHelper.getDecoration(builder, itemPosition);
        int dividerSpace = builder.getSpacing();

        if (type == LINEAR_VERTICAL) {
            int top = 0, bottom = dividerSpace;

            Boolean isDrawLeft = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.LEFT);
            Boolean isDrawRight = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.RIGHT);

            int left = isDrawLeft == null ? 0 : dividerSpace;
            int right = isDrawRight == null ? 0 : dividerSpace;
            //第一条顶部分割线
            if (builder.isShowFirstTopLine() && itemPosition == 0) {
                top = dividerSpace;
            }
            //最后分割线
            if (!builder.isShowLastLine() && itemPosition == count - 1) {
                bottom = 0;
            }

            if (itemPosition < count) {
                outRect.set(left, top, right, bottom);
            }

        } else {
            int left = 0, right = dividerSpace;
            Boolean isDrawTop = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.TOP);
            Boolean isDrawBottom = DividerHelper.isDrawEdge(decoration, DividerHelper.Edge.BOTTOM);

            int top = isDrawTop == null ? 0 : dividerSpace;
            int bottom = isDrawBottom == null ? 0 : dividerSpace;

            //第一条顶部分割线
            if (builder.isShowFirstTopLine() && itemPosition == 0) {
                left = dividerSpace;
            }
            //最后分割线
            if (!builder.isShowLastLine() && itemPosition == count - 1) {
                right = 0;
            }
            if (itemPosition < count) {
                outRect.set(left, top, right, bottom);
            }
        }

    }


    /**
     * 为Grid绘制有Drawable的线(GridLayoutManage方向为竖直V方向)
     * getItemOffsets方法限制了item的偏移量
     */
    protected void drawDividerForVGrid(Canvas c, RecyclerView parent, XGridBuilder builder) {
        c.save();
        drawHDividerForVGrid(c, parent, builder);
        drawVDividerForVGrid(c, parent, builder);
        c.restore();
    }

    /**
     * 为Grid画水平方向的分割线（GridLinearLayoutManage的布局方向是竖直）
     *
     * @param c
     * @param parent
     * @param builder
     */
    private void drawHDividerForVGrid(Canvas c, RecyclerView parent, XGridBuilder builder) {
        Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter");
        Objects.requireNonNull(builder, "GridLinearLayoutManage分割线必须设置GridBuilder");
        if (builder.getHLineDividerDrawable() == null) {
            return;
        }
        int hLineSpacing = builder.getHLineSpacing() == 0 ? builder.getSpacing() : builder.getHLineSpacing();
        int vLineSpacing = builder.getVLineSpacing() == 0 ? builder.getSpacing() : builder.getVLineSpacing();

        int childCount = parent.getChildCount();
//        int spanCount = getSpanCount(parent);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        Objects.requireNonNull(gridLayoutManager, "RecyclerView LayoutManager请设置GridLayoutManager");
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        int spanCount = gridLayoutManager.getSpanCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 当前i的itemSpanSize
            int itemSpanSize = spanSizeLookup.getSpanSize(i);
            // spanIndex = 0 表示是最左边(当前item起始的index)
            int spanIndex = spanSizeLookup.getSpanIndex(i, spanCount);
            // 行
            int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(i, spanCount);

            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + hLineSpacing;

            int edgeTop = child.getTop() - params.topMargin - hLineSpacing;
            int edgeBottom = child.getTop() - params.topMargin;
            if (builder.isIncludeEdge()) {
                //竖直方向分割线不包括角边
                if (!builder.isVerticalIncludeEdge()) {
//                    if (spanIndex == 0) {
//                        left = left - vLineSpacing;
//                    }
                    //会重复绘制
                    left = left - vLineSpacing;
                    right = right + vLineSpacing;
                }
                //第一行
                if (spanGroupIndex == 0) {
                    builder.getHLineDividerDrawable().setBounds(left, edgeTop, right, edgeBottom);
                    builder.getHLineDividerDrawable().draw(c);
                }
                //如果item跨span
                if (spanGroupIndex != 0 && itemSpanSize > 1) {
                    builder.getHLineDividerDrawable().setBounds(left, edgeTop, right, edgeBottom);
                    builder.getHLineDividerDrawable().draw(c);
                }
                builder.getHLineDividerDrawable().setBounds(left, top, right, bottom);
                builder.getHLineDividerDrawable().draw(c);

            } else {
                //竖直方向分割线不包括角边
                if (!builder.isVerticalIncludeEdge()) {
//                    if (spanIndex == 0) {
//                        left = left - vLineSpacing;
//                    }
                    //会重复绘制
                    left = left - vLineSpacing;
                    right = right + vLineSpacing;
                }
                //如果item跨span
                if (spanGroupIndex != 0 && itemSpanSize > 1) {
                    builder.getHLineDividerDrawable().setBounds(left, edgeTop, right, edgeBottom);
                    builder.getHLineDividerDrawable().draw(c);
                }
//                //最后一行不绘制分割线
//                if (!DividerHelper.isLastRow(parent, childCount, i, itemSpanSize, spanIndex)) {
//                    builder.getHLineDividerDrawable().setBounds(left, top, right, bottom);
//                    builder.getHLineDividerDrawable().draw(c);
//                }
                builder.getHLineDividerDrawable().setBounds(left, top, right, bottom);
                builder.getHLineDividerDrawable().draw(c);
            }
        }
    }

    /**
     * 为Grid画竖直方向的分割线（GridLinearLayoutManage的布局方向是竖直）
     *
     * @param c
     * @param parent
     * @param builder
     */
    private void drawVDividerForVGrid(Canvas c, RecyclerView parent, XGridBuilder builder) {
        Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter");
        if (builder.getVLineDividerDrawable() == null) {
            return;
        }
        int hLineSpacing = builder.getHLineSpacing() == 0 ? builder.getSpacing() : builder.getHLineSpacing();
        int vLineSpacing = builder.getVLineSpacing() == 0 ? builder.getSpacing() : builder.getVLineSpacing();

        int childCount = parent.getChildCount();
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        Objects.requireNonNull(gridLayoutManager, "RecyclerView LayoutManager请设置GridLayoutManager");
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        int spanCount = gridLayoutManager.getSpanCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 当前item的spanSize
            int spanSize = spanSizeLookup.getSpanSize(i);
            // spanIndex = 0 表示是最左边(当前item起始的index)
            int spanIndex = spanSizeLookup.getSpanIndex(i, spanCount);

            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin;
            int right = left + vLineSpacing;
            int bottom = child.getBottom() + params.bottomMargin;

            if (builder.isIncludeEdge()) {
                int edgeLeft = child.getLeft() - params.leftMargin - vLineSpacing;
                int edgeRight = child.getLeft() - params.leftMargin;

                if (builder.isVerticalIncludeEdge()) {
                    top = top - hLineSpacing;
                    bottom = bottom + hLineSpacing;
                }
                //第一列
                if (spanIndex == 0) {
                    builder.getVLineDividerDrawable().setBounds(edgeLeft, top, edgeRight, bottom);
                    builder.getVLineDividerDrawable().draw(c);
                }

            } else {

                if (builder.isVerticalIncludeEdge()) {
                    bottom = bottom + hLineSpacing;
//                    if (DividerHelper.isLastRow(parent, childCount, i, spanSize, spanIndex)) {
//                        bottom -= hLineSpacing;
//                        HLog.e("lastRaw：" + i);
//                    }
                }
            }
            builder.getVLineDividerDrawable().setBounds(left, top, right, bottom);
            builder.getVLineDividerDrawable().draw(c);
        }
    }


    /**
     * 为分割线设置item偏移量
     *
     * @param outRect
     * @param view
     * @param parent
     * @param builder
     */
    public void getItemOffsetsForGrid(Rect outRect,
                                      View view,
                                      RecyclerView parent,
                                      XGridBuilder builder) {

        Objects.requireNonNull(builder, " GridLayoutManager分割线必须设置GridBuilder");
        int hLineSpacing = builder.getHLineSpacing() == 0 ? builder.getSpacing() : builder.getHLineSpacing();
        int vLineSpacing = builder.getVLineSpacing() == 0 ? builder.getSpacing() : builder.getVLineSpacing();

        int lastPosition = Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter").getItemCount() - 1;
        int position = parent.getChildAdapterPosition(view);
        if (position > lastPosition) {
            return;
        }
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        Objects.requireNonNull(layoutManager, "RecyclerView LayoutManager请设置GridLayoutManager");
        //规则是第n列item的outRect.right + 第n+1列的outRect.left 等于 spacing，以此类推
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanCount = layoutManager.getSpanCount();
        // 当前position的itemSpanSize
        int itemSpanSize = spanSizeLookup.getSpanSize(position);
        // 一行几个
        int rowSpanCount = spanCount / itemSpanSize;
        // spanIndex = 0 表示是最左边,即第一列
        int spanIndex = spanSizeLookup.getSpanIndex(position, spanCount);
        // 列
        int column = spanIndex / itemSpanSize;
        // 行 可在这控制底部Footer绘制
        int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount);
        int orientation = layoutManager.getOrientation();

        if (builder.isIncludeEdge()) {
            //包括边界：第一列的outRect.left = spacing，第column + 1 = realSpanCount列outRect.right = spacing
            if (orientation == VERTICAL) {
                outRect.left = vLineSpacing - column * vLineSpacing / rowSpanCount;
                outRect.right = (column + 1) * vLineSpacing / rowSpanCount;
            } else {
                outRect.top = hLineSpacing - column * hLineSpacing / rowSpanCount;
                outRect.bottom = (column + 1) * hLineSpacing / rowSpanCount;
            }
            //第一行才有间距
            if (spanGroupIndex < 1 && position < rowSpanCount) {
                if (orientation == VERTICAL) {
                    // 上间距
                    outRect.top = hLineSpacing;
                } else {
                    // 左间距
                    outRect.left = vLineSpacing;
                }
            }
            if (orientation == VERTICAL) {
                // 下边偏移量
                outRect.bottom = hLineSpacing;
            } else {
                // 右边偏移量
                outRect.right = vLineSpacing;
            }
        } else {
            if (orientation == VERTICAL) {
                outRect.left = column * vLineSpacing / rowSpanCount;
                outRect.right = vLineSpacing - (column + 1) * vLineSpacing / rowSpanCount;
            } else {
                outRect.top = column * hLineSpacing / rowSpanCount;
                outRect.bottom = hLineSpacing - (column + 1) * hLineSpacing / rowSpanCount;
            }

            if (spanGroupIndex >= 1) {
                if (orientation == VERTICAL) {
                    // 超过第0行都显示上间距
                    outRect.top = hLineSpacing;
                } else {
                    // 超过第0列都显示左间距
                    outRect.left = vLineSpacing;

                }
            }
        }

    }

    /**
     * 为Grid绘制有Drawable的线(GridLayoutManage方向为竖直H方向)
     */
    protected void drawDividerForHGrid(Canvas c, RecyclerView parent, XGridBuilder builder) {
        //行实际上是LayoutManager的列
        //列实际上是LayoutManager的行
        //layoutManage的方向是水平
        c.save();
        drawHDividerForHGrid(c, parent, builder);
        drawVDividerForHGrid(c, parent, builder);
        c.restore();
    }

    /**
     * 为Grid画水平方向的分割线（GridLinearLayoutManage的布局方向是水平）
     *
     * @param c
     * @param parent
     * @param builder
     */
    private void drawHDividerForHGrid(Canvas c, RecyclerView parent, XGridBuilder builder) {
        Objects.requireNonNull(builder, "GridLinearLayoutManage分割线必须设置GridBuilder");
        if (builder.getHLineDividerDrawable() == null) {
            return;
        }
        int hLineSpacing = builder.getHLineSpacing() == 0 ? builder.getSpacing() : builder.getHLineSpacing();
        int vLineSpacing = builder.getVLineSpacing() == 0 ? builder.getSpacing() : builder.getVLineSpacing();

        int childCount = parent.getChildCount();
//        int spanCount = DividerHelper.getSpanCount(parent);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        Objects.requireNonNull(gridLayoutManager, "RecyclerView LayoutManager请设置GridLayoutManager");
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        int spanCount = gridLayoutManager.getSpanCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 当前i的itemSpanSize
            int itemSpanSize = spanSizeLookup.getSpanSize(i);
            // spanIndex = 0 表示是最左边(当前item起始的index)
            int spanIndex = spanSizeLookup.getSpanIndex(i, spanCount);
            // 行
            int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(i, spanCount);

            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + hLineSpacing;

            if (builder.isIncludeEdge()) {
                int edgeTop = child.getTop() - params.topMargin - hLineSpacing;
                int edgeBottom = child.getTop() - params.topMargin;
                if (!builder.isVerticalIncludeEdge()) {
//                    //第一列
//                    if (spanGroupIndex == 0) {
//                        left = left - vLineSpacing;
//
//                    }
                    //会重复绘制
                    left = left - vLineSpacing;
                    right = right + vLineSpacing;
                }
                //第一行
                if (spanIndex == 0) {
                    builder.getHLineDividerDrawable().setBounds(left, edgeTop, right, edgeBottom);
                    builder.getHLineDividerDrawable().draw(c);
                }

            } else {
                if (!builder.isVerticalIncludeEdge()) {
                    //非最最后一列
                    if (!DividerHelper.isLastColumn(parent, childCount, i)) {
                        right = right + vLineSpacing;
                    }

                }
            }
            builder.getHLineDividerDrawable().setBounds(left, top, right, bottom);
            builder.getHLineDividerDrawable().draw(c);
        }
    }

    /**
     * 为Grid画竖直方向的分割线（GridLinearLayoutManage的布局方向是水平）
     *
     * @param c
     * @param parent
     * @param builder
     */
    private void drawVDividerForHGrid(Canvas c, RecyclerView parent, XGridBuilder builder) {
        Objects.requireNonNull(builder, "GridLinearLayoutManage分割线必须设置GridBuilder");
        if (builder.getVLineDividerDrawable() == null) {
            return;
        }
        int hLineSpacing = builder.getHLineSpacing() == 0 ? builder.getSpacing() : builder.getHLineSpacing();
        int vLineSpacing = builder.getVLineSpacing() == 0 ? builder.getSpacing() : builder.getVLineSpacing();

        int childCount = parent.getChildCount();
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        Objects.requireNonNull(gridLayoutManager, "RecyclerView LayoutManager请设置GridLayoutManager");
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        int spanCount = gridLayoutManager.getSpanCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 当前i的itemSpanSize
            int itemSpanSize = spanSizeLookup.getSpanSize(i);
            // spanIndex = 0 表示是最左边(当前item起始的index)
            int spanIndex = spanSizeLookup.getSpanIndex(i, spanCount);
            // 行
            int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(i, spanCount);

            //绘制item右边界
            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin;
            int right = left + vLineSpacing;
            int bottom = child.getBottom() + params.bottomMargin;

            if (builder.isIncludeEdge()) {
                if (builder.isVerticalIncludeEdge()) {
                    //第一行
                    if (spanIndex == 0) {
                        top = top - hLineSpacing;
                    }
                    bottom = bottom + hLineSpacing;
                }
                //第一列
                if (spanGroupIndex == 0) {
                    int edgeLeft = child.getLeft() - params.leftMargin - vLineSpacing;
                    int edgeRight = child.getLeft() - params.leftMargin;
                    builder.getVLineDividerDrawable().setBounds(edgeLeft, top, edgeRight, bottom);
                    builder.getVLineDividerDrawable().draw(c);
                }
                builder.getVLineDividerDrawable().setBounds(left, top, right, bottom);
                builder.getVLineDividerDrawable().draw(c);
            } else {
                if (builder.isVerticalIncludeEdge()) {
                    bottom = bottom + hLineSpacing;
                }
//                //最后一行
//                if (spanIndex + itemSpanSize == spanCount) {
//                    HLog.e("ttt", i + "");
//                    bottom -= hLineSpacing;
//                }
                //非最最后一列
                if (!DividerHelper.isLastColumn(parent, childCount, i)) {
                    builder.getVLineDividerDrawable().setBounds(left, top, right, bottom);
                    builder.getVLineDividerDrawable().draw(c);
                }
            }
            //处理item跨多个span的情况(会重复绘制)
            if (spanGroupIndex != 0) {
                int edgeLeft = child.getLeft() - params.leftMargin - vLineSpacing;
                int edgeRight = child.getLeft() - params.leftMargin;
                builder.getVLineDividerDrawable().setBounds(edgeLeft, top, edgeRight, bottom);
                builder.getVLineDividerDrawable().draw(c);
            }
        }
    }


    /**
     * 为分割线设置item偏移量
     *
     * @param outRect
     * @param view
     * @param parent
     * @param builder
     */
    public void getItemOffsetsForStaggeredGrid(Rect outRect, View view,
                                               RecyclerView parent,
                                               XStaggeredGridBuilder builder) {
        Objects.requireNonNull(builder, "GridLinearLayoutManage分割线必须设置XStaggeredGridBuilder");
        int hLineSpacing = builder.getHLineSpacing() == 0 ? builder.getSpacing() : builder.getHLineSpacing();
        int vLineSpacing = builder.getVLineSpacing() == 0 ? builder.getSpacing() : builder.getVLineSpacing();

        int lastPosition = Objects.requireNonNull(parent.getAdapter(), "RecyclerView请设置Adapter").getItemCount() - 1;
        int position = parent.getChildAdapterPosition(view);
        if (position > lastPosition) {
            return;
        }
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        Objects.requireNonNull(layoutManager, "RecyclerView LayoutManager请设置StaggeredGridLayoutManager");
        // 瀑布流获取列方式不一样
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 瀑布流item处于那一列
        int column = params.getSpanIndex();
        // 瀑布流是否占满一行
        boolean isFullSpan = params.isFullSpan();
        int spanCount = layoutManager.getSpanCount();
        //真正的spanCount
        spanCount = spanCount / (isFullSpan ? spanCount : 1);
        int orientation = layoutManager.getOrientation();

        if (builder.isIncludeEdge()) {
            if (isFullSpan && !builder.isIgnoreFullSpan()) {
                outRect.left = 0;
                outRect.right = 0;
            } else {
                if (orientation == VERTICAL) {
                    outRect.left = vLineSpacing - column * vLineSpacing / spanCount;
                    outRect.right = (column + 1) * vLineSpacing / spanCount;
                } else {
                    outRect.top = hLineSpacing - column * hLineSpacing / spanCount;
                    outRect.bottom = (column + 1) * hLineSpacing / spanCount;
                }
            }

            // 找到头部第一个整行的position，后面的上间距都不显示
            if (mFullSpanPosition == -1 && position < spanCount && isFullSpan) {
                mFullSpanPosition = position;
            }
            //显示上间距: 头部没有整行||头部体验整行但是在之前的position
            boolean isFirstLineStagger = (mFullSpanPosition == -1 || position < mFullSpanPosition) && (position < spanCount);
            if (isFirstLineStagger) {
                if (orientation == VERTICAL) {
                    // 上间距
                    outRect.top = hLineSpacing;
                } else {
                    // 左间距
                    outRect.left = vLineSpacing;
                }
            }
            if (orientation == VERTICAL) {
                // 底部偏移量
                outRect.bottom = hLineSpacing;
            } else {
                // 右边偏移量
                outRect.right = vLineSpacing;
            }
        } else {
            if (isFullSpan && !builder.isIgnoreFullSpan()) {
                outRect.left = 0;
                outRect.right = 0;
            } else {
                if (orientation == VERTICAL) {
                    outRect.left = column * vLineSpacing / spanCount;
                    outRect.right = vLineSpacing - (column + 1) * vLineSpacing / spanCount;
                } else {
                    outRect.top = column * hLineSpacing / spanCount;
                    outRect.bottom = hLineSpacing - (column + 1) * hLineSpacing / spanCount;
                }
            }

            // 找到头部第一个整行的position
            if (mFullSpanPosition == -1 && position < spanCount && isFullSpan) {
                mFullSpanPosition = position;
            }
            // 上间距显示规则
            boolean isStaggerShowTop = position >= spanCount || (isFullSpan && position != 0) || (mFullSpanPosition != -1 && position != 0);

            if (isStaggerShowTop) {
                if (orientation == VERTICAL) {
                    // 超过第0行都显示上间距
                    outRect.top = hLineSpacing;
                } else {
                    // 超过第0列都显示左间距
                    outRect.left = vLineSpacing;
                }
            }
        }


    }

    /**
     * 提供初始化XDividerDecoration
     */
    protected static class Builder {
        //上下文
        protected Context mContext;

        protected Builder(Context context) {
            this.mContext = context;
        }

        /**
         * XDividerDecoration初始化
         *
         * @return RecyclerView.ItemDecoration
         */
        public RecyclerView.ItemDecoration build() {
            return new XDividerDecoration().bind(this);
        }

    }

}
