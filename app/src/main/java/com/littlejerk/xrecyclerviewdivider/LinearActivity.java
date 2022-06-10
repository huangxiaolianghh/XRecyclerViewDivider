package com.littlejerk.xrecyclerviewdivider;

import android.os.Bundle;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.littlejerk.rvdivider.builder.XLinearBuilder;
import com.littlejerk.rvdivider.decoration.LDecoration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearActivity extends AppCompatActivity {

    private LinearAdapter mAdapter;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        findViewById(R.id.iv_back).setOnClickListener(mView -> onBackPressed());
        RadioButton rb_v = findViewById(R.id.rb_v);
        RadioButton rb_h = findViewById(R.id.rb_h);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        rb_v.setOnCheckedChangeListener((mCompoundButton, mB) -> {
            if (mB) {
                deleteItemDivider(recyclerView);
                mManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mManager);
                recyclerView.addItemDecoration(bindXLinear().build());
                mAdapter = new LinearAdapter(R.layout.item_linear_vertical_list, getData());
                recyclerView.setAdapter(mAdapter);
            }
        });
        rb_h.setOnCheckedChangeListener((mCompoundButton, mB) -> {
            if (mB) {
                deleteItemDivider(recyclerView);
                mManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
                recyclerView.setLayoutManager(mManager);
                recyclerView.addItemDecoration(bindXLinear().build());
                mAdapter = new LinearAdapter(R.layout.item_linear_horizontal_list, getData());
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
    public XLinearBuilder bindXLinear() {
        return new XLinearBuilder(this)
                //分割线间距，支持float dp 和DimenRes dp
                .setSpacing(2f)
                .setShowFirstTopLine(true)
                .setShowLastLine(true)
//                .setColor(Color.BLACK)
                //可设置颜色和drawable,drawable>color
//                .setColorRes(R.color.white)
//                .setDrawable(new ColorDrawable(Color.WHITE))
//                .setDrawableRes(R.drawable.)
                //是否包含RecyclerView的padding，分为左右（竖直），上下（水平）
                .setIncludeParentHVPadding(false, false)
                //设置item分割线的padding，分为左右（竖直），上下（水平）
                .setPadding(4f)
//                .setLeftPadding(4f)
//                .setRightPadding(4f)
//                .setTopPadding(4f)
//                .setBottomPadding(4f)
                //设置哪几个position不绘制分割线，接受数组new int[]{0,1}，返回null表示不处理
                .setOnItemNoDivider(() -> null)
                //定制某一item分割线样式，可改颜色和设置边沿分割线，返回null表示不处理
                .setOnItemDividerDecoration(new XLinearBuilder.OnItemDivider() {
                    @Override
                    public LDecoration getItemDividerDecoration(int position) {
//                        if(position == 1){
//                            return new LDecoration(LinearActivity.this)
//                                    //竖直
//                                    .setAroundEdge(true,null,true,null)
//                                    //水平
//                                    .setAroundEdge(null,true,null,true)
//
//                                    //设置drawable和上面一样（针对某一条分割线）
//                                    .setColor(Color.RED)
//                                    //设置padding和上面一样
//                                    .setPadding(10f);
//
//                        }
                        return null;
                    }
                })
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


    public static class LinearAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LinearAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, String mS) {

        }
    }


    public List<String> getData() {
        return Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
    }


}