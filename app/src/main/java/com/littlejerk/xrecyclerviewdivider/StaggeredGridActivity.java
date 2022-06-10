package com.littlejerk.xrecyclerviewdivider;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.littlejerk.rvdivider.DividerHelper;
import com.littlejerk.rvdivider.builder.XStaggeredGridBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class StaggeredGridActivity extends AppCompatActivity {

    private StaggeredGridAdapter mAdapter;
    private StaggeredGridLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_grid);
        findViewById(R.id.iv_back).setOnClickListener(mView -> onBackPressed());
        RadioButton rb_v = findViewById(R.id.rb_v);
        RadioButton rb_h = findViewById(R.id.rb_h);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        rb_v.setOnCheckedChangeListener((mCompoundButton, mB) -> {
            if (mB) {
                deleteItemDivider(recyclerView);
                mManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mManager);
                recyclerView.addItemDecoration(bindXStaggeredGrid().build());
                mAdapter = new StaggeredGridAdapter(R.layout.item_grid_vertical_list, getData());
                recyclerView.setAdapter(mAdapter);
            }
        });
        rb_h.setOnCheckedChangeListener((mCompoundButton, mB) -> {
            if (mB) {
                deleteItemDivider(recyclerView);
                mManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mManager);
                recyclerView.addItemDecoration(bindXStaggeredGrid().build());
                mAdapter = new StaggeredGridAdapter(R.layout.item_gird_horizontal_list, getData());
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
    public XStaggeredGridBuilder bindXStaggeredGrid() {
        return new XStaggeredGridBuilder(this)
                //分割线间距，支持float dp 和DimenRes dp
                .setSpacing(2f)//这几个Spacing的优先级可看XGridBuilder说明
                .setVLineSpacing(4f)
                .setHLineSpacing(8f)
                //是否包括边界
                .setIncludeEdge(true)
                //是否忽略FullSpan的情况
                .setIgnoreFullSpan(true)
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


    public static class StaggeredGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public StaggeredGridAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, String mS) {
            TextView textView = holder.getView(R.id.tv_name);
            if (holder.getAdapterPosition() < 3) {
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                    p.setFullSpan(true);
                }

            } else {
                if (holder.getAdapterPosition() % 2 == 0) {
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    //设置图片的相对于屏幕的宽高比
                    params.width = DividerHelper.dp2px(150);
                    params.height = DividerHelper.dp2px(100);
                    textView.setLayoutParams(params);
                }
            }
        }
    }


    public List<String> getData() {
        return Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
    }

}