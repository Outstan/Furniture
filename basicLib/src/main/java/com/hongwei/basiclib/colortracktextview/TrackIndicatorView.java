package com.hongwei.basiclib.colortracktextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.hongwei.basiclib.R;

/**
 * FileName: TrackIndicatorView
 * Author: XYB
 * Date: 2020/12/25
 * Description: ViewPager指示器
 */
public class TrackIndicatorView extends HorizontalScrollView {

    // 自定义适配器
    private IndicatorBaseAdapter mAdapter;

    // Item的容器因为ScrollView只允许加入一个孩子
    private IndicatorContainer mIndicatorContainer;

    // 获取一屏显示多少个Item,默认是0
    private int mTabVisibleNums = 0;

    // 每个Item的宽度
    private int mItemWidth = 0;

    private ViewPager2 mViewPager;

    private int mCurrentPosition = 0;

    //解决点击抖动
    private boolean mIsExecuteScroll = false;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化Indicator容器用来存放item
        mIndicatorContainer = new IndicatorContainer(context);
        addView(mIndicatorContainer);

        // 获取自定义属性值 一屏显示多少个
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = array.getInt(R.styleable.TrackIndicatorView_tabVisibleNums,
                mTabVisibleNums);
        array.recycle();
    }

    /**
     * 重载一个setAdapter的方法
     *
     * @param adapter 适配器
     */
    public void setAdapter(IndicatorBaseAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("Adapter cannot be null!");
        }
        this.mAdapter = adapter;

        // 获取Item个数
        int count = mAdapter.getCount();

        // 动态添加到布局容器
        for (int i = 0; i < count; i++) {
            View indicatorView = mAdapter.getView(i, mIndicatorContainer);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicatorView.getLayoutParams();
            params.bottomMargin = 10;
            mIndicatorContainer.addView(indicatorView);
            switchIndicatorClick(indicatorView, i);
        }

        highLightIndicator(0);
    }

    /**
     * Indicator条目点击对应切换ViewPager
     */
    private void switchIndicatorClick(View indicatorView, int position) {
        indicatorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager != null) {
                    // 对应切换ViewPager
                    mViewPager.setCurrentItem(position);
                }
                // IndicatorItem对应滚动到最中心
                indicatorSmoothScrollTo(position);

                //点击移动底部指示器
                mIndicatorContainer.smoothScrollToPosition(position);
            }
        });
    }

    /**
     * 滚动到当前的位置带动画
     */
    private void indicatorSmoothScrollTo(int position) {
        // 当前的偏移量
        int currentOffset = ((position) * mItemWidth);
        // 原始的左边的偏移量
        int originLeftOffset = (getWidth() - mItemWidth) / 2;
        // 当前应该滚动的位置
        int scrollToOffset = currentOffset - originLeftOffset;
        // smoothScrollTo
        smoothScrollTo(scrollToOffset, 0);
    }

    /**
     * 重载一个setAdapter的方法
     *
     * @param adapter   适配器
     * @param viewPager 联动的ViewPager
     */
    public void setAdapter(IndicatorBaseAdapter adapter, ViewPager2 viewPager) {
        // 直接调用重载方法
        setAdapter(adapter);

        // 为ViewPager添加滚动监听事件
        this.mViewPager = viewPager;
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 在ViewPager滚动的时候会不断的调用该方法
                // 在不断滚动的时候让头部的当前Item一直保持在最中心
                if (mIsExecuteScroll) {
                    indicatorScrollTo(position, positionOffset);
                    //底部指示器移动
                    mIndicatorContainer.scrollBottomTrack(position, positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                // 重置上一个位置的状态
                View lastView = mIndicatorContainer.getItemAt(mCurrentPosition);
                mAdapter.restoreIndicator(lastView);
                // 高亮当前位置的状态
                mCurrentPosition = position;
                highLightIndicator(mCurrentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    mIsExecuteScroll = true;
                }

                if (state == 0) {
                    mIsExecuteScroll = false;
                }
            }
        });
    }

    /**
     * 高亮当前位置
     */
    private void highLightIndicator(int position) {
        View currentView = mIndicatorContainer.getItemAt(position);
        mAdapter.highLightIndicator(currentView);
    }

    /**
     * 不断的滚动头部
     */
    private void indicatorScrollTo(int position, float positionOffset) {
        // 当前的偏移量
        int currentOffset = (int) ((position + positionOffset) * mItemWidth);
        // 原始的左边的偏移量
        int originLeftOffset = (getWidth() - mItemWidth) / 2;
        // 当前应该滚动的位置
        int scrollToOffset = currentOffset - originLeftOffset;
        // 调用ScrollView的scrollTo方法
        scrollTo(scrollToOffset, 0);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 指定Item的宽度
            mItemWidth = getItemWidth();
            int itemCounts = mAdapter.getCount();
            for (int i = 0; i < itemCounts; i++) {
                // 指定每个Item的宽度
                mIndicatorContainer.getItemAt(i).getLayoutParams().width = mItemWidth;
            }

            //添加底部指示器
            mIndicatorContainer.addBottomTrackView(mAdapter.getBottomTrackView(), mItemWidth);
        }
    }

    /**
     * 获取每一个条目的宽度
     */
    public int getItemWidth() {
        int itemWidth = 0;
        // 获取当前控件的宽度
        int width = getWidth();
        if (mTabVisibleNums != 0) {
            // 在布局文件中指定一屏幕显示多少个
            itemWidth = width / mTabVisibleNums;
            return itemWidth;
        }
        // 如果没有指定获取最宽的一个作为ItemWidth
        int maxItemWidth = 0;
        int mItemCounts = mAdapter.getCount();
        // 总的宽度
        int allWidth = 0;

        for (int i = 0; i < mItemCounts; i++) {
            View itemView = mIndicatorContainer.getItemAt(i);
            int childWidth = itemView.getMeasuredWidth();
            maxItemWidth = Math.max(maxItemWidth, childWidth);
            allWidth += childWidth;
        }

        itemWidth = maxItemWidth;

        // 如果不足一个屏那么宽度就为  width/mItemCounts
        if (allWidth < width) {
            itemWidth = width / mItemCounts;
        }
        return itemWidth;
    }
}
