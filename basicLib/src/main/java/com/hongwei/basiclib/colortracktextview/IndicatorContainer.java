package com.hongwei.basiclib.colortracktextview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FileName: IndicatorContainer
 * Author: XYB
 * Date: 2020/12/27
 * Description: 指示器的容器包括下标
 */
public class IndicatorContainer extends FrameLayout {
    private LinearLayout mIndicatorContainer;

    private View mBottomTrackView;

    //一个条目的宽度
    private int mItemWidth;

    //底部的指示器的LayoutParams
    private LayoutParams mBottomTrackParams;

    private Context mContext;

    private int mInitLeftMargin;

    public IndicatorContainer(@NonNull Context context) {
        this(context,null);
    }

    public IndicatorContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    public void addView(View child) {
        if (mIndicatorContainer == null) {
            // 初始化容器
            mIndicatorContainer = new LinearLayout(mContext);
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            super.addView(mIndicatorContainer, params);
        }
        mIndicatorContainer.addView(child);
    }

    //获取当前位置的item
    public View getItemAt(int position) {
        return mIndicatorContainer.getChildAt(position);
    }

    //添加底部指示器
    public void addBottomTrackView(View bottomTrackView,int itemWidth) {
        if (bottomTrackView == null || mBottomTrackView != null){
            return;
        }

        this.mItemWidth = itemWidth;

        this.mBottomTrackView = bottomTrackView;
        //添加底部跟踪的View
        super.addView(mBottomTrackView);

        mBottomTrackParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mBottomTrackParams.gravity = Gravity.BOTTOM;
    //    mBottomTrackParams.topMargin = 100;
        mBottomTrackParams.width = mItemWidth;

        // 计算和指定指示器的宽度
        int width = mBottomTrackParams.width;
        mItemWidth = mIndicatorContainer.getChildAt(0).getLayoutParams().width;
        if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
            width = mItemWidth;
        }
        // 计算跟踪的View初始左边距离
        if (width < mItemWidth) {
            mInitLeftMargin = (mItemWidth - width) / 2;
        }
        mBottomTrackParams.leftMargin = mInitLeftMargin;
    }

    //滚动底部的指示器
    public void scrollBottomTrack(int position, float positionOffset) {
        if (mBottomTrackView == null) return;
        mBottomTrackParams.leftMargin = (int) (mInitLeftMargin + (position + positionOffset) * mItemWidth);
        mBottomTrackView.setLayoutParams(mBottomTrackParams);
    }

    /**
     * 开启一个动画移动到当前位置
     */
    public void smoothScrollToPosition(int position) {
        if (mBottomTrackView == null) return;
        // 获取当前指示器距左边的距离
        final int mCurrentLeftMargin = mBottomTrackParams.leftMargin;
        // 计算出最终的距离
        final int finalLeftMargin = mItemWidth * position + mInitLeftMargin;
        // 用于动画执行的事件
        final int distance = finalLeftMargin - mCurrentLeftMargin;
        // 利用属性动画不断的更新距离
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBottomTrackView, "leftMargin",
                mCurrentLeftMargin, finalLeftMargin).setDuration(Math.abs(distance));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        // 添加动画监听不断的更新 leftMargin
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentLeftMargin = (float) animation.getAnimatedValue();
                // Log.e(TAG, "current --> " + currentLeftMargin);
                setBottomTrackLeftMargin((int) currentLeftMargin);
            }
        });
    }

    /**
     * 设置底部跟踪指示器的左边距离
     */
    public void setBottomTrackLeftMargin(int bottomTrackLeftMargin) {
        mBottomTrackParams.leftMargin = bottomTrackLeftMargin;
        mBottomTrackView.setLayoutParams(mBottomTrackParams);
    }
}
