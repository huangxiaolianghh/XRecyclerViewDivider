package com.littlejerk.xrecyclerviewdivider;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.littlejerk.rvdivider.builder.XGridBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private GridAdapter mAdapter;
    private GridLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        findViewById(R.id.iv_back).setOnClickListener(mView -> onBackPressed());
        RadioButton rb_v = findViewById(R.id.rb_v);
        RadioButton rb_h = findViewById(R.id.rb_h);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        rb_v.setOnCheckedChangeListener((mCompoundButton, mB) -> {
            if (mB) {
                deleteItemDivider(recyclerView);
                mManager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(mManager);
                recyclerView.addItemDecoration(bindXGrid().build());
                mAdapter = new GridAdapter(R.layout.item_grid_vertical_list, getData());
                recyclerView.setAdapter(mAdapter);
            }
        });
        rb_h.setOnCheckedChangeListener((mCompoundButton, mB) -> {
            if (mB) {
                deleteItemDivider(recyclerView);
                mManager = new GridLayoutManager(this, 4, RecyclerView.HORIZONTAL, false);
                recyclerView.setLayoutManager(mManager);
                recyclerView.addItemDecoration(bindXGrid().build());
                mAdapter = new GridAdapter(R.layout.item_gird_horizontal_list, getData());
                recyclerView.setAdapter(mAdapter);
            }
        });

        rb_v.setChecked(true);

    }

    /**
     * 线性构造器
     *
     * @return
     */
    public XGridBuilder bindXGrid() {
        return new XGridBuilder(this)
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
    }

    /**
     * 重新设置分割线时，需删除之前的分割线
     *
     * @param recyclerView
     */
    public void deleteItemDivider(RecyclerView recyclerView) {
        for (int i = 0; i < recyclerView.getItemDecorationCount(); i++) {
            recyclerView.removeItemDecoration(recyclerView.getItemDecorationAt(i));
        }
    }


    public static class GridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public GridAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, String mS) {

        }
    }


    public List<String> getData() {
        return Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
    }

}