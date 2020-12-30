package com.hongwei.basiclib.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author xyb
 * @description:RecyclerView 分割线定制
 * @date : 2020-12-28
 */
public class CategoryItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    /**
     * 入口
     * @param context
     * @param resource_id 资源id 例：R.drawable.list_item_divider
     */
    public CategoryItemDecoration(Context context , int resource_id) {
        // 利用Drawable绘制分割线
        mDivider = ContextCompat.getDrawable(context, resource_id);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //绘制分割线
        drawHorizontal(canvas, parent);
        drawVertical(canvas, parent);
    }

    /**
     * 绘制垂直方向
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() - params.bottomMargin;
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);

            mDivider.draw(canvas);
        }
    }

    /**
     * 绘制水平方向
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + mDivider.getIntrinsicWidth() + params.rightMargin;
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);

            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int bottom = mDivider.getIntrinsicHeight();
        int right = mDivider.getIntrinsicWidth();

        if (isLastCloumn(view, parent)) {
            right = 0;
        }

        if (isLastRow(view, parent)) {
            bottom = 0;
        }

        outRect.bottom = bottom;
        outRect.right = right;
    }

    /**
     * 最后一行
     */
    private boolean isLastRow(View view, RecyclerView parent) {
        //当前的位置 > （行数-1）* 列数
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //列数
        int spanCount = getSpanCount(parent);
        //行数
        int rowNumber = parent.getAdapter().getItemCount() % spanCount == 0 ?
                parent.getAdapter().getItemCount() / spanCount : parent.getAdapter().getItemCount() / spanCount + 1;

        return currentPosition + 1 > (rowNumber - 1) * spanCount;
    }

    /**
     * 最后一列
     */
    private boolean isLastCloumn(View view, RecyclerView parent) {
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);

        return (currentPosition + 1) % spanCount == 0;
    }

    /**
     * 获取RrcycleView列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        //获取列数
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            return spanCount;
        } else return 1;
    }
}
