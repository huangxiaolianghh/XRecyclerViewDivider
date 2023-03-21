# Android ReycyclerView强大的分割线-XRecyclerViewDivider
[![](https://jitpack.io/v/HHotHeart/XRecyclerViewDivider.svg)](https://jitpack.io/#HHotHeart/XRecyclerViewDivider)

XRecyclerViewDivider是一个功能十分强大且全面的RecyclerView 分割线库，使用建造者模式初始化Decoration,链式调用即可绘制你所需要的分割线样式，间接减少重复性代码，主要有三个建造者：
[XLinearBuilder](https://github.com/HHotHeart/XRecyclerViewDivider/blob/master/baselib/src/main/java/com/littlejerk/rvdivider/builder/XLinearBuilder.java)、[XGridBuilder](https://github.com/HHotHeart/XRecyclerViewDivider/blob/master/baselib/src/main/java/com/littlejerk/rvdivider/builder/XGridBuilder.java)、[XStaggeredGridBuilder](https://github.com/HHotHeart/XRecyclerViewDivider/blob/master/baselib/src/main/java/com/littlejerk/rvdivider/builder/XStaggeredGridBuilder.java)
它们分别为LinearLayoutManager、GridLayoutManager、StaggeredGridLayoutManager绘制不一样的分割线。

## 项目引入该库

在你的 Project build.gradle文件中添加：

```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在你的 Module build.gradle文件中添加：

```java
dependencies {
	        implementation 'com.github.huangxiaolianghh:XRecyclerViewDivider:1.0.2'
	}
```


## 效果图

<table>
    <tr>
        <td ><center><img src="https://img-blog.csdnimg.cn/20210602000248207.jpg"  width="50%" ></center></td>
        <td ><center><img src="https://img-blog.csdnimg.cn/2021060200333132.jpg" width="50%"  ></center></td>
    </tr>
</table>

<table>
    <tr>
        <td ><center><img src="https://img-blog.csdnimg.cn/20210602110439897.jpg"  width="50%" ></center></td>
        <td ><center><img src="https://img-blog.csdnimg.cn/20210602125739990.jpg" width="50%"  ></center></td>
    </tr>
</table>

<table>
    <tr>
        <td ><center><img src="https://img-blog.csdnimg.cn/2021060212590869.jpg" width="50%"  ></center></td>
        <td ><center><img src="https://img-blog.csdnimg.cn/20210602125946627.jpg" width="50%"  ></center></td>
    </tr>
</table>

<table>
    <tr>
        <td ><center><img src="https://img-blog.csdnimg.cn/20210602202822236.jpg"  width="50%" ></center></td>
        <td ><center><img src="https://img-blog.csdnimg.cn/20210602202926578.jpg" width="50%"  ></center></td>
    </tr>
</table>


## 功能特点

### 1.LinearLayoutManager
 * 支持水平竖直方向LayoutManager
 * 支持设置第一个item 上边距
 * 支持设置最后一个item的分割线
 * 支持space大小、分割线padding、分割线是否绘制到RecyclerView的padding
 * 支持设置分割线color、drawable
 * 支持设置不显示哪些item分割线
 * 支持定制某个item的分割线（包括padding、color、drawable、设置边界等）

XLinearBuilder属性表格

属性| 意义
-------- | -----
mSpacing| 分割线间距，默认1dp;可直接设置dp或dp对应的res
mShowLastLine| 是否绘制最后一个item的分割线，默认false
mShowFirstTopLine| 是否绘制第一个item上边或左边分割线，默认false
mIsIncludeParentLTPadding| 分割线是否绘制RecyclerView的左或上padding部分，默认false
mIsIncludeParentRBPadding| 分割线是否绘制RecyclerView的右或下padding部分，默认false
mLeftPadding &#124; mTopPadding|分割线左边距或上边距
mRightPadding &#124; mBottomPadding|分割线右边距或下边距
mColor| 分割线的颜色
mDividerDrawable| 分割线的drawable
mOnNoDividerPosition| position分割线不绘制的回调，返回position的数组，为null不处理
mOnItemDivider| 定制某条分割线的回调，返回的是LDecoration，为null不处理

LDecoration主要是设置某条分割线的属性，如果设置了setPadding方法，则会把mLeftPadding、mTopPadding、mRightPadding、mBottomPadding设置，设置了mDividerDrawable会覆盖mColor，具体可看源码。


基本使用：
```java
        new XLinearBuilder(this)
                //分割线间距，支持float dp 和DimenRes dp
                .setSpacing(4f)
                .setShowFirstTopLine(true)
                .setShowLastLine(true)
                .setColor(Color.BLACK)
                //设置哪几个position不绘制分割线，接受数组new int[]{0,1}，返回null表示不处理
                .setOnItemNoDivider(() -> null)
                //定制某一item分割线样式，可改颜色和设置边沿分割线，返回null表示不处理
                .setOnItemDividerDecoration(new XLinearBuilder.OnItemDivider() {
                    @Override
                    public LDecoration getItemDividerDecoration(int position) {
                        if (position == 1) {
                            return new LDecoration(LinearActivity.this)
                                    .setColor(Color.RED);

                        }
                        return null;
                    }
                })
        ;
```
如果想全部绘制边沿分割线，可这样设置：

```java
        new XLinearBuilder(this)
                //分割线间距，支持float dp 和DimenRes dp
                .setSpacing(4f)
                .setShowFirstTopLine(true)
                .setShowLastLine(true)
                .setColor(Color.BLACK)
                //设置哪几个position不绘制分割线，接受数组new int[]{0,1}，返回null表示不处理
                .setOnItemNoDivider(() -> null)
                //定制某一item分割线样式，可改颜色和设置边沿分割线，返回null表示不处理
                .setOnItemDividerDecoration(new XLinearBuilder.OnItemDivider() {
                    @Override
                    public LDecoration getItemDividerDecoration(int position) {
                        return new LDecoration(LinearActivity.this)
                                .setAroundEdge(true, null, true, null)
                                .setColor(Color.RED);


                    }
                });
```
具体还有很多功能，可clone下来，自己去体验。

### 2.GridLayoutManager

 * 支持水平竖直方向GridLayoutManager
 * 支持设置是否显示边界
 * 可分别设置水平方向和竖直方向的space、color、drawable等
 * 支持SpanSizeLookup（不规则的Grid）
 * 支持设置横竖交叉处的归属（属于竖向或横向）

XGridBuilder属性表格

属性| 意义
-------- | -----
mSpacing| 分割线间距，默认0dp;可直接设置dp或dp对应的res
mVLineSpacing| 竖直线的间距，默认0dp，会覆盖mSpacing
mHLineSpacing| 水平线的间距，默认0dp，会覆盖mSpacing
mIsIncludeEdge| 是否绘制边界，默认false
mVerticalIncludeEdge| 分割线交叉处是否属于竖直线的部分，默认false
mVLineColor|竖直分割线颜色
mHLineColor|水平分割线颜色
mColor| 分割线的颜色，会被mVLineColor或mHLineColor覆盖
mVLineDividerDrawable| 竖直分割线的drawable
mHLineDividerDrawable| 水平分割线的drawable
mDividerDrawable| 分割线的drawable,会被mVLineDividerDrawable或mHLineDividerDrawable覆盖

基本使用：

```java
        new XGridBuilder(this)
                //分割线间距，支持float dp 和DimenRes dp
                .setSpacing(2f)//这几个Spacing的优先级可看XGridBuilder说明
                .setVLineSpacing(4f)
                .setHLineSpacing(8f)
                //这几个color和drawable的优先级可看XGridBuilder说明
                .setColor(Color.BLUE)
                //可设置颜色和drawable,drawable>color
//                .setColorRes(R.color.white)
//                .setDrawable(new ColorDrawable(Color.WHITE))
//                .setDrawableRes(R.drawable.)
                .setHLineColor(Color.BLACK)
//                .setHLineDrawable()
                .setVLineColor(Color.RED)
//                .setVLineDrawable()
                //是否包括边界
                .setIncludeEdge(true)
                //竖直和水平分割线交叉处绘制的是竖直分割线颜色（交叉处属于竖直分割线）
                .setVerticalIncludeEdge(true)
        ;
```

### 3.StaggeredGridLayoutManager
 * 支持水平竖直方向StaggeredGridLayoutManager
 * 支持设置是否显示边界
 * 可设置水平方向和竖直方向的space、不支持color、drawable（基本不用）
 * 支持设置FullSpan情况

XStaggeredGridBuilder属性表格

属性| 意义
-------- | -----
mSpacing| 分割线间距，默认0dp;可直接设置dp或dp对应的res
mVLineSpacing| 竖直线的间距，默认0dp，会覆盖mSpacing
mHLineSpacing| 水平线的间距，默认0dp，会覆盖mSpacing
mIsIncludeEdge| 是否绘制边界，默认false
mIsIgnoreFullSpan| 边界绘制是否忽略FullSpan的情况，默认false


基本使用：

```java
        new XStaggeredGridBuilder(this)
                //分割线间距，支持float dp 和DimenRes dp
                .setSpacing(2f)//这几个Spacing的优先级可看XGridBuilder说明
                .setVLineSpacing(4f)
                .setHLineSpacing(8f)
                //是否包括边界
                .setIncludeEdge(true)
                //是否忽略FullSpan的情况
                .setIgnoreFullSpan(true)
        ;
```

感谢：[ByRecyclerView](https://github.com/youlookwhat/ByRecyclerView)，采纳了其对FullSpan处理的一些思想。

## 结语

使用中有问题或没有实现的功能都可以反馈给我，我一定会尽量满足大家的需求。希望这个库能够帮到大家，欢迎star~。

[文章博客](https://blog.csdn.net/HHHceo/article/details/117453495)